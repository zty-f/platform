package com.platform.vo;

import com.platform.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2022/5/25 22:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TeacherVO extends Teacher {
    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String teamIds;

    private String schoolName;

    private String tel;

    private String avatarPath;
}
