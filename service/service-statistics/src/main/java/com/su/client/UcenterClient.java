package com.su.client;

import com.su.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter")
public interface UcenterClient {
    //统计注册人数，远程调用"
    @GetMapping("/ucenterservice/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
