package com.su.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.commonutils.R;
import com.su.commonutils.utils.JwtUtils;
import com.su.entity.TOrder;
import com.su.service.TOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 */
@Api(description = "订单模块")
@RestController
@RequestMapping("/orderservice/order")
//@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    @ApiOperation(value = "根据课程id、用户id创建订单")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId,
                         HttpServletRequest request){

        //用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo=orderService.createOrder(courseId,memberId);
        return R.ok().data("orderNo",orderNo);

    }

    @ApiOperation(value = "根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderById(@PathVariable String orderNo){
        QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        TOrder order = orderService.getOne(queryWrapper);
        return R.ok().data("order",order);
    }

    @ApiOperation(value = "根据课程id、用户id查询是否已购买课程,远程调用")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId){

        QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);

        int count = orderService.count(queryWrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }


}



