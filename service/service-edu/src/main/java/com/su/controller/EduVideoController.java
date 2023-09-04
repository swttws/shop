package com.su.controller;


import com.su.client.VodClient;
import com.su.commonutils.R;
import com.su.entity.EduChapter;
import com.su.entity.EduVideo;
import com.su.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author su
 * @since 2022-05-08
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/eduvideo")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private VodClient vodClient;

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "添加小节信息")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    @ApiOperation(value = "根据id删除小节")
    @DeleteMapping("delVideo/{id}")
    public R delVideo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (videoSourceId!=null){
             vodClient.delVideo(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询小节信息")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo byId = videoService.getById(id);
        return R.ok().data("eduVideo",byId);
    }

    @ApiOperation(value = "修改小节信息")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

