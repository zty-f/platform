package com.platform.mapper;

import com.platform.entity.Team;
import com.platform.mtqo.TeamInfoMTQO;
import com.platform.vo.TeamAllInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    Team selectByPrimaryKey(Integer id);

    List<Team> selectAll();

    int updateByPrimaryKey(Team record);

    Team selectByName(String name);

    TeamInfoMTQO selectMTQOByPrimary(Integer id);

    List<TeamInfoMTQO> selectMTQOByPrimaryLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    List<TeamInfoMTQO> selectMTQOQuery(TeamInfoMTQO teamInfoMTQO);

    String selectMemberIdsByPrimaryKey(Integer id);

    Integer selectTeacherIdByPrimaryKey(Integer id);


    /**
     * 根据projectId查询有关team的全部信息
     * @param id
     * @return
     */
    TeamAllInfoVO getTeamByProjectId(Integer id);
}