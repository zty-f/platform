package com.platform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/26 13:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfoVO {
    private Integer id;
    private String name;
    private Boolean isLeader;
    private List<String[]> memberList;
    private String projectName;
    private Integer leaderId;
    private String leaderUsername;
    private String leaderRealName;
    private Boolean committed;
    private Integer teacherId;
    private String teacherName;
    private String projectPath;
}
