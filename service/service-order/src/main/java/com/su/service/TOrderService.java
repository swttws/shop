package com.su.service;

import com.su.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-16
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}
