package com.onj.template_manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.DTO.Request.UserUpdateRequestDTO;
import com.onj.template_manage.config.TestSecurityConfig;
import com.onj.template_manage.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void signUpUser() throws Exception {
        //given
        UserSignUpRequestDTO userSignUpRequestDTO = new UserSignUpRequestDTO();
        userSignUpRequestDTO.setId("id");
        userSignUpRequestDTO.setPassword("password");

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSignUpRequestDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).signUP(userSignUpRequestDTO);
    }

    @Test
    void signIn() throws Exception {
        //given
        UserSignUpRequestDTO userSignInRequestDTO = new UserSignUpRequestDTO();
        userSignInRequestDTO.setId("id");
        userSignInRequestDTO.setPassword("password");

        //when & then: 예상되는 결과 검증
        mockMvc.perform(post("/onj/template-manage/user/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSignInRequestDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).signIn(userSignInRequestDTO);
    }

    @Test
    void updateUser() throws Exception {
        //given
        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO();
        userUpdateRequestDTO.setId("id");
        userUpdateRequestDTO.setPresentPassword("password");
        userUpdateRequestDTO.setUpdatePassword("updatepassword");

        //when & then: 예상되는 결과 검증
        mockMvc.perform(put("/onj/template-manage/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequestDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(userUpdateRequestDTO);
    }

    @Test
    void deleteUser() throws Exception {
        //given
        UserSignUpRequestDTO userDeleteDTO = new UserSignUpRequestDTO();
        userDeleteDTO.setId("id");
        userDeleteDTO.setPassword("password");

        //when & then: 예상되는 결과 검증
        mockMvc.perform(delete("/onj/template-manage/user/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDeleteDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteMember(userDeleteDTO);
    }
}