package com.onj.template_manage.repository;

import com.onj.template_manage.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("멤버 등록 Test")
    void saveMemberTest(){
        //given
        User user = User.builder().id("id").password("123").role("USER").build();

        //when
        User result = userRepository.save(user);

        //then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());

    }
}
