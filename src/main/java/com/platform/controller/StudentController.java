package com.platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.base.BaseController;
import com.platform.base.Const;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.entity.Student;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.service.StudentService;
import com.platform.service.TeacherService;
import com.platform.service.TeamService;
import com.platform.vo.StudentVO;
import com.platform.vo.TeamInfoVO;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 18:38
 */
@RestController
public class StudentController extends BaseController {
    private StudentService studentService;
    private ObjectMapper objectMapper;
    private TeamService teamService;

    public StudentController(StudentService studentService, ObjectMapper objectMapper, TeamService teamService) {
        this.studentService = studentService;
        this.objectMapper = objectMapper;
        this.teamService = teamService;
    }

    @PostMapping("/api/student/register")
    public RestResponse studentRegister(@RequestBody Student student) {
        if (studentService.selectPasswordByUsername(student.getUsername()) != null) {
            return RestResponse.fail(ResponseCode.USER_ALREADY_EXIST.getCode(), ResponseCode.USER_ALREADY_EXIST.getMessage());
        }
        if (studentService.insert(student) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.REGISTER_FAIL.getCode(), ResponseCode.REGISTER_FAIL.getMessage());
    }

    //    @PostMapping("/studentLogin")
    @PostMapping("/api/student/login")
    public RestResponse studentLogin(@RequestBody Student student, HttpServletRequest request) {
        System.out.println("haha");

        String password = studentService.selectPasswordByUsername(student.getUsername());
        if (password == null) {
            return RestResponse.fail(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getMessage());
        }
        if (studentService.selectPasswordByUsername(student.getUsername()).equals(student.getPassword())) {
            setCurrentUser(request, Const.ROLE_STUDENT.getMessage(), student.getUsername());
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.AUTHENTICATION_FAIL.getCode(), ResponseCode.AUTHENTICATION_FAIL.getMessage());
    }

    @PostMapping("/api/student/logout")
    public RestResponse studentLogout(HttpServletRequest request) {
        removeCurrentUser(request);
        return RestResponse.ok();
    }

    @GetMapping("/api/student/getByUsername/{username}")
    public RestResponse getStudentByUsername(@PathVariable String username, HttpServletRequest request) {
        StudentVO studentVO = studentService.selectVOByUsername(username);
        if (studentVO == null) {
            return RestResponse.fail(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getMessage());
        }
        String[] currentUser = getCurrentUser(request);
        if (Const.ROLE_STUDENT.getMessage().equals(currentUser[0]) && username.equals(currentUser[1])) {
            return RestResponse.ok(studentVO);
        }
        studentVO.setPassword(null).setTeamIds(null).setTel(null).setTel(null).setAvatarPath(null);
        return RestResponse.ok(studentVO);
    }

    @GetMapping("/api/student/getCurrentUser")
    public RestResponse getCurrentUserTest(HttpServletRequest request) {
        return RestResponse.ok(getCurrentUser(request));
    }

    @PostMapping("/api/student/teamsInfo")
    public RestResponse teamsInfo(HttpServletRequest request) throws JsonProcessingException {
        String[] currentUser = getCurrentUser(request);
        String teamIdsJson = studentService.selectTeamIdsByUsername(currentUser[1]);
        List<Integer> teamIdsList = objectMapper.readValue(teamIdsJson, List.class);
        List<TeamInfoVO> teamsInfoVO = new LinkedList<>();
        for (Integer id : teamIdsList) {
            TeamInfoMTQO teamInfoMTQO = teamService.selectMTQOByPrimary(id);
            TeamInfoVO teamInfoVO = MODEL_MAPPER.map(teamInfoMTQO, TeamInfoVO.class);
            teamInfoVO.setIsLeader(teamInfoVO.getLeaderUsername().equals(currentUser[1]));
            List<Integer> memberIdList = objectMapper.readValue(teamInfoMTQO.getMemberIds(), List.class);
            teamInfoVO.setMemberList(new LinkedList<>());
            for (Integer studentId : memberIdList) {
                teamInfoVO.getMemberList().add(new String[]{String.valueOf(studentId), studentService.selectRealNameByPrimaryKey(studentId)});
            }
            teamsInfoVO.add(teamInfoVO);
        }
        return RestResponse.ok(teamsInfoVO);
    }


}
