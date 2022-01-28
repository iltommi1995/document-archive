package com.z9devs.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="documents", uniqueConstraints = { @UniqueConstraint(columnNames = { "file_name"}) })
public class Document 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="candidate_first_name")
	private String candidateFirstName;
	
	@Column(name="candidate_second_name")
	private String candidateSecondName;
	
	@Column(name="document_text")
	@Lob
	private String documentText;
	
	@ManyToMany
	@JoinTable(
		name ="documents_lists",
		joinColumns= {@JoinColumn(name="id_document") },
		inverseJoinColumns = {@JoinColumn(name="id_list") }
	)
	private List<MyList> documentLists;
	
	@Column(name="comments")
	private String comments;
	
	
	// Getters & Setters
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getFileName() 
	{
		return fileName;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	public String getCandidateFirstName() 
	{
		return candidateFirstName;
	}
	public void setCandidateFirstName(String candidateFirstName) 
	{
		this.candidateFirstName = candidateFirstName;
	}
	public String getCandidateSecondName() 
	{
		return candidateSecondName;
	}
	public void setCandidateSecondName(String candidateSecondName) 
	{
		this.candidateSecondName = candidateSecondName;
	}
	public String getComments() 
	{
		return comments;
	}
	public void setComments(String comments) 
	{
		this.comments = comments;
	}
	public String getDocumentText() {
		return documentText;
	}
	public void setDocumentText(String documentText) {
		this.documentText = documentText;
	}
	public List<MyList> getDocumentLists() {
		return documentLists;
	}
	public void setDocumentLists(List<MyList> documentLists) {
		this.documentLists = documentLists;
	}
}