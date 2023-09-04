package com.su.service;

import com.su.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.su.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-08
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);
}
