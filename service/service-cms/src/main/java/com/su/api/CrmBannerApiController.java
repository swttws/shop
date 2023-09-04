package com.su.api;

import com.su.commonutils.R;
import com.su.entity.CrmBanner;
import com.su.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//前台
@Api(description = "前台banner展示")
@RestController
//@CrossOrigin
@RequestMapping("/cmsservice/banner")
public class CrmBannerApiController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "查询所有banner信息")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> banners = bannerService.getAllBannner();
        return R.ok().data("bannerList",banners);
    }



}
