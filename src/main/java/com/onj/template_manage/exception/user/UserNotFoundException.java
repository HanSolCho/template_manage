package com.onj.template_manage.exception.user;

import com.onj.template_manage.exception.ErrorCode;
import com.onj.template_manage.exception.TemplateManageException;

public class UserNotFoundException extends TemplateManageException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
