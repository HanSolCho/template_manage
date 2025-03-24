package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.DTO.Request.UserUpdateRequestDTO;
import com.onj.template_manage.jwt.JwtToken;
import com.onj.template_manage.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/user")
@Tag(name = "회원 정보", description = "회원 정보에 대한 기능입니다.")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입")
    public ResponseEntity<?> signUpUser(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        userService.signUP(userSignUpRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    public JwtToken signIn(@RequestBody UserSignUpRequestDTO userSignInRequestDTO){
        JwtToken jwtToken = userService.signIn(userSignInRequestDTO);
        return jwtToken;
    }

    @PutMapping("/update")
    @Operation(summary = "회원 정보 변경")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO){
        userService.updateUser(userUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴")
    public ResponseEntity<?> deleteUser(@RequestBody UserSignUpRequestDTO userDeleteRequestDTO){
        userService.deleteMember(userDeleteRequestDTO);
        return ResponseEntity.ok().build();
    }
}
