package com.example.itemorderapplication.common.exception.dto;

import com.example.itemorderapplication.common.enumeration.ErrorType;
import com.example.itemorderapplication.common.enumeration.HttpStatus;
import lombok.Getter;


@Getter
public class ErrorResult {
    private int code;
    private HttpStatus status;
    private String message;

    public ErrorResult(ErrorType errorType) {
        this.status = errorType.getHttpStatus();
        this.code = this.status.value();
        this.message = errorType.getMsg();
    }

    public ErrorResult(ErrorType errorType,
                       String text) {
        this.status = errorType.getHttpStatus();
        this.code = this.status.value();
        this.message = ErrorType.formatErrorMsg(errorType, text);
    }

    public static class ItemErrorResult extends ErrorResult {

        public ItemErrorResult(ErrorType errorType,
                               String itemName) {
            super(errorType, itemName);
        }
    }
}
