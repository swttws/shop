package com.su.client;

import com.su.commonutils.vo.UcenterMemberForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据memberId获取用户信息,跨模块
    @GetMapping("/ucenterservice/member/getUcenterInfoForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterInfoForOrder(
            @PathVariable("memberId") String memberId);
}
