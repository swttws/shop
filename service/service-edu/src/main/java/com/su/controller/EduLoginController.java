package com.su.controller;

import com.su.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(description= "模拟登录")
@RestController
@RequestMapping("/eduuser")
//@CrossOrigin//解决跨域问题
public class EduLoginController {

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}
