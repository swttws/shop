package com.su.controller;

import com.su.commonutils.R;
import com.su.service.SmsService;
import com.su.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(description = "邮箱发送")
@RestController
@RequestMapping("/edusms/sms")
//@CrossOrigin //跨域
public class SmsApiController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "短信发送")
    @GetMapping("sends/{phone}")
    public R send(@PathVariable String phone){
        //从redis查询是否有发送过验证码
        String code = redisTemplate.opsForValue().get(phone);
        //验证验证码是否存在，存在直接返回
        if (code!=null){
            return R.ok();
        }
        //验证码不存在，生成验证码
        String codes = RandomUtil.getCode();
        //调用接口服务发送短信
        Map<String,String> map=new HashMap<>();
        map.put("code",codes);
        boolean isSend=smsService.sendSmsPhone(phone,codes,map);
        //发送成功，验证码存入redis,时效5分钟
        if (isSend){
            redisTemplate.opsForValue().set(phone,codes,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
           return R.error();
        }

    }

    @ApiOperation(value = "邮箱发送")
    @GetMapping("send/{email}")
    public R sendEmail(@PathVariable String email){
        //从redis中检查是否有验证码存在
        String isCode = redisTemplate.opsForValue().get(email);
        //验证码存在，直接返回
        if (isCode!=null)
            return R.error();
        //生成验证码
        String code = RandomUtil.getCode();
        //调用邮箱发送验证码service
        boolean isSuccess=smsService.sendSmsEmail(email,code);
        //判断验证码是否发送成功，成功将验证码存入redis，时效为5分钟
        if (isSuccess){
            redisTemplate.opsForValue().set(email,code,5,TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error();
        }
    }

}
