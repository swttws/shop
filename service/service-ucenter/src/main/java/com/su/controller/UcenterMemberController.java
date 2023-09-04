package com.su.controller;


import com.su.commonutils.R;
import com.su.commonutils.utils.JwtUtils;
import com.su.commonutils.vo.UcenterMemberForOrder;
import com.su.entity.UcenterMember;
import com.su.entity.vo.LoginVo;
import com.su.entity.vo.RegisterVo;
import com.su.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-12
 */
@Api(description = "前台用户会员管理")
@RestController
@RequestMapping("/ucenterservice/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token=memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("getUcenterByToken")
    public R getUcenterByToken(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("ucenterMember",member);
    }

    @ApiOperation(value = "根据memberId获取用户信息,跨模块")
    @GetMapping("getUcenterInfoForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterInfoForOrder(
            @PathVariable("memberId") String memberId){
        UcenterMember member = memberService.getById(memberId);
        UcenterMemberForOrder ucenterMemberForOrder = new UcenterMemberForOrder();
        BeanUtils.copyProperties(member,ucenterMemberForOrder);
        return ucenterMemberForOrder;
    }

    @ApiOperation(value = "统计注册人数，远程调用")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
        Integer count=memberService.countRegister(day);
        return R.ok().data("countRegister",count);
    }




}












