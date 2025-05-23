package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.user.UserSignUpRequestDTO;
import com.onj.template_manage.DTO.Request.user.UserUpdateRequestDTO;
import com.onj.template_manage.DTO.Response.user.UserSelectResponseDTO;
import com.onj.template_manage.entity.User;
import com.onj.template_manage.exception.User.UserAlreadyExistsException;
import com.onj.template_manage.exception.User.UserNotFoundException;
import com.onj.template_manage.exception.User.UserNotValidatePasswordException;
import com.onj.template_manage.jwt.JwtToken;
import com.onj.template_manage.jwt.JwtTokenProvider;
import com.onj.template_manage.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Transactional
    public JwtToken signIn(UserSignUpRequestDTO userSignInRequestDTO) {
        try {
            validateMember(userSignInRequestDTO.getId(), userSignInRequestDTO.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userSignInRequestDTO.getId(), userSignInRequestDTO.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            return jwtToken;

        } catch (Exception e) {
            log.error("로그인 실패: ", e);
            throw new RuntimeException("로그인 실패");
        }
    }

    public void validateMember(String id, String password) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotValidatePasswordException();
        }
    }

    public Optional<UserSelectResponseDTO> selectUserById(String id){
        Optional<User> user =  userRepository.findById(id);
        if (user.isPresent()) {
            UserSelectResponseDTO userSelectResponseDTO = new UserSelectResponseDTO(user.get().getId(),user.get().getRole());
            return Optional.of(userSelectResponseDTO);
        } else{
            log.error("회원 조회 실패: 존재하지 않는 회원 ID: {}", id);
            throw new UserNotFoundException();
        }
    }

    public void updateUser(UserUpdateRequestDTO userUpdateRequestDTO){
        validateMember(userUpdateRequestDTO.getId(), userUpdateRequestDTO.getPresentPassword());

        Optional<User> validUser = userRepository.findById(userUpdateRequestDTO.getId());

        if (validUser.isPresent()) {
            User user = validUser.get();
            user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getUpdatePassword()));
            userRepository.save(user);
            log.info("회원 정보 수정 성공: {}", user.getId());
        } else {
            log.error("회원 정보 수정 실패: 존재하지 않는 회원 ID: {}", userUpdateRequestDTO.getId());
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void deleteMember(UserSignUpRequestDTO userDeleteRequestDTO){

        validateMember(userDeleteRequestDTO.getId(), userDeleteRequestDTO.getPassword());

        Optional<User> deleteUser = userRepository.findById(userDeleteRequestDTO.getId());

        if (deleteUser.isPresent()) {
            userRepository.deleteById(deleteUser.get().getNum());  // 제품이 존재하면 삭제
            log.info("회원 삭제 성공: {}", userDeleteRequestDTO.getId());
        }else {
            log.error("회원 삭제 실패: 존재하지 않는 회원 ID: {}", userDeleteRequestDTO.getId());
            throw new UserNotFoundException();
        }
    }


}
