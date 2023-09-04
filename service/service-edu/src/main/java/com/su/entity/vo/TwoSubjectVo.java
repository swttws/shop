package com.su.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TwoSubjectVo {

    @ApiModelProperty(value = "课程类别")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

}
