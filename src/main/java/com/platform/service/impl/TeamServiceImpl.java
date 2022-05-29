package com.platform.service.impl;

import com.platform.entity.Team;
import com.platform.mapper.TeamMapper;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:26
 */
@Service
public class TeamServiceImpl implements TeamService {
    private TeamMapper teamMapper;

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


}
