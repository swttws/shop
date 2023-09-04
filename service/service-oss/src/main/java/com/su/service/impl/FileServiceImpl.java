package com.su.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.su.handler.GuliException;
import com.su.service.FileService;
import com.su.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFileOss(MultipartFile file) {

        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtil.BUCKET_NAME;

        String fileName=file.getOriginalFilename();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //文件流获取
            InputStream inputStream = file.getInputStream();
            //优化文件名,名字不重复上传
            fileName= UUID.randomUUID().toString()+fileName;
            //优化文件存储路径,按时间分类
            String path=new DateTime().toString("yyyy/MM/dd");
            fileName=path+"/"+fileName;
            //上传操作
            ossClient.putObject(bucketName, fileName,inputStream);
            //关闭客户端
            ossClient.shutdown();
            //返回图片url
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new GuliException(20001,"上传失败");
        }
    }
}
