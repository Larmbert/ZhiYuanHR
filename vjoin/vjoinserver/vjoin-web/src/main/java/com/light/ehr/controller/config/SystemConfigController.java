package com.light.ehr.controller.config;

import com.light.ehr.model.Menu;
import com.light.ehr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/4/21
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenuByHrId(){
        return menuService.getMenuByHrId();
    }
}
