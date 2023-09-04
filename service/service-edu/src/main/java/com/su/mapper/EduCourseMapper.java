package com.su.mapper;

import com.su.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.su.entity.vo.CoursePublishVo;
import com.su.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2022-05-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishById(String id);

    CourseWebVo getCourseWebVo(String courseId);
}
