package com.platform.controller;

import com.platform.base.BaseController;
import com.platform.base.Const;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import com.platform.entity.Judge;
import com.platform.entity.Score;
import com.platform.service.JudgeService;
import com.platform.service.ScoreService;
import com.platform.vo.ScoreVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 11:41
 */
@RestController
public class JudgeController extends BaseController {
    private JudgeService judgeService;
    private ScoreService scoreService;

    public JudgeController(JudgeService judgeService, ScoreService scoreService) {
        this.judgeService = judgeService;
        this.scoreService = scoreService;
    }

    @PostMapping("/api/judge/login")
    public RestResponse login(@RequestBody Judge judge, HttpServletRequest request) {
        Judge judgeSelected = judgeService.selectByUsername(judge.getUsername());
        if (judgeSelected.getPassword().equals(judge.getPassword())) {
            setCurrentUser(request, Const.ROLE_JUDGE.getMessage(), judge.getUsername(), judgeSelected.getId());
            return RestResponse.ok();
        }
        return RestResponse.fail(ResponseCode.AUTHENTICATION_FAIL.getCode(), ResponseCode.AUTHENTICATION_FAIL.getMessage());
    }

    @PostMapping("/api/judge/logout")
    public RestResponse logout(HttpServletRequest request) {
        removeCurrentUser(request);
        return RestResponse.ok();
    }

    @GetMapping("/api/judge/getTeams")
    public RestResponse getTeams(HttpServletRequest request) {
        String[] currentUser = getCurrentUser(request);
        List<ScoreVO> scoreList = scoreService.selectByJudgeId(Integer.valueOf(currentUser[2]));
        for (ScoreVO scoreVO : scoreList) {
            String[] split = scoreVO.getProjectPath().split("\\\\");
            scoreVO.setProjectPath(split[split.length - 1]);
        }
        if (scoreList.size() == 0) {
            return RestResponse.fail(ResponseCode.NO_DATA.getCode(), ResponseCode.NO_DATA.getMessage());
        }
        return RestResponse.ok(scoreList);
    }

    @PostMapping("/api/judge/updateScore")
    public RestResponse updateScore(@RequestBody Score score, HttpServletRequest request) {
        return scoreService.updateScoreByPrimaryKey(score) == 1 ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.UPDATE_SCORE_FAIL.getCode(), ResponseCode.UPDATE_SCORE_FAIL.getMessage());
    }
}
