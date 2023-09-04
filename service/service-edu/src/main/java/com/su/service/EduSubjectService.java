package com.su.service;

import com.su.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.su.entity.vo.OneSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubjectVo> getAllSubject();

}
