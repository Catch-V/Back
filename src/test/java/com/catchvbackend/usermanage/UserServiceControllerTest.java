package com.catchvbackend.usermanage;

import com.catchvbackend.usermanage.UserRepository.UserMember.User;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceControllerTest {
    @Autowired
    UserServiceController userServiceController;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(2)
    void loginUser() {
    }

    @Test
    @Order(3)
    void saveUser() {
    }

    @Test
    @Order(4)
    void showUser() {
    }

    @Test
    @Order(6)
    void logoutUser() {
    }

    @Test
    @Order(1)
    @Transactional
    @DisplayName("저장, 전체조회하여 데이터가 잘 들어갔는지 확인")
    void registerUser(){
        Optional<User> user1 = findByEmail(user.getUserEmail());
        String msg = "중복된 이메일 입니다.";
        if (user1.isEmpty()) {
            msg = "회원가입 성공";
            log.info(msg);
            String sql = "insert into User(id,userEmail,userPassword,loginstatus) values(?,?,?,?)";
            jdbcTemplate.update(
                    sql,
                    0, user.getUserEmail(), user.getUserPassword(), 0);
        } else {
            log.info(msg);
        }
        return null;
    }



    }

    @Test
    @Order(5)
    void editUser() {
    }

    @Test
    @Transactional
    @DisplayName("삭제, 전체데이터갯수 변화 확인")
    void outUser() {
    }
}