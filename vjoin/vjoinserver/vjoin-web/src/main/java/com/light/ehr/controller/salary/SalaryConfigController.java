package com.light.ehr.controller.salary;

import com.light.ehr.model.RespBean;
import com.light.ehr.model.RespPageBean;
import com.light.ehr.model.Salary;
import com.light.ehr.service.EmployeeService;
import com.light.ehr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/6/30
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalaryConfigController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;
    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue ="1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeByPageWithSalary(page,size);
    }
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }
    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid,Integer sid){
        Integer result=employeeService.updateEmployeeSalaryById(eid,sid);
        if (result==1||result==2){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
