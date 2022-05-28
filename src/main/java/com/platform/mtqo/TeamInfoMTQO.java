package com.platform.mtqo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/26 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfoMTQO {
    private Integer id;
    private String name;
    private String memberIds;
    private String projectName;
    private Integer leaderId;
    private String leaderUsername;
    private String leaderRealName;
    private Boolean committed;
    private Integer teacherId;
    private String teacherName;
    private String projectPath;

}
