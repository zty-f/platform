package com.platform.service.impl;

import com.platform.mapper.SummaryMapper;
import com.platform.service.SummaryService;
import com.platform.vo.SummaryVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/7/10 16:16
 */
@Service
public class SummaryServiceImpl implements SummaryService {
    private SummaryMapper summaryMapper;

    public SummaryServiceImpl(SummaryMapper summaryMapper) {
        this.summaryMapper = summaryMapper;
    }

    @Override
    public List<SummaryVO> selectAll() {
        return summaryMapper.selectAll();
    }
}
