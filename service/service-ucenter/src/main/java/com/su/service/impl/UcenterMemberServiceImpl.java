package com.su.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.su.commonutils.utils.JwtUtils;
import com.su.entity.UcenterMember;
import com.su.entity.vo.LoginVo;
import com.su.entity.vo.RegisterVo;
import com.su.handler.GuliException;
import com.su.mapper.UcenterMemberMapper;
import com.su.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.su.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-05-12
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取数据。并验空
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(nickname)||StringUtils.isEmpty(mobile)
        ||StringUtils.isEmpty(code)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册信息缺失");
        }
        //手机号唯一,验证邮箱是否有重复
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count>0){
            throw new GuliException(20001,"邮箱已经注册过");
        }
        //验证邮箱验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new GuliException(20001,"验证码错误");
        }
        //密码加密MD5
        String md5Password = MD5.encrypt(password);
        //补充数据，插入数据库
        UcenterMember ucenterMember=new UcenterMember();
        ucenterMember.setNickname(nickname);
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(md5Password);
        ucenterMember.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        ucenterMember.setIsDisabled(false);
        baseMapper.insert(ucenterMember);
    }

    //用户登录
    @Override
    public String login(LoginVo loginVo) {
        //获取数据，验空
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"邮箱号或密码有误");
        }
        //判断邮箱是否注册过
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if (ucenterMember==null){
            throw new GuliException(20001,"邮箱号或密码有误");
        }
        //密码加密后验证密码
        String md5Password = MD5.encrypt(password);
        if (!md5Password.equals(ucenterMember.getPassword())){
            throw new GuliException(20001,"邮箱号或密码有误");
        }
        //生成token字符串
        String token= JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());
        return token;
    }

    //统计注册人数，远程调用
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
