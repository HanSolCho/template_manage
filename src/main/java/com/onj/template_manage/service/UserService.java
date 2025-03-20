package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.entity.User;
import com.onj.template_manage.exception.User.UserAlreadyExistsException;
import com.onj.template_manage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    @Autowired
    public UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signUP(UserSignUpRequestDTO userSignUpRequestDTO) {
        try {
            Optional<User> existingMember = userRepository.findById(userSignUpRequestDTO.getId());
            if (existingMember.isPresent()) {
                log.error("이미 존재하는 아이디: {}", userSignUpRequestDTO.getId());
                throw new UserAlreadyExistsException();
            }

            User user = User.builder()
                    .id(userSignUpRequestDTO.getId())
                    .password(passwordEncoder.encode(userSignUpRequestDTO.getPassword()))
                    .role("USER")
                    .build();

            userRepository.save(user);
            log.info("회원가입 성공 : {}", user.getId());
        } catch (UserAlreadyExistsException e) {
            log.error("회원가입 실패: 이미 존재하는 아이디입니다. Member ID: {}", userSignUpRequestDTO.getId());
            throw e;  // 이미 존재하는 아이디 예외를 그대로 던짐
        } catch (Exception e) {
            log.error("회원가입 중 예외 발생: ", e);
            throw new RuntimeException("회원가입 중 예외 발생");
        }
    }
}
