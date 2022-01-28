package com.z9devs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.z9devs.models.Document;

public interface DocumentRepo extends JpaRepository<Document, Integer> 
{
	public List<Document> findByFileName(String fileName);
}
