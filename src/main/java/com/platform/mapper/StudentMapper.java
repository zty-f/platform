package com.platform.mapper;

import com.platform.entity.Student;
import com.platform.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    Student selectByPrimaryKey(Integer id);

    List<Student> selectAll();

    int updateByPrimaryKey(Student record);

    StudentVO selectVOByUsername(String username);

    String selectPasswordByUsername(String username);

    String selectTeamIdsByUsername(String username);

    String selectUsernameByPrimaryKey(Integer id);

    String selectRealNameByPrimaryKey(Integer id);
}