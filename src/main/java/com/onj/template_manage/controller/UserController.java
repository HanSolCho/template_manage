package com.onj.template_manage.controller;

import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.DTO.Request.UserUpdateRequestDTO;
import com.onj.template_manage.jwt.JwtToken;
import com.onj.template_manage.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onj/template-manage/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        userService.signUP(userSignUpRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody UserSignUpRequestDTO userSignInRequestDTO){
        JwtToken jwtToken = userService.signIn(userSignInRequestDTO);
        return jwtToken;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO){
        userService.updateUser(userUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserSignUpRequestDTO userDeleteRequestDTO){
        userService.deleteMember(userDeleteRequestDTO);
        return ResponseEntity.ok().build();
    }
}
