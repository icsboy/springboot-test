package com.approval.approval.dto;

import com.approval.approval.domain.Document;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DocumentDto {
    private Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String status;
    @NotNull
    private Long creatorId;
    @NotNull
    private Long approverId;

    public Document toEntity() {
        Document document = new Document();
        document.setTitle(this.title);
        document.setContent(this.content);
        document.setStatus(this.status);
        document.setCreatorId(this.creatorId);
        document.setApproverId(this.approverId);

        return document;
    }

    public static DocumentDto from(Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setTitle(document.getTitle());
        documentDto.setContent(document.getContent());
        documentDto.setStatus(document.getStatus());
        documentDto.setCreatorId(document.getCreatorId());
        documentDto.setApproverId(document.getApproverId());
        return documentDto;
    }
}
