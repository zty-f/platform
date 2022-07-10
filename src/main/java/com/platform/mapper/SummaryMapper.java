package com.platform.mapper;

import com.platform.entity.Summary;
import com.platform.vo.SummaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SummaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Summary record);

    Summary selectByPrimaryKey(Integer id);

    List<SummaryVO> selectAll();

    int updateByPrimaryKey(Summary record);

    int insertList(List<Summary> summaryList);
}