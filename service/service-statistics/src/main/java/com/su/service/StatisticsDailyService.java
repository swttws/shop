package com.su.service;

import com.su.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-18
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStaDaily(String day);

    Map<String, Object> getStaDaily(String type, String begin, String end);
}
