package com.platform.mapper;

import com.platform.entity.Judge;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface JudgeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Judge record);

    Judge selectByPrimaryKey(Integer id);

    List<Judge> selectAll();

    int updateByPrimaryKey(Judge record);

    Judge selectByUsername(String username);
}