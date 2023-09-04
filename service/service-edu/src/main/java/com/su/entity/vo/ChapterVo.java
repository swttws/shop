package com.su.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo implements Serializable {
    private String id;
    private String title;
    private List<VideVo> children=new ArrayList<>();
}
