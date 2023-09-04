package com.su.controller;


import com.su.commonutils.R;
import com.su.handler.GuliException;
import com.su.service.TPayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-16
 */
@Api(description = "支付模块")
@RestController
@RequestMapping("/orderservice/paylog")
//@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    @ApiOperation(value = "根据订单编号生成二维码")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map<String,Object> map=payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        //调用微信接口查询支付状态
        Map<String,String> map=payLogService.queryPayStatus(orderNo);
        //判断支付状态
        if (map==null){
            return R.error().message("支付出错");
        }
        if ("SUCCESS".equals(map.get("trade_state"))){
            //订单状态更改,记录支付日志
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");

    }

}

