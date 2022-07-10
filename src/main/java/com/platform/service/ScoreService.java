package com.platform.service;

import com.platform.entity.Score;
import com.platform.vo.ScoreVO;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 12:08
 */
public interface ScoreService {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    Score selectByPrimaryKey(Integer id);

    List<Score> selectAll();

    int updateScoreByPrimaryKey(Score record);

    int insertDistributeList(List<Score> distributeList);

    List<ScoreVO> selectByJudgeId(int judgeId);

    boolean judgeOver();
}
