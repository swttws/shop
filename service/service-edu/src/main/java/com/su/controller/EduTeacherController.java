package com.su.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.su.commonutils.R;
import com.su.entity.EduTeacher;
import com.su.entity.vo.TeacherQuery;
import com.su.handler.GuliException;
import com.su.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-01
 */
@Api(description= "讲师管理")
@RestController
@RequestMapping("/eduservice/eduteacher")
//@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有讲师
    @ApiOperation(value ="查询所有讲师" )
    @GetMapping
    public R getAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("list",list);
    }

    //讲师逻辑删除
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public R delTeacher(@PathVariable("id") String id){
        boolean remove = eduTeacherService.removeById(id);
        if (remove){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/getTeacherPage/{current}/{limit}")
    public R getTeacherPage(@PathVariable Long current,@PathVariable Long limit){
        Page<EduTeacher> page=new Page<>(current,limit);
        eduTeacherService.page(page,null);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        //1.存入map
//        Map<String,Object> map=new HashMap<>();
//        map.put("list",records);
//        map.put("total",total);
//        return R.ok().data(map);

        //2.直接拼接
        return R.ok().data("list",records).data("total",total);
    }

    @ApiOperation(value = "带条件的分页查询讲师列表")
    @PostMapping("/getTeacherPageVo/{current}/{limit}")
    public R getTeacherPageVo(@PathVariable Long current,
                              @PathVariable Long limit,
                              //@RequestBody json串转换为实体类
                              @RequestBody TeacherQuery teacherQuery){

        //1、取出查询条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //2、判断条件是否为空，不为空拼接sql
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        Page<EduTeacher> page=new Page<>(current,limit);
        eduTeacherService.page(page,wrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        //1.存入map
//        Map<String,Object> map=new HashMap<>();
//        map.put("list",records);
//        map.put("total",total);
//        return R.ok().data(map);

        //2.直接拼接
        return R.ok().data("list",records).data("total",total);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",teacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if (update){
            return R.ok();
        }else {
            return R.error();
        }
    }

}











