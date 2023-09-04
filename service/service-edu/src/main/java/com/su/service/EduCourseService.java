package com.su.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.su.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.su.entity.vo.CourseInfoForm;
import com.su.entity.vo.CoursePublishVo;
import com.su.entity.vo.CourseQueryVo;
import com.su.entity.vo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-06
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishById(String id);

    void delCourseInfo(String id);

    Map<String, Object> getCourseApiPageVo(Page<EduCourse> page, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseWebVo(String courseId);
}
