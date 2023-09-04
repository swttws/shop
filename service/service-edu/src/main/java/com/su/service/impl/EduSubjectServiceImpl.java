package com.su.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.entity.EduSubject;
import com.su.entity.vo.ExcelSubjectData;
import com.su.entity.vo.OneSubjectVo;
import com.su.entity.vo.TwoSubjectVo;
import com.su.handler.GuliException;
import com.su.listener.SubjectExcelListener;
import com.su.mapper.EduSubjectMapper;
import com.su.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //批量导入课程分类
    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService))
                    .sheet().doRead();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new GuliException(20001,"导入课程分类失败");
        }
    }

    //查询所有课程分类
    @Override
    public List<OneSubjectVo> getAllSubject() {

        //1.查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2.查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //3.封装一级数据
        List<OneSubjectVo> allSubject=new ArrayList<>();
        for (EduSubject oneSubject : oneSubjectList) {
            OneSubjectVo oneSubjectVo=new OneSubjectVo();
            //对象复制，根据名字来存储
            BeanUtils.copyProperties(oneSubject,oneSubjectVo);
            allSubject.add(oneSubjectVo);
            //4.封装一级有关的二级数据
            List<TwoSubjectVo> twoSubjectVos=new ArrayList<>();
            for (int i = 0; i < twoSubjectList.size(); i++) {
                EduSubject twoSubject=twoSubjectList.get(i);
                //判断二级标题是否为一级标题子标题
                if (twoSubject.getParentId().equals(oneSubjectVo.getId())){
                    TwoSubjectVo twoSubjectVo=new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject,twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                }
            }
            oneSubjectVo.setChildren(twoSubjectVos);

        }

        return allSubject;
    }
}
