package com.su.service;

import com.su.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.su.entity.vo.LoginVo;
import com.su.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author su
 * @since 2022-05-12
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    Integer countRegister(String day);
}
