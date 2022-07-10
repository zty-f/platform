package com.platform;

import com.platform.mapper.JudgeMapper;
import com.platform.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlatformApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JudgeMapper judgeMapper;


    @Test
    void contextLoads() {
        System.out.println(adminService.distribute());
    }

}
