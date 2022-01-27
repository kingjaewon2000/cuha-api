package com.cju.cuhaapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionMessage {

    private int code;
    private String message;

    public ExceptionMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
