package com.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 22:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Integer id;

    private String name;

    private List<Integer> memberList;

    private Integer projectId;

    private String leaderId;

    private Boolean committed;

    private Integer teacherId;

    private String projectPath;
}
