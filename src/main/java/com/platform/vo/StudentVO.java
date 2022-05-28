package com.platform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author by Hchier
 * @Date 2022/5/25 19:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StudentVO implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String schoolName;

    private String teamIds;

    private String tel;

    private String avatarPath;

    private static final long serialVersionUID = 1L;

}
