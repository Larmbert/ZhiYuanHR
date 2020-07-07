package com.light.ehr.controller;

import com.light.ehr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Light
 * @CreateTime 2020/4/19
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("你还没有登录，请先登录哦！");
    }
}
