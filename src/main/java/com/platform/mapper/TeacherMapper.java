package com.platform.mapper;

import com.platform.entity.Teacher;
import com.platform.vo.TeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    List<Teacher> selectAll();

    int updateByPrimaryKey(Teacher record);

    Teacher selectByUsername(String username);

    String selectPasswordByUsername(String username);

    String selectTeamIdsById(Integer id);

    String selectTeamIdsByUsername(String username);

    TeacherVO selectVOByUsername(String username);
}