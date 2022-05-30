package com.platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.base.BaseController;
import com.platform.base.Const;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.entity.Teacher;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.service.SchoolService;
import com.platform.service.StudentService;
import com.platform.service.TeacherService;
import com.platform.service.TeamService;
import com.platform.vo.TeacherVO;
import com.platform.vo.TeamInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 19:22
 */
@RestController
public class TeacherController extends BaseController {
    private TeacherService teacherService;
    private SchoolService schoolService;
    private ObjectMapper objectMapper;
    private TeamService teamService;
    private StudentService studentService;

    public TeacherController(TeacherService teacherService, SchoolService schoolService, ObjectMapper objectMapper, TeamService teamService, StudentService studentService) {
        this.teacherService = teacherService;
        this.schoolService = schoolService;
        this.objectMapper = objectMapper;
        this.teamService = teamService;
        this.studentService = studentService;
    }

    @PostMapping("/api/teacher/register")
    public RestResponse teacherRegister(@RequestBody Teacher teacher) {
        System.out.println(11);
        if (teacherService.selectByUsername(teacher.getUsername()) != null) {
            return RestResponse.fail(ResponseCode.USER_ALREADY_EXIST.getCode(), ResponseCode.USER_ALREADY_EXIST.getMessage());
        }
        if (teacherService.insert(teacher) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.REGISTER_FAIL.getCode(), ResponseCode.REGISTER_FAIL.getMessage());
    }


    @PostMapping("/api/teacher/login")
    public RestResponse teacherLogin(@RequestBody Teacher teacher, HttpServletRequest request) {
        Teacher teacherSelected = teacherService.selectByUsername(teacher.getUsername());
        if (teacherSelected == null) {
            return RestResponse.fail(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getMessage());
        }
        if (teacherSelected.getPassword().equals(teacher.getPassword())) {
            setCurrentUser(request, Const.ROLE_TEACHER.getMessage(), teacher.getUsername(), teacherSelected.getId());
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.AUTHENTICATION_FAIL.getCode(), ResponseCode.AUTHENTICATION_FAIL.getMessage());
    }

    @PostMapping("/api/teacher/logout")
    public RestResponse teacherLogout(HttpServletRequest request) {
        removeCurrentUser(request);
        return RestResponse.ok();
    }

    @GetMapping("/api/teacher/getByUsername/{username}")
    public RestResponse getByUsername(@PathVariable String username, HttpServletRequest request) {
        String[] currentUser = getCurrentUser(request);
        Teacher teacher = teacherService.selectByUsername(username);
        if (teacher == null) {
            return RestResponse.fail(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getMessage());
        }
        TeacherVO teacherVO = MODEL_MAPPER.map(teacher, TeacherVO.class);
        teacherVO.setSchoolName(schoolService.selectByPrimaryKey(teacher.getSchoolId()).getName());
        if (Const.ROLE_TEACHER.getMessage().equals(currentUser[0]) && username.equals(currentUser[1])) {
            return RestResponse.ok(teacherVO);
        }
        teacherVO.setPassword(null).setTeamIds(null).setTel(null).setAvatarPath(null);
        return RestResponse.ok(teacherVO);
    }

    @PostMapping("/api/teacher/teamsInfo")
    public RestResponse teamsInfo(HttpServletRequest request) throws JsonProcessingException {
        String[] currentUser = getCurrentUser(request);
        String teamIdsJson = teacherService.selectTeamIdsByUsername(currentUser[1]);
        List<Integer> teamIdsList = objectMapper.readValue(teamIdsJson, List.class);
        List<TeamInfoVO> teamsInfoVO = new LinkedList<>();
        for (Integer id : teamIdsList) {
            TeamInfoMTQO teamInfoMTQO = teamService.selectMTQOByPrimary(id);
            TeamInfoVO teamInfoVO = MODEL_MAPPER.map(teamInfoMTQO, TeamInfoVO.class);
            teamInfoVO.setIsLeader(false);
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
