package com.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Score implements Serializable {
    private Integer id;

    private Integer teamId;

    private Integer judgeId;

    private Float score;

    private static final long serialVersionUID = 1L;

}