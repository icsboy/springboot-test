package com.approval.approval.controller;

import com.approval.approval.common.BasicResponse;
import com.approval.approval.common.CommonResponse;
import com.approval.approval.common.ErrorResponse;
import com.approval.approval.common.ResponseMessage;
import com.approval.approval.domain.Document;
import com.approval.approval.dto.ApiResponseDto;
import com.approval.approval.dto.DocumentDto;
import com.approval.approval.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Basic;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    private ModelMapper modelMapper;

    private DocumentDto convertToDto(Document document) {
        return modelMapper.map(document, DocumentDto.class);
    }

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * 결재 문서 생성
     */
    @PostMapping("/document/new")
    public ResponseEntity<? extends BasicResponse> create(@RequestBody @Valid DocumentDto documentDto) {
        Document document = documentService.create(documentDto.toEntity());

        return ResponseEntity.ok().body(new CommonResponse(ResponseMessage.DOCUMENT_CREATE_SUCCESS));
    }

    /**
     * 전체 결제 문서 검색
     */
    @GetMapping("/documents")
    public List<DocumentDto> list() {
        List<Document> documents = documentService.findDocuments();
        return documents.stream()
                .map(document -> convertToDto(document))
                .collect(Collectors.toList());
    }

    /**
     * 생성자 기준 결재 문서 조회
     */
    @GetMapping("/documents/creator/{creatorId}")
    public List<DocumentDto> listByCreatorId(@PathVariable @NotBlank Long creatorId) {
        List<Document> documents = documentService.findDocumentsByCreatorId(creatorId);
        return documents.stream().map(document -> convertToDto(document)).collect(Collectors.toList());
    }

    /**
     * 승인자 기준 결재 문서 조회
     */
    @GetMapping("/documents/approver/{approverId}")
    public List<DocumentDto> listByApproverId(@PathVariable @NotBlank Long approverId) {
        List<Document> documents = documentService.findDocumentsByApproverId(approverId);
        return documents.stream()
                .map(document -> convertToDto(document))
                .collect(Collectors.toList());
    }

    /**
     * 결재 문서 ID로 문서 조회
     */
    @GetMapping("/document/{id}")
    public ResponseEntity<? extends BasicResponse> getDocument(@PathVariable @NotBlank Long id) {
        Optional<Document> document = documentService.findDocumentById(id);

        if(!document.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ResponseMessage.DOCUMENT_NOT_FOUND));
        }

        return ResponseEntity.ok().body(new CommonResponse<Document>(document.get(), ResponseMessage.DOCUMENT_READ_SUCCESS));
    }

    /**
     * 결재 문서 수정
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<? extends BasicResponse> edit(@PathVariable @NotBlank Long id, @RequestBody Map<String, String> param) {
        try {
            String content = param.get("content");
            Long creatorId = Long.parseLong(param.get("creatorId"));
            String result = documentService.update(id, content, creatorId);
            if (!result.equals(ResponseMessage.DOCUMENT_UPDATE_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(result));
            }
            return ResponseEntity.ok().body(new CommonResponse(result));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ResponseMessage.MEMBER_NOT_FOUND));
        }
    }

    /**
     * 결재 문서 상태 변경
     */
    @PostMapping("/updateDocumentState/{id}")
    public ResponseEntity<? extends BasicResponse> updateDocumentState(@PathVariable @NotBlank Long id, @RequestBody @Valid Map<String, String> param) {
        try {
            String status = param.get("status");
            Long approverId = Long.parseLong(param.get("approverId"));
            String result = documentService.updateDocumentState(id, status, approverId);

            if(!result.equals(ResponseMessage.DOCUMENT_UPDATE_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(result));
            }

            return ResponseEntity.ok().body(new CommonResponse(result));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ResponseMessage.MEMBER_NOT_FOUND));
        }
    }

    /**
     * 결재 문서 삭제
     */
    @DeleteMapping("/document/{id}")
    public ResponseEntity<? extends BasicResponse> deleteDocument(@PathVariable @NotBlank Long id, @RequestBody Map<String, String> param) {
        String result = "";
        try {
            Long creatorId = Long.parseLong(param.get("creatorId"));
            result = documentService.delete(id, creatorId);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ResponseMessage.MEMBER_NOT_FOUND));
        }

        if (!result.equals(ResponseMessage.DOCUMENT_DELETE_SUCCESS)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ResponseMessage.DOCUMENT_DELETE_FAIL));
        }

        return ResponseEntity.ok().body(new CommonResponse(ResponseMessage.DOCUMENT_DELETE_SUCCESS));
    }
}
