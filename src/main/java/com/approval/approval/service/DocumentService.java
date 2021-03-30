package com.approval.approval.service;

import com.approval.approval.common.ResponseMessage;
import com.approval.approval.domain.Document;
import com.approval.approval.domain.Member;
import com.approval.approval.repository.DocumentRepository;
import com.approval.approval.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;

    public DocumentService(DocumentRepository documentRepository, MemberRepository memberRepository) {
        this.documentRepository = documentRepository;
        this.memberRepository = memberRepository;
    }

    public Document create(Document document) {
        document.setStatus("request");
        return documentRepository.save(document);
    }

    public List<Document> findDocuments() {
        return documentRepository.findAll();
    }

    public List<Document> findDocumentsByCreatorId(Long creatorId) {
        return documentRepository.findByCreatorId(creatorId);
    }

    public List<Document> findDocumentsByApproverId(Long approverId) {
        return documentRepository.findByApproverId(approverId);
    }

    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public String update(Long id, String content, Long creatorId) {
        Optional<Document> result = documentRepository.findById(id);
        if (!result.isPresent()) {
            return ResponseMessage.DOCUMENT_NOT_FOUND;
        }

        Document selectDoc = result.get();
        if (!selectDoc.getStatus().equals("request")) {
            return ResponseMessage.DOCUMENT_STATUS_NOT_REQUEST;
        }
        if (!selectDoc.getCreatorId().equals(creatorId)) {
            return ResponseMessage.MEMBER_NO_PERMISSION;
        }

        selectDoc.setContent(content);
        documentRepository.save(selectDoc);

        return ResponseMessage.DOCUMENT_UPDATE_SUCCESS;
    }

    public String updateDocumentState(Long id, String status, Long approverId) {
        Optional<Member> member = memberRepository.findById(approverId);

        if (!member.isPresent()) {
            return ResponseMessage.MEMBER_NOT_FOUND;
        }

        Member approver = member.get();

        if (!approver.getRole().equals("approver")) {
            return ResponseMessage.MEMBER_NO_PERMISSION;
        }

        Optional<Document> result = documentRepository.findById(id);

        if (!result.isPresent()) {
            return ResponseMessage.DOCUMENT_NOT_FOUND;
        }

        Document selectDoc = result.get();

        selectDoc.setStatus(status);
        documentRepository.save(selectDoc);

        return ResponseMessage.DOCUMENT_UPDATE_SUCCESS;
    }

    public String delete(Long id, Long creatorId) {
        Optional<Document> selectedDocument = documentRepository.findById(id);

        if (!selectedDocument.isPresent()) {
            return ResponseMessage.DOCUMENT_NOT_FOUND;
        }

        if (!selectedDocument.get().getCreatorId().equals(creatorId)) {
            return ResponseMessage.DOCUMENT_OWNER_NOT_MATCH;
        }

        documentRepository.deleteByIdAndCreatorId(id, creatorId);

        return ResponseMessage.DOCUMENT_DELETE_SUCCESS;
    }
}
