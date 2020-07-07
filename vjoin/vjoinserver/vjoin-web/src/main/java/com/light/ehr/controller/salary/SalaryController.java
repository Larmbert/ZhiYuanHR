package com.light.ehr.controller.salary;

import com.light.ehr.model.RespBean;
import com.light.ehr.model.Salary;
import com.light.ehr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/6/27
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    //获取所有工资账套信息
    @GetMapping("/")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    //添加工资账套
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if (salaryService.addSalary(salary)==1){
            return RespBean.ok("添加工资账套成功！");
        }
        return RespBean.error("添加工资账套失败！");

    }

    //根据id删除工资账套
    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id){
        if (salaryService.deleteSalaryById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除成功！");
    }

    //更新账套信息
    @PutMapping("/")
    public RespBean updateSalaryById(@RequestBody Salary salary){
        if (salaryService.updateSalaryById(salary)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
