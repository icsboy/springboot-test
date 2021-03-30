package com.approval.approval.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Document {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String status;
    private Long creatorId;
    private Long approverId;
}
