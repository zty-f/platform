package com.platform.service.impl;

import com.platform.entity.*;
import com.platform.mapper.*;
import com.platform.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:25
 */
@Service("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;
    private JudgeMapper judgeMapper;
    private TeamMapper teamMapper;
    private ScoreMapper scoreMapper;
    private SummaryMapper summaryMapper;

    public AdminServiceImpl(JudgeMapper judgeMapper, TeamMapper teamMapper, ScoreMapper scoreMapper, SummaryMapper summaryMapper) {
        this.judgeMapper = judgeMapper;
        this.teamMapper = teamMapper;
        this.scoreMapper = scoreMapper;
        this.summaryMapper = summaryMapper;
    }

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Admin record) {
        return adminMapper.insert(record);
    }

    @Override
    public Admin selectByPrimaryKey(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        return adminMapper.updateByPrimaryKey(record);
    }

    @Override
    public Admin selectByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    @Override
    public boolean distribute() {
        Random random = new Random();
        List<Judge> judgeList = judgeMapper.selectAll();
        List<Team> teamList = teamMapper.selectAll();
        List<Score> distributeList = new LinkedList<>();

        for (Team team : teamList) {
            if (team.getProjectPath().isEmpty()) {
                continue;
            }
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                int randomInt;
                do {
                    randomInt = random.nextInt(judgeList.size());
                } while (!set.add(randomInt));

                distributeList.add(
                    new Score().
                        setTeamId(team.getId()).
                        setJudgeId(judgeList.get(randomInt).getId())
                );
            }
        }
        return scoreMapper.insertDistributeList(distributeList) != 0;
    }

    @Override
    public int summarize() {
        return summaryMapper.insertList(scoreMapper.summarize());
    }
}
