package com.su.controller;

import com.su.commonutils.R;
import com.su.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "文件管理")
@RestController
@RequestMapping("/ossservice/fileoss")
//@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file){
        //1.获取文件
        //2.调用接口上传文件,获取url
        String url=fileService.uploadFileOss(file);
        return R.ok().data("url",url);
    }

}
