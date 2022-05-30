package com.platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.base.BaseController;
import com.platform.base.Const;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.dto.TeamDTO;
import com.platform.entity.Student;
import com.platform.entity.Teacher;
import com.platform.entity.Team;
import com.platform.service.StudentService;
import com.platform.service.TeacherService;
import com.platform.service.TeamService;
import com.platform.utils.JSONUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 22:32
 */
@RestController
@RequestMapping("/api/team")
public class TeamController extends BaseController {
    private TeamService teamService;
    private ObjectMapper objectMapper;
    private StudentService studentService;
    private JSONUtil jsonUtil;
    private TeacherService teacherService;

    public TeamController(TeamService teamService, ObjectMapper objectMapper, StudentService studentService, JSONUtil jsonUtil, TeacherService teacherService) {
        this.teamService = teamService;
        this.objectMapper = objectMapper;
        this.studentService = studentService;
        this.jsonUtil = jsonUtil;
        this.teacherService = teacherService;
    }

    @Transactional
    @PostMapping("/add")
    public RestResponse add(@RequestBody TeamDTO teamDTO, HttpServletRequest request) throws JsonProcessingException {
        if (teamService.selectByName(teamDTO.getName()) != null) {
            return RestResponse.fail(ResponseCode.TEAM_ALREADY_EXIST.getCode(), ResponseCode.TEAM_ALREADY_EXIST.getMessage());
        }
        String[] currentUser = getCurrentUser(request);
        Team team = MODEL_MAPPER.map(teamDTO, Team.class);
        team.setMemberIds(objectMapper.writeValueAsString(teamDTO.getMemberList())).setLeaderId(currentUser[2]);
        int insert = teamService.insert(team);
        if (insert == 0) {
            return RestResponse.fail(ResponseCode.ADD_FAIL.getCode(), ResponseCode.ADD_FAIL.getMessage());
        }
        for (Integer id : teamDTO.getMemberList()) {
            String teamIdsStr = studentService.selectTeamIdsByUsername(studentService.selectUsernameByPrimaryKey(id));
            teamIdsStr = jsonUtil.jsonArrayAdd(teamIdsStr, team.getId());
            int update = studentService.updateByPrimaryKey(new Student().setId(id).setTeamIds(teamIdsStr));
            if (update == 0) {
                return RestResponse.fail(ResponseCode.ADD_FAIL.getCode(), ResponseCode.ADD_FAIL.getMessage());
            }
        }
        String teamIdsStr = teacherService.selectTeamIdsById(teamDTO.getTeacherId());
        teamIdsStr = jsonUtil.jsonArrayAdd(teamIdsStr, team.getId());
        Teacher teacher = new Teacher().setId(teamDTO.getTeacherId()).setTeamIds(teamIdsStr);
        if (teacherService.updateByPrimaryKey(teacher) == 0) {
            return RestResponse.fail(ResponseCode.ADD_FAIL.getCode(), ResponseCode.ADD_FAIL.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getById/{id}")
    public RestResponse getById(@PathVariable Integer id) {
        Team team = teamService.selectByPrimaryKey(id);
        return RestResponse.ok(team);
    }

    @Transactional
    @GetMapping("/addStudent/{teamId}/{studentId}")
    public RestResponse addStudent(@PathVariable Integer teamId, @PathVariable Integer studentId) throws JsonProcessingException {
        String memberIdsStr = teamService.selectMemberIdsByPrimaryKey(teamId);
        memberIdsStr = jsonUtil.jsonArrayAdd(memberIdsStr, studentId);
        Team team = new Team().setId(teamId).setMemberIds(memberIdsStr);

        String teamIdsStr = studentService.selectTeamIdsByUsername(studentService.selectUsernameByPrimaryKey(studentId));
        teamIdsStr = jsonUtil.jsonArrayAdd(teamIdsStr, teamId);
        if (teamService.updateByPrimaryKey(team) == 1 && studentService.updateByPrimaryKey(new Student().setId(studentId).setTeamIds(teamIdsStr)) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.ADD_FAIL.getCode(), ResponseCode.ADD_FAIL.getMessage());
    }

    @Transactional
    @GetMapping("/deleteStudent/{teamId}/{studentId}")
    public RestResponse deleteStudent(@PathVariable Integer teamId, @PathVariable Integer studentId) throws JsonProcessingException {
        String memberIdsStr = teamService.selectMemberIdsByPrimaryKey(teamId);
        memberIdsStr = jsonUtil.jsonArrayDelete(memberIdsStr, studentId);
        Team team = new Team().setId(teamId).setMemberIds(memberIdsStr);

        String teamIdsStr = studentService.selectTeamIdsByUsername(studentService.selectUsernameByPrimaryKey(studentId));
        teamIdsStr = jsonUtil.jsonArrayDelete(teamIdsStr, teamId);
        if (teamService.updateByPrimaryKey(team) == 1 && studentService.updateByPrimaryKey(new Student().setId(studentId).setTeamIds(teamIdsStr)) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.ADD_FAIL.getCode(), ResponseCode.ADD_FAIL.getMessage());
    }


    @Transactional
    @GetMapping("/updateTeacher/{teamId}/{newTeacherId}//{oldTeacherId}")
    public RestResponse updateTeacher(@PathVariable Integer teamId, @PathVariable Integer newTeacherId, @PathVariable Integer oldTeacherId) throws JsonProcessingException {
        Team team = new Team().setId(teamId).setTeacherId(newTeacherId);

        String teamIdsStr = teacherService.selectTeamIdsById(oldTeacherId);
        teamIdsStr = jsonUtil.jsonArrayDelete(teamIdsStr, teamId);
        Teacher oldTeacher = new Teacher().setId(oldTeacherId).setTeamIds(teamIdsStr);

        teamIdsStr = teacherService.selectTeamIdsById(newTeacherId);
        teamIdsStr = jsonUtil.jsonArrayAdd(teamIdsStr, teamId);
        Teacher newTeacher = new Teacher().setId(newTeacherId).setTeamIds(teamIdsStr);


        if (teamService.updateByPrimaryKey(team) == 1 && teacherService.updateByPrimaryKey(oldTeacher) == 1 && teacherService.updateByPrimaryKey(newTeacher) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.ADD_FAIL.getCode(), ResponseCode.ADD_FAIL.getMessage());
    }
}
