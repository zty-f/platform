package com.platform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2022/7/10 16:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryVO {
    private Integer id;
    private Integer teamId;
    private String teamName;
    private Float score;
}
