package com.platform.service;

import com.platform.entity.School;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:24
 */
public interface SchoolService {
    int deleteByPrimaryKey(Integer id);

    int insert(School record);

    School selectByPrimaryKey(Integer id);

    List<School> selectAll();

    int updateByPrimaryKey(School record);
}
