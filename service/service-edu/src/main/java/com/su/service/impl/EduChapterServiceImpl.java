package com.su.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.entity.EduChapter;
import com.su.entity.EduVideo;
import com.su.entity.vo.ChapterVo;
import com.su.entity.vo.VideVo;
import com.su.mapper.EduChapterMapper;
import com.su.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.su.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //根据课程id查询章节，小节信息
    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        //根据courseId查询章节集合信息
        QueryWrapper<EduChapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        //根据courseId查询小节集合信息
        QueryWrapper<EduVideo> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = eduVideoService.list(videoQueryWrapper);

        //遍历章节信息进行封装
        List<ChapterVo> chapterVideoList=new ArrayList<>();
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVideoList.add(chapterVo);

            //遍历和此章节关联小节信息进行封装
            List<VideVo> videVos=new ArrayList<>();
            for (EduVideo eduVideo : videoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideVo videVo=new VideVo();
                    BeanUtils.copyProperties(eduVideo,videVo);
                    videVos.add(videVo);
                }
            }
            chapterVo.setChildren(videVos);

        }
        return chapterVideoList;
    }
}
