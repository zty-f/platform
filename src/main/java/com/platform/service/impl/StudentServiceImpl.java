package com.platform.service.impl;

import com.platform.dto.StudentDTO;
import com.platform.entity.Student;
import com.platform.mapper.StudentMapper;
import com.platform.service.StudentService;
import com.platform.vo.StudentVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:26
 */
@Service
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return studentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Student record) {
        return studentMapper.insert(record);
    }

    @Override
    public Student selectByPrimaryKey(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Student record) {
        return studentMapper.updateByPrimaryKey(record);
    }

    @Override
    public StudentVO selectVOByUsername(String username) {
        return studentMapper.selectVOByUsername(username);
    }

    @Override
    public List<StudentVO> selectVOsQuery(StudentDTO studentDTO) {
        return studentMapper.selectVOsQuery(studentDTO);
    }


    @Override
    public String selectPasswordByUsername(String username) {
        return studentMapper.selectPasswordByUsername(username);
    }

    @Override
    public String selectTeamIdsByUsername(String username) {
        return studentMapper.selectTeamIdsByUsername(username);
    }

    @Override
    public String selectUsernameByPrimaryKey(Integer id) {
        return studentMapper.selectUsernameByPrimaryKey(id);
    }

    @Override
    public String selectRealNameByPrimaryKey(Integer id) {
        return studentMapper.selectRealNameByPrimaryKey(id);
    }

    @Override
    public List<StudentVO> selectVOsLimit(Integer startIndex, Integer pageSize) {
        return studentMapper.selectVOsLimit(startIndex, pageSize);
    }


}
