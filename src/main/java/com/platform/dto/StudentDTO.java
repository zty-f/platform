package com.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author by Hchier
 * @Date 2022/6/6 19:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StudentDTO implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String schoolName;

    private String teamIds;

    private String tel;

    private Integer startIndex;

    private Integer pageSize;

    private String avatarPath;

    private static final long serialVersionUID = 1L;

}
