package com.approval.approval.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> extends BasicResponse {

    private T data;
    private String message;

    public CommonResponse(String message) { this.message = message; }

    public CommonResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }


}
