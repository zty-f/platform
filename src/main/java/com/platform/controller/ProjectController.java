package com.platform.controller;

import com.platform.base.BaseController;
import com.platform.base.RestResponse;
import com.platform.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController extends BaseController {


    @Autowired
    private ProjectService projectService;


    @GetMapping("/api/project/selectNameById/{id}")
    public RestResponse selectNameById (@PathVariable Integer id) {

        String name = projectService.selectNameById(id);
        return RestResponse.ok(name);
    }
}
