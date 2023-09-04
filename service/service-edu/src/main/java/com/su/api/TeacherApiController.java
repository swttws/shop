package com.su.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.su.commonutils.R;
import com.su.entity.EduCourse;
import com.su.entity.EduTeacher;
import com.su.entity.vo.TeacherQuery;
import com.su.service.EduCourseService;
import com.su.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前台讲师展示")
@RestController
@RequestMapping("/eduservice/teacherapi")
//@CrossOrigin
public class TeacherApiController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/getTeacherApiPage/{current}/{limit}")
    public R getTeacherApiPage(@PathVariable Long current,
                              @PathVariable Long limit){
        Page<EduTeacher> page=new Page<>(current,limit);
        Map<String,Object> map=teacherService.getTeacherApiPage(page);
        return R.ok().data(map);
    }

    @ApiOperation(value = "前台讲师详情信息查询")
    @GetMapping("getTeacherCourseById/{id}")
    public R getTeacherCourseById(@PathVariable String id){
        //讲师信息
        EduTeacher eduTeacher = teacherService.getById(id);
        //相关课程信息
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        List<EduCourse> courseList = courseService.list(queryWrapper);

        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);

    }

}
