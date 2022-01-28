package com.z9devs.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="lists")
public class MyList 
{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="list_name")
	private String listName;
	
	@ManyToMany(mappedBy="documentLists")
	private List<Document> documents;

	
	// Getters & Setters
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getListName() 
	{
		return listName;
	}

	public void setListName(String listName) 
	{
		this.listName = listName;
	}

	public List<Document> getDocuments() 
	{
		return documents;
	}

	public void setDocuments(List<Document> documents) 
	{
		this.documents = documents;
	}
}
