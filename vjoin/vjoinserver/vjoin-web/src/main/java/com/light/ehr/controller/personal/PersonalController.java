package com.light.ehr.controller.personal;

import com.light.ehr.model.Employee;
import com.light.ehr.model.RespBean;
import com.light.ehr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/7/12
 */
@RestController
@RequestMapping("/personnel/emp")
public class PersonalController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/")
    public List<Employee> getAllPersonals(){
        return employeeService.getAllPersonals();
    }
}
