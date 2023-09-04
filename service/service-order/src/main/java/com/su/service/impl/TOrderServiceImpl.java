package com.su.service.impl;

import com.su.client.EduClient;
import com.su.client.UcenterClient;
import com.su.commonutils.vo.CourseWebVoForOrder;
import com.su.commonutils.vo.UcenterMemberForOrder;
import com.su.entity.TOrder;
import com.su.handler.GuliException;
import com.su.mapper.TOrderMapper;
import com.su.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.su.utils.OrderUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-16
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    //远程调用接口
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //创建订单
    @Override
    public String createOrder(String courseId, String memberId) {
        //跨模块获取课程信息
        CourseWebVoForOrder courseDto = eduClient.getCourseInfoForOrder(courseId);
        //进行校验
        if (courseDto==null)
            throw new GuliException(20001,"获取课程信息失败");
        //跨模块获取用户信息
        UcenterMemberForOrder ucenterMember = ucenterClient.getUcenterInfoForOrder(memberId);
        //进行校验
        if (ucenterMember==null)
            throw new GuliException(20001,"获取用户信息失败");
        //生成订单编号
        String orderNo = OrderUtils.getOrderNo();
        //封装数据
        TOrder order = new TOrder();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//微信
        baseMapper.insert(order);

        return orderNo;
    }
}
