package com.onj.template_manage.DTO.Response;

import lombok.Data;

@Data
public class UserSelectResponseDTO {
    private String id;
    private String role;

    public UserSelectResponseDTO(String id, String role) {
        this.id = id;
        this.role = role;
    }
}
