package com.spring.toyproject.repository;

import com.spring.toyproject.domain.entity.User;
import com.spring.toyproject.repository.base.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // DB 테스트를 위한 간소설정 (속도가 @SpringBootTest에 비해 월등히 빠름)
//@ActiveProfiles("test") // application-test.yml을 읽어라
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .username("testUser1")
                .email("test1@example.com")
                .password("password123")
                .build();

        userRepository.save(user);
    }


    @Test
    @DisplayName("사용자명으로 조회 테스트")
    void findByUsernameTest() {
        //given
        String username = "testUser1";
        //when
        User foundUser = userRepository.findByUsername(username).orElseThrow();
        //then
        assertNotNull(foundUser);
        assertEquals("test1@example.com", foundUser.getEmail());
    }

    @Test
    @DisplayName("이메일로 조회 테스트")
    void findByEmailTest() {
        //given
        String email = "test1@example.com";
        //when
        User foundUser = userRepository.findByEmail(email).orElseThrow();

        //then
//        assertNotNull(foundUser); // JUnit5
        assertThat(foundUser).isNotNull(); // JUnit4

//        assertEquals("testUser", foundUser.getUsername()); // JUnit5
        assertThat(foundUser.getUsername()).isEqualTo("testUser1"); // JUnit4
    }


    @Test
    @DisplayName("사용자명 중복확인 테스트")
    void existsUsernameTest() {
        //given
        String username = "monkey";
        //when
        boolean flag = userRepository.existsByUsername(username);
        //then
        assertThat(flag).isFalse();
//        assertFalse(flag);
    }

    @Test
    @DisplayName("이메일 중복확인 테스트")
    void existsEmailTest() {
        //given
        String email = "test1@example.com";
        //when
        boolean flag = userRepository.existsByEmail(email);
        //then
        assertThat(flag).isTrue();
//        assertFalse(flag);
    }


}