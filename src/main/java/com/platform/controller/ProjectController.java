package com.platform.controller;

import com.platform.base.BaseController;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.entity.Project;
import com.platform.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyk
 */
@RestController
public class ProjectController extends BaseController {


    @Autowired
    private ProjectService projectService;


    @GetMapping("/api/project/selectTitleById/{id}")
    public RestResponse selectTitleById(@PathVariable Integer id) {
        String name = projectService.selectNameById(id);
        return RestResponse.ok(name);
    }

    @GetMapping("/api/project/selectById/{id}")
    public RestResponse selectById(@PathVariable Integer id) {
        Project project = projectService.selectByPrimaryKey(id);
        if (project == null) {
            return RestResponse.fail(ResponseCode.PROJECT_NOT_EXIST.getCode(), ResponseCode.PROJECT_NOT_EXIST.getMessage());
        }
        return RestResponse.ok(project);
    }
}
