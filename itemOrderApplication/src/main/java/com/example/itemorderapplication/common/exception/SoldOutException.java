package com.example.itemorderapplication.common.exception;

import com.example.itemorderapplication.common.enumeration.ErrorType;
import com.example.itemorderapplication.common.exception.dto.ErrorResult;
import lombok.Getter;

@Getter
public class SoldOutException extends RuntimeException {
    private ErrorResult errorResult;

    public SoldOutException(String itemName) {
        this.errorResult = new ErrorResult.ItemErrorResult(ErrorType.INSUFFICIENT_STOCK_QUANTITY, itemName);
    }

}
