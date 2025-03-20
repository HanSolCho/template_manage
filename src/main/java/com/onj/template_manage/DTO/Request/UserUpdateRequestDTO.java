package com.onj.template_manage.DTO.Request;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    String id;
    String presentPassword;
    String updatePassword;
}
