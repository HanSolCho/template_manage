package com.onj.template_manage.service;

import com.onj.template_manage.DTO.Request.UserSignUpRequestDTO;
import com.onj.template_manage.entity.User;
import com.onj.template_manage.exception.User.UserAlreadyExistsException;
import com.onj.template_manage.jwt.JwtToken;
import com.onj.template_manage.jwt.JwtTokenProvider;
import com.onj.template_manage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    @Spy
    @InjectMocks
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager; // AuthenticationManager mock
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder; // AuthenticationManagerBuilder mock 객체

    @Mock
    private JwtTokenProvider jwtTokenProvider; // JwtTokenProvider mock

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("멤버 등록 성공")
    void signUpMemberSuccess() {
        //given
        User user = User.builder()
                .num(1L)
                .id("user1")
                .password("1234")
                .role("USER")
                .build();

        UserSignUpRequestDTO userSignUpRequestDTO = new UserSignUpRequestDTO();
        userSignUpRequestDTO.setId(user.getId());
        userSignUpRequestDTO.setPassword("1234");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("password");

        //when
        userService.signUP(userSignUpRequestDTO);

        //then
        verify(userRepository, times(1)).save(any(User.class)); // save 메소드가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("회원 가입 실패 (중복 아이디)")
    void signUpMemberFailDuplicateId() {

        // given: 이미 존재하는 ID일 경우
        User user = User.builder()
                .num(1L)
                .id("user1")
                .password("1234")
                .role("USER")
                .build();

        UserSignUpRequestDTO userSignUpRequestDTO = new UserSignUpRequestDTO();
        userSignUpRequestDTO.setId(user.getId());
        userSignUpRequestDTO.setPassword("1234");

        when(userRepository.findById(userSignUpRequestDTO.getId())).thenReturn(Optional.of(user));

        // when & then: 예외가 발생하는지 확인
        assertThatThrownBy(() -> userService.signUP(userSignUpRequestDTO))
                .isInstanceOf(UserAlreadyExistsException.class);

        // verify: memberRepository.save가 호출되지 않았음을 확인
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void signIn() {
//        // given
//        User user = User.builder()
//                .num(1L)
//                .id("user1")
//                .password("1234")
//                .role("USER")
//                .build();
//        UserSignUpRequestDTO userSignUpRequestDTO = new UserSignUpRequestDTO();
//        userSignUpRequestDTO.setId(user.getId());
//        userSignUpRequestDTO.setPassword(user.getPassword());
//        // Authentication mock 객체
//        Authentication authentication = mock(Authentication.class);
//
//        // JwtToken mock 객체
//        JwtToken jwtToken = new JwtToken("mockJwtToken","mockJwtToken","mockJwtToken");
//
//        // validateMember 메소드 mock 처리
//        doNothing().when(userService).validateMember(user.getId(), user.getPassword()); // validateMember가 호출되면 예외 없이 그냥 지나가도록 설정
//
//        // authenticationManager.authenticate()가 인증을 성공한 Authentication 객체를 반환하도록 mock 처리
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(authentication);
//
//        // jwtTokenProvider.generateToken()이 mockJwtToken을 반환하도록 설정
//        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn(jwtToken);
//
//        // when
//        JwtToken result = userService.signIn(userSignUpRequestDTO);
//
//        // then
//        assertThat(result).isNotNull(); // JwtToken이 null이 아니어야 함
//        assertThat(result.getAccessToken()).isEqualTo("mockJwtToken"); // 반환된 토큰이 mockJwtToken이어야 함
//
//        // verify
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class)); // authenticate 메소드가 호출되었는지 확인
//        verify(jwtTokenProvider).generateToken(authentication); // generateToken 메소드가 호출되었는지 확인
    }

    @Test
    void validateMember() {
        // given:
        String password = "password";
        String databasePassword = "password";
        when(passwordEncoder.matches(password, databasePassword)).thenReturn(true);

        // when:
        boolean validCheck = passwordEncoder.matches(password, databasePassword);

        // then:
        assertThat(validCheck).isTrue();
        // verify:
        verify(passwordEncoder).matches(password, databasePassword);
    }
}
