package com.platform.service;

import com.platform.dto.StudentDTO;
import com.platform.entity.Student;
import com.platform.vo.StudentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:24
 */
public interface StudentService {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    Student selectByPrimaryKey(Integer id);

    List<Student> selectAll();

    int updateByPrimaryKey(Student record);

    StudentVO selectVOByUsername(String username);

    List<StudentVO> selectVOsQuery(StudentDTO studentDTO);

    String selectPasswordByUsername(String username);

    String selectTeamIdsByUsername(String username);

    String selectUsernameByPrimaryKey(Integer id);

    String selectRealNameByPrimaryKey(Integer id);

    List<StudentVO> selectVOsLimit(Integer startIndex, Integer pageSize);
}
