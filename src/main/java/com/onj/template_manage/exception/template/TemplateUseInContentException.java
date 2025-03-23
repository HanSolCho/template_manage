package com.onj.template_manage.exception.template;

import com.onj.template_manage.exception.ErrorCode;
import com.onj.template_manage.exception.TemplateManageException;

public class TemplateUseInContentException extends TemplateManageException {
    public TemplateUseInContentException() {
        super(ErrorCode.TEMPLATE_USING_CONTENT);
    }
}