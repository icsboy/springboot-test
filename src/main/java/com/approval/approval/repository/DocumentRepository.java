package com.approval.approval.repository;

import com.approval.approval.domain.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    Document save(Document document);
    Optional<Document> findById(Long id);
    List<Document> findByCreatorId(Long creatorId);
    List<Document> findByApproverId(Long approverId);
    List<Document> findAll();
    int deleteByIdAndCreatorId(Long id, Long creatorId);
    void delete(Document document);
}
