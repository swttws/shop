package com.su.controller;


import com.su.commonutils.R;
import com.su.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-18
 */
@Api(description = "统计分析管理")
@RestController
@RequestMapping("/staservice/stadaily")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    @ApiOperation("生成统计数据")
    @PostMapping("createStaDaily/{day}")
    public R createStaDaily(@PathVariable String day){
        dailyService.createStaDaily(day);
        return R.ok();
    }

    @ApiOperation("查询统计数据")
    @GetMapping("/getStaDaily/{type}/{begin}/{end}")
    public R getStaDaily(@PathVariable String type,
                         @PathVariable String begin,
                         @PathVariable String end){
        Map<String,Object> map=dailyService.getStaDaily(type,begin,end);

        return R.ok().data(map);
    }


}

