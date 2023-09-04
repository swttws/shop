package com.su.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.su.client.VodClient;
import com.su.entity.EduChapter;
import com.su.entity.EduCourse;
import com.su.entity.EduCourseDescription;
import com.su.entity.EduVideo;
import com.su.entity.vo.CourseInfoForm;
import com.su.entity.vo.CoursePublishVo;
import com.su.entity.vo.CourseQueryVo;
import com.su.entity.vo.CourseWebVo;
import com.su.handler.GuliException;
import com.su.mapper.EduCourseMapper;
import com.su.service.EduChapterService;
import com.su.service.EduCourseDescriptionService;
import com.su.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.su.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加课程信息
    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        //添加课程信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);//数据转换
        int insert = baseMapper.insert(eduCourse);
        if (insert==0){
            throw new GuliException(20001,"创建课程失败");
        }
        //获取课程id
        String id = eduCourse.getId();//数据库插入成功会自动填充id
        //添加课程描述信息
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return id;
    }

    //根据id查询课程信息
    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        CourseInfoForm courseInfoForm=new CourseInfoForm();
        //根据id查询课程信息
        EduCourse eduCourse = baseMapper.selectById(id);
        //封装课程信息
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        //根据id查询课程描述信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        //封装课程描述信息
        courseInfoForm.setDescription(eduCourseDescription.getDescription());
        return courseInfoForm;
    }

    //修改课程id
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //复制课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        //更新课程数据
        int update = baseMapper.updateById(eduCourse);
        //更新是否成功
        if (update==0){
            throw new GuliException(20001,"更新失败");
        }
        //更新课程描述
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    //根据课程id查询课程发布信息
    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo=
                baseMapper.getCoursePublishById(id);
        return coursePublishVo;
    }

    //根据课程id删除相关信息
    @Override
    public void delCourseInfo(String id) {
        //删除视频
        QueryWrapper<EduVideo> videoWrapper=new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        List<EduVideo> list = videoService.list(videoWrapper);
        //构造视频id集合
        List<String> list1=new ArrayList<>();
        for (EduVideo eduVideo : list) {
            list1.add(eduVideo.getVideoSourceId());
        }
        if (list1.size()>0){
            vodClient.delVideos(list1);
        }
        //删除小节
        QueryWrapper<EduVideo> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        videoService.remove(videoQueryWrapper);
        //删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        chapterService.remove(chapterQueryWrapper);
        //删除课程描述
        eduCourseDescriptionService.removeById(id);
        //删除课程
        int delete = baseMapper.deleteById(id);
        if (delete==0){
            throw new GuliException(20001,"删除失败");
        }
    }

    //带条件的分页查询课程列表
    @Override
    public Map<String, Object> getCourseApiPageVo(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        //获取查询条件
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();

        //判断条件是否为空,不为空，拼接sql
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)){
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(priceSort)){
            queryWrapper.orderByDesc("price");
        }

        //分页查询
        baseMapper.selectPage(pageParam,queryWrapper);

        //封装数据
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //课程详情查询
    @Override
    public CourseWebVo getCourseWebVo(String courseId) {
        return baseMapper.getCourseWebVo(courseId);
    }


}
