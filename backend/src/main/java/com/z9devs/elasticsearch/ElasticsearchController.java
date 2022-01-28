package com.z9devs.elasticsearch;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
	
	public SearchHit[] searchDocuments(List<String> skills)
	{
		
		BoolQueryBuilder query = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(0)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(1).equals("") ? skills.get(0) : skills.get(1)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(2).equals("") ? skills.get(0) : skills.get(2)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(3).equals("") ? skills.get(0) : skills.get(3)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(4).equals("") ? skills.get(0) : skills.get(4)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(5).equals("") ? skills.get(0) : skills.get(5)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(6).equals("") ? skills.get(0) : skills.get(6)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(7).equals("") ? skills.get(0) : skills.get(7)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(8).equals("") ? skills.get(0) : skills.get(8)))
				.must(QueryBuilders.matchPhraseQuery("documentText", skills.get(9).equals("") ? skills.get(0) : skills.get(9)));
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(query);
		
		SearchRequest sr = new SearchRequest()
				.indices(INDEX_NAME)
				.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = highLevelClient.search(sr, RequestOptions.DEFAULT);
			
			return searchResponse.getHits().getHits();
			/*
			for (SearchHit hit : searchResponse.getHits().getHits()) 
			{
				System.out.println(hit.getSourceAsMap().get("fileName"));
				
			}
			*/
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
