package com.platform.mapper;

import com.platform.entity.Score;
import com.platform.entity.Summary;
import com.platform.vo.ScoreVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    Score selectByPrimaryKey(Integer id);

    List<Score> selectAll();

    int updateScoreByPrimaryKey(Score record);

    int insertDistributeList(List<Score> distributeList);

    List<ScoreVO> selectByJudgeId(int judgeId);

    int judgeOver();

    List<Summary> summarize();
}