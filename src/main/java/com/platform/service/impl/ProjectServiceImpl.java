package com.platform.service.impl;

import com.platform.entity.Project;
import com.platform.mapper.ProjectMapper;
import com.platform.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:25
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return projectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Project record) {
        return projectMapper.insert(record);
    }

    @Override
    public Project selectByPrimaryKey(Integer id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Project> selectAll() {
        return projectMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Project record) {
        return projectMapper.updateByPrimaryKey(record);
    }
}
