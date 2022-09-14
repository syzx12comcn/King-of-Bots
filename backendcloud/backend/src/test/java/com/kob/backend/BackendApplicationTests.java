package com.kob.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(new BCryptPasswordEncoder().encode("abc"));
        System.out.println(new BCryptPasswordEncoder().matches("abc", "$2a$10$MrgUN2MagDUJhuDJMY4aQ.W2WWHcPdiM6D.dbFD26VtJ6udkAVHum"));
    }

}
