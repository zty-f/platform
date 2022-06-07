package com.platform.service;

import com.platform.entity.Teacher;
import com.platform.vo.TeacherVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:24
 */
public interface TeacherService {
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

    List<TeacherVO> selectVOsLimit(Integer startIndex, Integer pageSize);
}
