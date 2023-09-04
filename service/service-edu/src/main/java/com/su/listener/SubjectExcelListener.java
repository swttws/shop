package com.su.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.entity.EduSubject;
import com.su.entity.vo.ExcelSubjectData;
import com.su.handler.GuliException;
import com.su.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {}
    //创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        //读取数据验空操作
        if (excelSubjectData==null){
            throw new GuliException(20001,"导入课程分类失败");
        }
        //判断一级分类名称是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, excelSubjectData.getOneSubjectName());
        //一级不重复插入数据库
        if (existOneSubject==null){
            existOneSubject=new EduSubject();
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        String pid=existOneSubject.getId();
        //判断二级名称是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(), pid);
        //二级名称不重复插入数据库
        if (existTwoSubject==null){
            existTwoSubject=new EduSubject();
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类名称是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        wrapper.eq("title",name);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //判断二级分类名称是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name,String pid) {
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",pid);
        wrapper.eq("title",name);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
