package com.su.service;

import com.su.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-10
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getAllBannner();
}
