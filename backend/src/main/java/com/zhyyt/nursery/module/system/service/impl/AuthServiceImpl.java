package com.zhyyt.nursery.module.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhyyt.nursery.common.constant.Constants;
import com.zhyyt.nursery.common.exception.BusinessException;
import com.zhyyt.nursery.module.system.entity.SysMenu;
import com.zhyyt.nursery.module.system.entity.SysRole;
import com.zhyyt.nursery.module.system.entity.SysUser;
import com.zhyyt.nursery.module.system.mapper.SysMenuMapper;
import com.zhyyt.nursery.module.system.mapper.SysRoleMapper;
import com.zhyyt.nursery.module.system.mapper.SysUserMapper;
import com.zhyyt.nursery.module.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysMenuMapper menuMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Map<String, Object> login(String username, String password, String captcha, String captchaKey) {
        // 验证码校验
        if (captchaKey != null && !captchaKey.isEmpty()) {
            Object cached = redisTemplate.opsForValue().delete(Constants.CAPTCHA_KEY + captchaKey);
            if (cached == null || !cached.toString().equalsIgnoreCase(captcha)) {
                throw new BusinessException("验证码错误或已过期");
            }
        }

        // 查询用户
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if ("1".equals(user.getStatus())) {
            throw new BusinessException("账号已被停用");
        }

        // 密码校验（MD5）
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // Sa-Token 登录
        StpUtil.login(user.getUserId());

        // 更新登录信息
        user.setLoginDate(java.time.LocalDateTime.now());
        userMapper.updateById(user);

        // 获取角色和权限
        List<String> roles = userMapper.selectRoleKeysByUserId(user.getUserId());
        List<String> perms = userMapper.selectPermsByUserId(user.getUserId());

        // 获取菜单树
        List<SysMenu> menus;
        if (roles.contains(Constants.SUPER_ADMIN_ROLE)) {
            menus = menuMapper.selectAllMenus();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("token", StpUtil.getTokenValue());
        result.put("roles", roles);
        result.put("perms", perms);
        result.put("menus", buildMenuTree(menus, 0L));
        return result;
    }

    @Override
    public Map<String, Object> getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null);

        List<String> roles = userMapper.selectRoleKeysByUserId(userId);
        List<String> perms = userMapper.selectPermsByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("roles", roles);
        result.put("perms", perms);
        return result;
    }

    @Override
    public Map<String, Object> getCaptcha() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 30);
        String uuid = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(Constants.CAPTCHA_KEY + uuid, captcha.getCode(), 5, TimeUnit.MINUTES);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        captcha.write(bos);
        String base64 = Base64.getEncoder().encodeToString(bos.toByteArray());

        Map<String, Object> result = new HashMap<>();
        result.put("captchaKey", uuid);
        result.put("captchaImg", "data:image/png;base64," + base64);
        return result;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    private List<SysMenu> buildMenuTree(List<SysMenu> menus, Long parentId) {
        return menus.stream()
                .filter(m -> parentId.equals(m.getParentId()))
                .peek(m -> {
                    m.setChildren(buildMenuTree(menus, m.getMenuId()));
                    if (m.getChildren().isEmpty()) {
                        m.setChildren(null);
                    }
                })
                .collect(Collectors.toList());
    }
}
