package com.platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.platform.base.BaseController;
import com.platform.base.Const;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.entity.Admin;
import com.platform.entity.Team;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.service.AdminService;
import com.platform.service.StudentService;
import com.platform.service.TeamService;
import com.platform.utils.JSONUtil;
import com.platform.vo.StudentVO;
import com.platform.vo.TeamInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by Hchier
 * @Date 2022/5/25 16:20
 */
@RestController
public class AdminController extends BaseController {
    private AdminService adminService;
    private TeamService teamService;
    private ObjectMapper objectMapper;
    private StudentService studentService;

    @Autowired
    public void setAdminService(AdminService adminService, TeamService teamService, ObjectMapper objectMapper, StudentService studentService) {
        this.adminService = adminService;
        this.teamService = teamService;
        this.objectMapper = objectMapper;
        this.studentService = studentService;
    }

    @PostMapping("/api/admin/add")
    public RestResponse addAdmin(@RequestBody Admin admin) {
        if (adminService.selectByUsername(admin.getUsername()) != null) {
            return RestResponse.fail(ResponseCode.USER_ALREADY_EXIST.getCode(), ResponseCode.USER_ALREADY_EXIST.getMessage());
        }
        int res = adminService.insert(admin);
        System.out.println(" " + admin);
        if (res == 1) {
            return RestResponse.ok(admin);
        } else {
            return RestResponse.fail(ResponseCode.REGISTER_FAIL.getCode(), ResponseCode.REGISTER_FAIL.getMessage());
        }
    }

    @PostMapping("/api/admin/login")
    public RestResponse adminLogin(@RequestBody Admin admin, HttpServletRequest request) {
        Admin adminSelected = adminService.selectByUsername(admin.getUsername());
        if (adminSelected == null) {
            return RestResponse.fail(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getMessage());
        }
        if (adminSelected.getPassword().equals(admin.getPassword())) {
            setCurrentUser(request, Const.ROLE_ADMIN.getMessage(), admin.getUsername());
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.AUTHENTICATION_FAIL.getCode(), ResponseCode.AUTHENTICATION_FAIL.getMessage());
    }

    @GetMapping("/api/admin/allAdmins")
    public RestResponse getAllAdmin() {
        List<Admin> admins = adminService.selectAll();
        return RestResponse.ok(admins);
    }

    @GetMapping("/api/admin/getTeams/{pageNum}/{pageSize}")
    public RestResponse getTeams(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        List<TeamInfoMTQO> list = teamService.selectMTQOByPrimaryLimit(pageNum * pageSize, pageSize);
        List<TeamInfoVO> teamsInfoVO = list.stream().map(team -> {
            TeamInfoVO teamInfoVO = MODEL_MAPPER.map(team, TeamInfoVO.class);
            teamInfoVO.setMemberList(new LinkedList<>());
            teamInfoVO.setProjectPath(null);
            teamInfoVO.setIsLeader(false);
            List<Integer> memberIdList = null;
            try {
                memberIdList = objectMapper.readValue(team.getMemberIds(), List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            for (Integer id : memberIdList) {
                teamInfoVO.getMemberList().add(new String[]{String.valueOf(id), studentService.selectRealNameByPrimaryKey(id)});
            }
            return teamInfoVO;
        }).collect(Collectors.toList());
        return RestResponse.ok(teamsInfoVO);
    }

    @GetMapping("/api/admin/getTeamById/{id}")
    public RestResponse getTeamInfoById(@PathVariable Integer id) {
        System.out.println("test");
        return null;
    }

    @GetMapping("/api/admin/getStudents/{pageNum}/{pageSize}")
    public RestResponse getStudents(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        List<StudentVO> studentVOS = studentService.selectVOsLimit(pageNum * pageSize, pageSize);
        return null;
    }
}

//
//