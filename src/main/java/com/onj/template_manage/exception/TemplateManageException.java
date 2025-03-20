package com.onj.template_manage.exception;

import lombok.Data;

@Data
public class TemplateManageException extends RuntimeException {
    private final ErrorCode errorCode;
    public TemplateManageException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
