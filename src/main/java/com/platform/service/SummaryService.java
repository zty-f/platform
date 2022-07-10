package com.platform.service;

import com.platform.vo.SummaryVO;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 16:15
 */
public interface SummaryService {
    List<SummaryVO> selectAll();
}
