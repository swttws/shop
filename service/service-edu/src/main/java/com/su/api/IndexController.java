package com.su.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.commonutils.R;
import com.su.entity.EduCourse;
import com.su.entity.EduTeacher;
import com.su.service.EduCourseService;
import com.su.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "首页显示")
@RestController
@RequestMapping("/eduservice/index")
//@CrossOrigin
public class IndexController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "首页面展示课程信息（8条）、讲师信息（4条）")
    @GetMapping("getCourseTeacherList")
    public R getCourseTeacherList(){
        //查询8条课程信息
        QueryWrapper<EduCourse> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("gmt_create");
        courseQueryWrapper.eq("status","Normal");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseQueryWrapper);
        //四条讲师信息
        QueryWrapper<EduTeacher> teacherQueryWrapper=new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("gmt_create");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherQueryWrapper);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }


}
