package com.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2022/6/7 8:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TeacherDTO {
    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String teamIds;

    private String schoolName;

    private String tel;

    private String avatarPath;

    private Integer startIndex;

    private Integer pageSize;
}
