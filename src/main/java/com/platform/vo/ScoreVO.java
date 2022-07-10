package com.platform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2022/7/10 13:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreVO {
    private Integer id;
    private Integer teamId;
    private Integer judgeId;
    private Float score;
    private String projectPath;
}
