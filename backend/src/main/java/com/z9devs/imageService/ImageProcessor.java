package com.z9devs.imageService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

@Component
public class ImageProcessor 
{
	public String processImage(MultipartFile file)
	{
		// File imageFile = new File(UPLOAD_FOLDER + "otonari_teijo.png");  
	    ITesseract instance = new Tesseract();  
	    BufferedImage img;
	
	    try {  
	    	img = ImageIO.read(file.getInputStream());
	        String result = instance.doOCR(ImageHelper.convertImageToGrayscale(img));  
	        return result;  
	    } catch (TesseractException | IOException e ) {  
	        System.err.println(e.getMessage());  
	        return "Error while reading image";  
	    }
	}
	
	
	public String processImageFromFile(File file)
	{
	    ITesseract instance = new Tesseract();  
	    BufferedImage img;
	
	    try {  
	    	img = ImageIO.read(file);
	        String result = instance.doOCR(ImageHelper.convertImageToGrayscale(img));  
	        return result;  
	    } catch (TesseractException | IOException e ) {  
	        System.err.println(e.getMessage());  
	        return "Error while reading image";  
	    }
	}
}
