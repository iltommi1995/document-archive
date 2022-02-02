package com.z9devs.controllers;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.z9devs.dao.DocumentRepo;
import com.z9devs.elasticsearch.ElasticsearchController;
import com.z9devs.imageService.ImageProcessor;
import com.z9devs.mantainance.MantainanceUtils;
import com.z9devs.models.Document;
import com.z9devs.storageService.StorageService;

@RestController
public class DocumentController 
{
	@Autowired
	private StorageService sService;

	@Autowired
	private DocumentRepo docRepo;

	@Autowired
	private ElasticsearchController eController;

	@Autowired
	private MantainanceUtils mantainance;

	@Autowired
	private ImageProcessor iProcessor;
	
	@Value("${documents_directory}")
	private String UPLOAD_FOLDER;

	// Checks all documents in "documents_directory" and, if they are not stored
	// in the databased or indexed with elasticsearch, they get stored and indexed
	@GetMapping(path = "/processDirectory")
	public ResponseEntity<LinkedHashMap<String, LinkedHashMap<String, String>>> processDirectory() 
	{
		return mantainance.processDirectory();
	}
	
	@GetMapping(path = "/search")
	public ResponseEntity<SearchHit[]> search(@RequestParam(value = "skill") List<String> skills) 
	{
		while (skills.size() < 10)
			skills.add("");
		return new ResponseEntity<>(eController.searchDocuments(skills), HttpStatus.OK);
	}
	
	@GetMapping(path = "/documents")
	public ResponseEntity<SearchHit[]> getAllDocuments() 
	{
		return new ResponseEntity<>(eController.allDocuments(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/files/{file_name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("file_name") String fileName) 
	{
	    return new FileSystemResource(new File(UPLOAD_FOLDER + fileName));
	}


	// Endpoint used to upload, store and index a nuew file
	@PostMapping(path = "/storeDocument", consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String, String>> postDocument(@RequestPart("document") MultipartFile file) 
	{
		Map<String, String> res = new LinkedHashMap<>();
		if (file.isEmpty()) 
		{
			res.put("Error", "The file is empty.");
			return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
		}
		try 
		{
			if (docRepo.findByFileName(file.getOriginalFilename()).size() == 0) 
			{
				// Storing file on file system
				res.put("storedOnFileSystem", sService.storeDocument(file) ? "True" : "False");

				// Extracting file text with Apache Tika or Image Processor
				String fileText = file.getContentType().contains("image") ? 
						iProcessor.processImage(file)
						: 
						new Tika().parseToString(file.getInputStream());

				// Saving CV on database
				Document doc = new Document();
				doc.setFileName(file.getOriginalFilename());
				doc.setDocumentText(fileText);
				docRepo.save(doc);
				res.put("storedOnDatabase", sService.storeDocument(file) ? "True" : "False");

				// Indexing CV on elasticsearch
				res.put("indexedOnElasticsearch", eController.addDocument(doc) ? "True" : "False");

				return new ResponseEntity<>(res, HttpStatus.CREATED);
			} 
			else 
			{
				res.put("error", "The document is already stored.");
				return new ResponseEntity<>(res, HttpStatus.ALREADY_REPORTED);
			}
		} 
		catch (TikaException | IOException e) 
		{
			e.printStackTrace();
			res.put("error", "Something wrong happened.");
			return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping(path="deleteDocument")
	public ResponseEntity<Map<String, String>> deleteDocument(@RequestParam("documentId") String documentId)
	{
		docRepo.deleteByFileName(documentId);
		System.out.println(documentId);
		
		eController.deleteDocument(documentId);
		
		File file = new File(UPLOAD_FOLDER + documentId);
		file.delete();
		
		return null;
		
	}
}