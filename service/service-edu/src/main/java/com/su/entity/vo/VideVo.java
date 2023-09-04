package com.su.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class VideVo implements Serializable {
    private String id;
    private String title;
    private String videoSourceId;
}

