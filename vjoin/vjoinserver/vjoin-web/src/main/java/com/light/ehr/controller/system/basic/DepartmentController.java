package com.light.ehr.controller.system.basic;

import com.light.ehr.model.Department;
import com.light.ehr.model.RespBean;
import com.light.ehr.service.system.position.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/5
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @PostMapping("/")
    public RespBean addDept(@RequestBody Department department){
        departmentService.addDept(department);
        if (department.getResult()==1){
            return RespBean.ok("添加成功！",department);
        }
        return RespBean.error("添加失败o(╯□╰)o");
    }
    @DeleteMapping("/{id}")
    public RespBean deleteDeptById(@PathVariable Integer id){
        Department department=new Department();
        department.setId(id);
        departmentService.deleteDeptById(department);
        if (department.getResult()==-2){
            return RespBean.error("该部门下有子部门，删除失败o(╯□╰)o");
        }else if(department.getResult()==-1){
            return RespBean.error("该部门下有职员，删除失败o(╯□╰)o");
        }else if (department.getResult()==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
}
