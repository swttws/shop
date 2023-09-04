package com.su.client;

import com.su.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //删除视频
    //请求url必须写全,参数注解不能省略
    @DeleteMapping("/eduvod/vod/delVideo/{videoId}")
    public R delVideo(@PathVariable String videoId);

    //批量删除视频
    @DeleteMapping("/eduvod/vod/delVideoList")
    public R delVideos(@RequestParam("videoIdList") List<String> videoIdList);

}
