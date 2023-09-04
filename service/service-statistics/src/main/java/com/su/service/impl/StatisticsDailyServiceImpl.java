package com.su.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.client.UcenterClient;
import com.su.commonutils.R;
import com.su.entity.StatisticsDaily;
import com.su.mapper.StatisticsDailyMapper;
import com.su.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //生成统计数据
    @Override
    public void createStaDaily(String day) {
        //删除数据,一天数据只能一条
        QueryWrapper<StatisticsDaily> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);

        //统计数据
        R r = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        StatisticsDaily daily=new StatisticsDaily();
        daily.setRegisterNum(countRegister);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);

    }

    //查询统计数据
    @Override
    public Map<String, Object> getStaDaily(String type, String begin, String end) {

        //查询数据
        QueryWrapper<StatisticsDaily> queryWrapper=new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        queryWrapper.select("date_calculated",type);
        List<StatisticsDaily> dailyList = baseMapper.selectList(queryWrapper);

        //遍历查询结果
        Map<String, Object> staDailyMap=new HashMap<>();
        List<String> xList=new ArrayList<>();
        List<Integer> yList=new ArrayList<>();

        for (int i = 0; i < dailyList.size(); i++) {
            StatisticsDaily daily = dailyList.get(i);
            //封装x轴数据
            xList.add(daily.getDateCalculated());
            //封装y轴数据
            switch (type){
                case "register_num":
                    yList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    yList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    yList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    yList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        staDailyMap.put("xList",xList);
        staDailyMap.put("yList",yList);

        return staDailyMap;
    }
}
