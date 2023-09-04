package com.su.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubjectVo {

    @ApiModelProperty(value = "课程类别")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    private List<TwoSubjectVo> children=new ArrayList<>();

}
