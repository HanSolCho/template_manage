package com.onj.template_manage.DTO.Request;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private String id;
    private String presentPassword;
    private String updatePassword;
}
