package com.platform.controller;

import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.entity.School;
import com.platform.service.SchoolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by Hchier
 * @Date 2022/5/27 11:29
 */
@RestController
@RequestMapping("/api/school")
public class SchoolController {
    private SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/getById/{id}")
    public RestResponse getById(@PathVariable Integer id) {
        School school = schoolService.selectByPrimaryKey(id);
        if (school == null) {
            return RestResponse.fail(ResponseCode.SCHOOL_NOT_EXIST.getCode(), ResponseCode.SCHOOL_NOT_EXIST.getMessage());
        }
        return RestResponse.ok(school);
    }
}
