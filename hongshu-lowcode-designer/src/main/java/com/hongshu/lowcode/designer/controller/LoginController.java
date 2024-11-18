package com.hongshu.lowcode.designer.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hongshu.boot.starter.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class LoginController {

    @PostMapping("/auth/doLogin")
    public Result doLogin(@RequestParam(name = "username") String username,
                          @RequestParam(name = "password") String password){

        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("admin".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return Result.buildSuccess(StpUtil.getTokenInfo());
        }
        return Result.buildFailure("error-designer-login-error","");
    }
    @GetMapping("/isLogin")
    public Result<Boolean> isLogin() {
        return Result.buildSuccess(StpUtil.isLogin());
    }
    @GetMapping("/tokenInfo")
    public Result tokenInfo(){
        return Result.buildSuccess(StpUtil.getTokenInfo());
    }
    @GetMapping("/logout")
    public Result logout(){
        StpUtil.logout();
        return Result.buildSuccess();
    }
}
