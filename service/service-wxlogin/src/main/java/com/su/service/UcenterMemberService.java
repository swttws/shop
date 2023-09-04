package com.su.service;

import com.su.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    UcenterMember getByOpenid(String openid);
}
