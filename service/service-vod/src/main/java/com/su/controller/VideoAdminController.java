package com.su.controller;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.su.commonutils.R;
import com.su.handler.GuliException;
import com.su.utils.AliyunVodSDKUtils;
import com.su.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Api(description = "视频管理")
@RestController
@RequestMapping("/eduvod/vod")
//@CrossOrigin
public class VideoAdminController {

    @ApiOperation(value = "视频上传")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title=originalFilename.substring(0,originalFilename.lastIndexOf("."));

            UploadStreamRequest request=
                    new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID,
                            ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                            title,originalFilename,inputStream);
            //创建上传对象
            UploadVideoImpl uploadVideo=new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            //获取视频id
            String videoId = response.getVideoId();
            return R.ok().data("videoId",videoId).message("视频上传成功");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new GuliException(20001,"上传视频失败");
        }
    }

    @ApiOperation(value = "删除视频")
    @DeleteMapping("delVideo/{videoId}")
    public R delVideo(@PathVariable String videoId){

        try {
            //初始化客户端对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request=new DeleteVideoRequest();
            //设置参数
            request.setVideoIds(videoId);
            //调用客户端发送请求
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }

    }

    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delVideoList")
    public R delVideos(@RequestParam("videoIdList")List<String> videoIdList){
        try {
            //初始化客户端对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request=new DeleteVideoRequest();
            //设置参数
            String videoIds= StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);
            //调用客户端发送请求
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"批量删除视频失败");
        }
    }

    @ApiOperation(value = "根据视频id获取播放凭证")
    @GetMapping("getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }

}









