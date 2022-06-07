package com.platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.platform.base.BaseController;
import com.platform.base.Const;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.dto.StudentDTO;
import com.platform.dto.TeacherDTO;
import com.platform.entity.Admin;
import com.platform.entity.Team;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.service.AdminService;
import com.platform.service.StudentService;
import com.platform.service.TeacherService;
import com.platform.service.TeamService;
import com.platform.utils.JSONUtil;
import com.platform.vo.StudentVO;
import com.platform.vo.TeacherVO;
import com.platform.vo.TeamAllInfoVO;
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
    private TeacherService teacherService;

    @Autowired
    public void setAdminService(AdminService adminService, TeamService teamService, ObjectMapper objectMapper, StudentService studentService, TeacherService teacherService) {
        this.adminService = adminService;
        this.teamService = teamService;
        this.objectMapper = objectMapper;
        this.studentService = studentService;
        this.teacherService = teacherService;
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
            setCurrentUser(request, Const.ROLE_ADMIN.getMessage(), admin.getUsername(), adminSelected.getId());
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.AUTHENTICATION_FAIL.getCode(), ResponseCode.AUTHENTICATION_FAIL.getMessage());
    }

    @PostMapping("/api/admin/logout")
    public RestResponse adminLogout(HttpServletRequest request) {
        removeCurrentUser(request);
        return RestResponse.ok();
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
            String projectPath = teamInfoVO.getProjectPath();
            if (projectPath != null && !"".equals(projectPath)) {
                String[] split = projectPath.split("\\\\");
                teamInfoVO.setProjectPath(split[split.length - 1]);
            }
            return teamInfoVO;
        }).collect(Collectors.toList());
        return RestResponse.ok(teamsInfoVO);
    }

    @PostMapping("/api/admin/getTeamsQuery")
    public RestResponse getTeamsQuery(@RequestBody TeamInfoMTQO teamInfoMTQO) {
        System.out.println(teamInfoMTQO.toString());
        List<TeamInfoMTQO> list = teamService.selectMTQOQuery(teamInfoMTQO);
        List<TeamInfoVO> teamsInfoVO = list.stream().map(team -> {
            TeamInfoVO teamInfoVO = MODEL_MAPPER.map(team, TeamInfoVO.class);
            teamInfoVO.setMemberList(new LinkedList<>());
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
            String projectPath = teamInfoVO.getProjectPath();
            if (projectPath != null && !"".equals(projectPath)) {
                String[] split = projectPath.split("\\\\");
                teamInfoVO.setProjectPath(split[split.length - 1]);
            }
            return teamInfoVO;
        }).collect(Collectors.toList());
        return RestResponse.ok(teamsInfoVO);
    }

    @GetMapping("/api/admin/getTeamById/{id}")
    public RestResponse getTeamInfoById(@PathVariable Integer id) {
        TeamInfoMTQO teamInfoVO = teamService.selectMTQOByPrimary(id);
        return RestResponse.ok(teamInfoVO);
    }

    @GetMapping("/api/admin/getStudents/{pageNum}/{pageSize}")
    public RestResponse getStudents(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        List<StudentVO> studentVOS = studentService.selectVOsLimit(pageNum * pageSize, pageSize);
        return RestResponse.ok(studentVOS);
    }

    @PostMapping("/api/admin/getStudentsQuery")
    public RestResponse getStudentsQuery(@RequestBody StudentDTO studentDTO) {
        System.out.println(studentDTO.toString());
        List<StudentVO> studentVOS = studentService.selectVOsQuery(studentDTO);
        return RestResponse.ok(studentVOS);
    }

    /**
     * 根据ProjectId查询有关队伍的全部信息
     *
     * @param id
     * @return
     */
    @GetMapping("/api/admin/getTeamByProjectId/{id}")
    public RestResponse getTeamByProjectId(@PathVariable Integer id) {
        List<TeamAllInfoVO> list = teamService.getTeamByProjectId(id);
        return RestResponse.ok(list);
    }

    @GetMapping("/api/admin/getTeachers/{pageNum}/{pageSize}")
    public RestResponse getTeachers(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        List<TeacherVO> teacherVOList = teacherService.selectVOsLimit(pageNum * pageSize, pageSize);
        return RestResponse.ok(teacherVOList);
    }

    @PostMapping("/api/admin/getTeachersQuery")
    public RestResponse getTeachersQuery(@RequestBody TeacherDTO teacherDTO){
        List<TeacherVO> teacherVOList = teacherService.selectVOsQuery(teacherDTO);
        return RestResponse.ok(teacherVOList);
    }

}

//
//