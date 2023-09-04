package com.su.client;

import com.su.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

//降级方法类
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R delVideo(String videoId) {
        return R.error().message("删除失败");
    }

    @Override
    public R delVideos(List<String> videoIdList) {
        return R.error().message("删除失败");
    }
}
