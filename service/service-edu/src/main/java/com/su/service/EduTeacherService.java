package com.su.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.su.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-01
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherApiPage(Page<EduTeacher> page);
}
