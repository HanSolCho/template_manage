package com.onj.template_manage.exception.Item;

import com.onj.template_manage.exception.ErrorCode;
import com.onj.template_manage.exception.TemplateManageException;

public class ItemOptionIsNullException extends TemplateManageException {
    public ItemOptionIsNullException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
