package com.onj.template_manage.exception.template;

import com.onj.template_manage.exception.ErrorCode;
import com.onj.template_manage.exception.TemplateManageException;

public class TemplateNotRegisterFromUserException extends TemplateManageException {
    public TemplateNotRegisterFromUserException() {
        super(ErrorCode.TEMPLATE_NOT_FOUND);
    }
}