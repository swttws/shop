package com.su.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFileOss(MultipartFile file);
}
