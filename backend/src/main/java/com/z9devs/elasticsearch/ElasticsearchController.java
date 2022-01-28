package com.z9devs.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.z9devs.models.Document;

@Component
public class ElasticsearchController 
{
	@Autowired
	private RestHighLevelClient highLevelClient;
	
	@Value("${elasticsearch.indexname}")
	private String INDEX_NAME;
	
	public boolean addDocument(Document doc)
	{
		IndexRequest request = new IndexRequest(INDEX_NAME)
				  .id(doc.getFileName())
				  .source(new Gson().toJson(doc),XContentType.JSON)
				  .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		
		try 
		{
			highLevelClient.index(request,RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkDocumentExists(String fileName) throws IOException
	{
		return highLevelClient.exists(new GetRequest(INDEX_NAME, fileName), RequestOptions.DEFAULT);
	}
}
