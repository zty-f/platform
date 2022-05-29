package com.platform.service;

import com.platform.entity.Team;
import com.platform.mtqo.TeamInfoMTQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:25
 */
public interface TeamService {
    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    Team selectByPrimaryKey(Integer id);

    List<Team> selectAll();

    int updateByPrimaryKey(Team record);

    Team selectByName(String name);

    TeamInfoMTQO selectMTQOByPrimary(Integer id);

    String selectMemberIdsByPrimaryKey(Integer id);

    Integer selectTeacherIdByPrimaryKey(Integer id);

    List<TeamInfoMTQO> selectMTQOByPrimaryLimit( Integer startIndex, Integer pageSize);



}
