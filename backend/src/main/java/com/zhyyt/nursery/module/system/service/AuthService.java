package com.zhyyt.nursery.module.system.service;

import java.util.Map;

public interface AuthService {

    /** 登录 */
    Map<String, Object> login(String username, String password, String captcha, String captchaKey);

    /** 获取当前登录用户信息 */
    Map<String, Object> getUserInfo();

    /** 获取验证码 */
    Map<String, Object> getCaptcha();

    /** 退出登录 */
    void logout();
}
