package com.platform.service.impl;

import com.platform.entity.Student;
import com.platform.entity.Teacher;
import com.platform.entity.Team;
import com.platform.mapper.ProjectMapper;
import com.platform.mapper.StudentMapper;
import com.platform.mapper.TeacherMapper;
import com.platform.mapper.TeamMapper;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.service.TeamService;
import com.platform.vo.TeamAllInfoVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:26
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    public TeamServiceImpl(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return teamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Team record) {
        return teamMapper.insert(record);
    }

    @Override
    public Team selectByPrimaryKey(Integer id) {
        return teamMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Team> selectAll() {
        return teamMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Team record) {
        return teamMapper.updateByPrimaryKey(record);
    }

    @Override
    public Team selectByName(String name) {
        return teamMapper.selectByName(name);
    }

    @Override
    public TeamInfoMTQO selectMTQOByPrimary(Integer id) {
        return teamMapper.selectMTQOByPrimary(id);
    }

    @Override
    public String selectMemberIdsByPrimaryKey(Integer id) {
        return teamMapper.selectMemberIdsByPrimaryKey(id);
    }

    @Override
    public Integer selectTeacherIdByPrimaryKey(Integer id) {
        return teamMapper.selectTeacherIdByPrimaryKey(id);
    }

    @Override
    public List<TeamInfoMTQO> selectMTQOByPrimaryLimit(Integer startIndex, Integer pageSize) {
        return teamMapper.selectMTQOByPrimaryLimit(startIndex, pageSize);
    }

    @Override
    public List<TeamAllInfoVO> getTeamByProjectId(Integer id) {
        List<TeamAllInfoVO> list = new ArrayList<>();

        //根据项目id查询出所有的team信息
        List<Team> team = projectMapper.getTeamInfoByProjectId(id);

        //组装list中TeamAllInfoVO
        for (int i = 0; i < team.size(); i++) {

            //队伍信息
            Team team_item = team.get(i);
            //队伍领导人信息
            Student student_item = studentMapper.selectByPrimaryKey(Integer.valueOf(team_item.getLeaderId()));
            //队伍指导老师的信息
            Teacher teacher_item = teacherMapper.selectByPrimaryKey(team_item.getTeacherId());

            //得到队伍成员的信息
            String memberIds = team_item.getMemberIds();
            memberIds=memberIds.replace('[','(');
            memberIds= memberIds.replace(']',')');
            List<Student> students = studentMapper.selectStudentsByIds(memberIds);


            TeamAllInfoVO teamAllInfoVO = new TeamAllInfoVO();
            //对TeamAllInfoVO设置值
            teamAllInfoVO.setTeamId(team_item.getId());
            teamAllInfoVO.setTeamName(team_item.getName());
            teamAllInfoVO.setLeaderId(Integer.parseInt(team_item.getLeaderId()));
            teamAllInfoVO.setCommitted(team_item.getCommitted());
            teamAllInfoVO.setTeacherId(team_item.getTeacherId());
            teamAllInfoVO.setProjectPath(team_item.getProjectPath());
            teamAllInfoVO.setLeaderUserName(student_item.getUsername());
            teamAllInfoVO.setLeaderRealName(student_item.getRealName());
            teamAllInfoVO.setTeacherUserName(teacher_item.getUsername());
            teamAllInfoVO.setTeacherRealName(teacher_item.getRealName());
            List<String[]> list_student = new ArrayList<>();

            for (int j = 0; j < students.size(); j++) {
                Student student = students.get(j);
                String[] student_item_string = new String[]{student.getId()+"",student.getUsername(),student.getRealName()};
                list_student.add(student_item_string);
            }

            teamAllInfoVO.setStudentsInfo(list_student);
            list.add(teamAllInfoVO);
        }

        return list;
    }


}
