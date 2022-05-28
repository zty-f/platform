package com.platform.service.impl;

import com.platform.entity.Teacher;
import com.platform.mapper.TeacherMapper;
import com.platform.service.TeacherService;
import com.platform.vo.TeacherVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:26
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return teacherMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Teacher record) {
        return teacherMapper.insert(record);
    }

    @Override
    public Teacher selectByPrimaryKey(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Teacher> selectAll() {
        return teacherMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Teacher record) {
        return teacherMapper.updateByPrimaryKey(record);
    }

    @Override
    public Teacher selectByUsername(String username) {
        return teacherMapper.selectByUsername(username);
    }

    @Override
    public String selectPasswordByUsername(String username) {
        return teacherMapper.selectPasswordByUsername(username);
    }

    @Override
    public String selectTeamIdsById(Integer id) {
        return teacherMapper.selectTeamIdsById(id);
    }

    @Override
    public String selectTeamIdsByUsername(String username) {
        return teacherMapper.selectTeamIdsByUsername(username);
    }

    @Override
    public TeacherVO selectVOByUsername(String username) {
        return teacherMapper.selectVOByUsername(username);
    }
}
