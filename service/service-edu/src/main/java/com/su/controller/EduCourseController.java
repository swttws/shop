package com.su.controller;


import com.su.commonutils.R;
import com.su.entity.EduCourse;
import com.su.entity.vo.CourseInfoForm;
import com.su.entity.vo.CoursePublishVo;
import com.su.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-06
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/educourse")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId= eduCourseService.addCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }

    @ApiOperation(value = "根据id查询课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id){
        CourseInfoForm courseInfoForm=eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoForm);
    }

    @ApiOperation(value ="修改课程信息" )
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询课程发布信息")
    @GetMapping("getCoursePublishById/{id}")
    public R getCoursePublishById(@PathVariable String id){
        CoursePublishVo coursePublishVo=eduCourseService.getCoursePublishById(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation(value = "根据id更改状态（发布课程）")
    @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = eduCourseService.getById(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @ApiOperation(value = "课程列表展示")
    @GetMapping("getCoursePublishInfo")
    public R getCoursePublishInfo(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "根据id删除课程信息")
    @DeleteMapping("delCourseInfo/{id}")
    public R delCourseInfo(@PathVariable String id){
        eduCourseService.delCourseInfo(id);
        return R.ok();
    }


}











