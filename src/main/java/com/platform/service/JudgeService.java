package com.platform.service;

import com.platform.entity.Judge;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 11:45
 */
public interface JudgeService {
    int deleteByPrimaryKey(Integer id);

    int insert(Judge record);

    Judge selectByPrimaryKey(Integer id);

    List<Judge> selectAll();

    int updateByPrimaryKey(Judge record);

    Judge selectByUsername(String username);

}
