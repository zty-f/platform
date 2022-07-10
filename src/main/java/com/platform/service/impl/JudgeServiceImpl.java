package com.platform.service.impl;

import com.platform.entity.Judge;
import com.platform.mapper.JudgeMapper;
import com.platform.service.JudgeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 11:48
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    private JudgeMapper judgeMapper;

    public JudgeServiceImpl(JudgeMapper judgeMapper) {
        this.judgeMapper = judgeMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Judge record) {
        return 0;
    }

    @Override
    public Judge selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Judge> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Judge record) {
        return 0;
    }

    @Override
    public Judge selectByUsername(String username) {
        return judgeMapper.selectByUsername(username);
    }
}
