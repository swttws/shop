package com.su.client;

import com.su.commonutils.vo.CourseWebVoForOrder;
import com.su.commonutils.vo.UcenterMemberForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu")
public interface EduClient {

    //url需要补全，参数一定要一样
    //根据课程id查询课程相关信息，跨模块调用"
    @GetMapping("/eduservice/courseapi/getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(
            @PathVariable("courseId") String courseId);


}
