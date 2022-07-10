package com.platform.service.impl;

import com.platform.entity.Score;
import com.platform.mapper.ScoreMapper;
import com.platform.service.ScoreService;
import com.platform.vo.ScoreVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 12:09
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    private ScoreMapper scoreMapper;

    public ScoreServiceImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Score record) {
        return 0;
    }

    @Override
    public Score selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Score> selectAll() {
        return null;
    }

    @Override
    public int updateScoreByPrimaryKey(Score record) {
        return scoreMapper.updateScoreByPrimaryKey(record);
    }

    @Override
    public int insertDistributeList(List<Score> distributeList) {
        return 0;
    }

    @Override
    public List<ScoreVO> selectByJudgeId(int judgeId) {
        return scoreMapper.selectByJudgeId(judgeId);
    }

    @Override
    public boolean judgeOver() {
        return scoreMapper.judgeOver() == 0;
    }
}
