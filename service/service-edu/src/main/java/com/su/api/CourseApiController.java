package com.su.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.su.client.OrderClient;
import com.su.commonutils.R;
import com.su.commonutils.utils.JwtUtils;
import com.su.commonutils.vo.CourseWebVoForOrder;
import com.su.entity.EduCourse;
import com.su.entity.vo.ChapterVo;
import com.su.entity.vo.CourseQueryVo;
import com.su.entity.vo.CourseWebVo;
import com.su.service.EduChapterService;
import com.su.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(description = "前台课程显示")
@RestController
@RequestMapping("/eduservice/courseapi")
//@CrossOrigin
public class CourseApiController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "带查询条件的分页查询课程列表")
    @PostMapping("/getCourseApiPageVo/{current}/{limit}")
    public R getCourseApiPageVo(@PathVariable Long current,
                             @PathVariable Long limit,
                             //@RequestBody json串转换为实体类
                             @RequestBody CourseQueryVo courseQueryVo){
        Page<EduCourse> page=new Page<>(current,limit);
        Map<String,Object> map=courseService.getCourseApiPageVo(page,courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询详情信息")
    @GetMapping("getCourseWebInfo/{courseId}")
    public R getCourseWebInfo(@PathVariable String courseId,
                              HttpServletRequest request){
        //查询课程信息存入CourseWebVo
        CourseWebVo courseWebVo=courseService.getCourseWebVo(courseId);
        //查询课程大纲信息
        List<ChapterVo> chapterVideoList=chapterService.getChapterVideoById(courseId);

        //根据课程id，用户id查询课程是否购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, memberId);

        return R.ok().data("courseWebVo",courseWebVo).
                data("chapterVideoList",chapterVideoList).
                data("isBuyCourse",isBuyCourse);
    }

    @ApiOperation(value = "根据课程id查询课程相关信息，跨模块调用")
    @GetMapping("getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(
            @PathVariable("courseId") String courseId){
        //查询课程信息
        CourseWebVo courseWebVo=courseService.getCourseWebVo(courseId);
        CourseWebVoForOrder courseWebVoForOrder = new CourseWebVoForOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoForOrder);
        return courseWebVoForOrder;
    }


}
