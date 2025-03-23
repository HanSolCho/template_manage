package com.onj.template_manage.exception.content;

import com.onj.template_manage.exception.ErrorCode;
import com.onj.template_manage.exception.TemplateManageException;

public class ContentNotRegisterFromUserException extends TemplateManageException {
    public ContentNotRegisterFromUserException() {
        super(ErrorCode.CONTENT_NOT_FOUND);
    }
}
