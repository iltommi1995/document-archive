package com.z9devs.mantainance;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.z9devs.dao.DocumentRepo;
import com.z9devs.elasticsearch.ElasticsearchController;
import com.z9devs.imageService.ImageProcessor;
import com.z9devs.models.Document;

@Component
public class MantainanceUtils 
{
	@Autowired
	private DocumentRepo docRepo;
	
	@Autowired
	private ElasticsearchController eController;
	
	@Autowired
	private ImageProcessor iProcessor;
	
	@Value("${documents_directory}")
	private String UPLOAD_FOLDER;
	
	public ResponseEntity<LinkedHashMap<String, LinkedHashMap<String, String>>> processDirectory() 
	{
		File folder = new File(UPLOAD_FOLDER);
		File[] listOfFiles = folder.listFiles();
		
		LinkedHashMap<String, LinkedHashMap<String, String>> res = new LinkedHashMap<>();
		LinkedHashMap<String, String> singleDoc;
		
		int dbCounter;
		int elasticCounter;
		
		for (File file : listOfFiles) 
		{
		    if (file.isFile()) 
		    {
		    	String fileExtension = FilenameUtils.getExtension(file.getAbsolutePath());
		    	dbCounter = 0;
		    	elasticCounter = 0;
		    	singleDoc = new LinkedHashMap<>();

		        // Check if the file is already stored on database
		        if (docRepo.findByFileName(file.getName()).size() == 1)
		        {
		        	singleDoc.put("database", "Already stored");
		        } 
		        else
		        	dbCounter++;
		        
		        // Check if the file is already stored on Elasticsearch
		        try 
		        {
					if(eController.checkDocumentExists(file.getName()))
					{
						singleDoc.put("elasticsearch", "Already stored");
					} 
					else
						elasticCounter++;
				} 
		        catch (IOException e) 
		        {
					e.printStackTrace();
					return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
				}
		        
		        // If file is not stored in one of both storages
		        if(dbCounter == 1 || elasticCounter == 1)
		        {
					String fileText;
					try {
						// Extracting file text with Apache Tika or with Image Processor
						fileText = (fileExtension.equalsIgnoreCase("jpg") || 
								fileExtension.equalsIgnoreCase("png")     || 
								fileExtension.equalsIgnoreCase("tiff")    || 
								fileExtension.equalsIgnoreCase("jpeg"))    ? 
										iProcessor.processImageFromFile(file) 
										: 
										new Tika().parseToString(file);
						
						// Saving CV on database
						Document doc = new Document();
						doc.setFileName(file.getName());
						doc.setDocumentText(fileText);
						
						// Storing file in database
						if (dbCounter == 1)
						{
							docRepo.save(doc);
							singleDoc.put("database", "Correctly stored");
						}
						
						// Indexing file on elasticsearch
						if (elasticCounter == 1)
						{
							eController.addDocument(doc);
							singleDoc.put("elasticsearch", "Correctly stored");
						}
					} 
					catch (IOException | TikaException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
					}
		        }
		        res.put(file.getName(), singleDoc);
		    }
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
