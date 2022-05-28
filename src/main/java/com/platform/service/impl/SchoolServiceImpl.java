package com.platform.service.impl;

import com.platform.entity.School;
import com.platform.mapper.SchoolMapper;
import com.platform.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:26
 */
@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return schoolMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(School record) {
        return schoolMapper.insert(record);
    }

    @Override
    public School selectByPrimaryKey(Integer id) {
        return schoolMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<School> selectAll() {
        return schoolMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(School record) {
        return schoolMapper.updateByPrimaryKey(record);
    }
}
