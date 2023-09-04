package com.su.service.impl;

import com.su.entity.CrmBanner;
import com.su.mapper.CrmBannerMapper;
import com.su.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-10
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //缓存存储到redis
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAllBannner() {
        return baseMapper.selectList(null);
    }
}
