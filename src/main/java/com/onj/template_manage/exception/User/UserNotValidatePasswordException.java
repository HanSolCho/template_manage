package com.onj.template_manage.exception.User;

import com.onj.template_manage.exception.ErrorCode;
import com.onj.template_manage.exception.TemplateManageException;

public class UserNotValidatePasswordException extends TemplateManageException {
    public UserNotValidatePasswordException() {
        super(ErrorCode.USER_FAIL_VALIDATE);
    }
}
