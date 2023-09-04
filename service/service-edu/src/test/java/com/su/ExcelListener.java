package com.su;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.su.entity.DemoData;

//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //读取每一行数据
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println(demoData);
    }

    //读取数据后做的事
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
