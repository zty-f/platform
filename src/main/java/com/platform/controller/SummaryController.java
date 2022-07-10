package com.platform.controller;

import com.platform.base.RestResponse;
import com.platform.service.SummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by Hchier
 * @Date 2022/7/10 16:16
 */
@RestController
public class SummaryController {
    private SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/api/summary/getAll")
    public RestResponse getAll() {
        return RestResponse.ok(summaryService.selectAll());
    }
}
