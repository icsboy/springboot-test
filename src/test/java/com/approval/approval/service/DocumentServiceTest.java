package com.approval.approval.service;

import com.approval.approval.common.ResponseMessage;
import com.approval.approval.domain.Document;
import com.approval.approval.repository.DocumentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DocumentServiceTest {

    @Autowired DocumentService documentService;
    @Autowired DocumentRepository documentRepository;

    @Test
    public void create() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document result = documentService.create(document);

        Document findDocument = documentService.findDocumentById(result.getId()).get();
        assertThat(document.getTitle()).isEqualTo(findDocument.getTitle());
    }

    @Test
    public void authorisedUserDocumentUpdate() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        String result = documentService.update(newDocument.getId(), "happy", newDocument.getCreatorId());
        Assertions.assertTrue(result.equals(ResponseMessage.DOCUMENT_UPDATE_SUCCESS));
    }

    @Test
    public void unauthorisedUserDocumentUpdate() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        String result = documentService.update(newDocument.getId(), "happy", newDocument.getCreatorId() + 1);
        Assertions.assertTrue(result.equals(ResponseMessage.MEMBER_NO_PERMISSION));
    }

    @Test
    public void approverUpdateDocumentState() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        documentService.updateDocumentState(newDocument.getId(), "denied", 2L);
        Optional<Document> selectDocument = documentService.findDocumentById(newDocument.getId());

        selectDocument.ifPresent(doc -> {
            Assertions.assertTrue(doc.getStatus().equals("denied"));
        });
    }

    @Test
    public void creatorUpdateDocumentState() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        documentService.updateDocumentState(newDocument.getId(), "denied", 1L);
        Optional<Document> selectDocument = documentService.findDocumentById(newDocument.getId());

        selectDocument.ifPresent(doc -> {
            Assertions.assertFalse(doc.getStatus().equals("denied"));
            Assertions.assertTrue(doc.getStatus().equals("request"));
        });
    }

    @Test
    public void authorisedUserDocumentDelete() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        documentService.delete(newDocument.getId(), 1L);

        Optional<Document> deleteDoc = documentService.findDocumentById(newDocument.getId());

        Assertions.assertFalse(deleteDoc.isPresent());
    }

    @Test
    public void unauthorisedUserDocumentDelete() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        documentService.delete(newDocument.getId(), 2L);

        Optional<Document> deleteDoc = documentService.findDocumentById(newDocument.getId());

        Assertions.assertTrue(deleteDoc.isPresent());
    }

    @Test
    public void unexistDocumentUpdate() {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("content1");
        document.setCreatorId(1L);
        document.setApproverId(2L);

        Document newDocument = documentService.create(document);

        documentService.delete(newDocument.getId(), 1L);

        String result = documentService.update(newDocument.getId(), "happy", newDocument.getCreatorId());
        Assertions.assertTrue(result.equals(ResponseMessage.DOCUMENT_NOT_FOUND));
    }

}
