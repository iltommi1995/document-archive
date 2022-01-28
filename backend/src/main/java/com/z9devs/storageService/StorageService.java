package com.z9devs.storageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageService 
{
	@Value("${documents_directory}")
	private String UPLOAD_FOLDER;
	
	public boolean storeDocument(MultipartFile file)
	{
		byte[] bytes;
		Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
		try 
		{
			bytes = file.getBytes();
			Files.write(path, bytes);
			return true;
		} catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
