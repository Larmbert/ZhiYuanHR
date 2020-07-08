package com.light.ehr.controller.chat;

import com.light.ehr.model.Hr;
import com.light.ehr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/7/5
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    HrService hrService;
    @GetMapping("/hrs")
    public List<Hr> getAllHrsExceptCurrentHr(){
        return hrService.getAllHrsExceptCurrentHr();
    }
}
