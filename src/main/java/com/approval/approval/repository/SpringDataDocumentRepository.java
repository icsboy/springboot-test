package com.approval.approval.repository;

import com.approval.approval.domain.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataDocumentRepository extends CrudRepository<Document, Long>, DocumentRepository {

    @Override
    List<Document> findByCreatorId(Long creatorId);
}
