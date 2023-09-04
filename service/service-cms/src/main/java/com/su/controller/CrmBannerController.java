package com.su.controller;


import com.su.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-10
 */
@Api(description = "后台banner展示")
@RestController
@RequestMapping("/cmsservice/crmbanner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

}

