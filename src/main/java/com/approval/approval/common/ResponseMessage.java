package com.approval.approval.common;

public class ResponseMessage {
    public static final String DOCUMENT_CREATE_SUCCESS = "문서 작성에 성공 하였습니다.";
    public static final String DOCUMENT_READ_SUCCESS = "문서 검색에 성공 하였습니다.";
    public static final String DOCUMENT_UPDATE_SUCCESS = "문서 정보 변경에 성공 하였습니다.";
    public static final String DOCUMENT_UPDATE_FAIL = "문서 정보 변경에 실패 하였습니다.";
    public static final String DOCUMENT_OWNER_NOT_MATCH = "문서의 권한이 없습니다.";
    public static final String DOCUMENT_NOT_FOUND = "문서를 찾을 수 없습니다.";
    public static final String DOCUMENT_STATUS_NOT_REQUEST = "요청 상태의 문서가 아닙니다.";
    public static final String DOCUMENT_DELETE_SUCCESS = "문서가 정상적으로 삭제 되었습니다.";
    public static final String DOCUMENT_DELETE_FAIL = "문서 삭제에 실패 하였습니다.";

    public static final String MEMBER_NOT_FOUND = "알 수 없는 사용자 입니다.";
    public static final String MEMBER_NO_PERMISSION = "권한이 없는 사용자 입니다.";
}
