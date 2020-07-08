package com.light.ehr.controller.empbasic;

import com.light.ehr.model.*;
import com.light.ehr.service.EmployeeService;
import com.light.ehr.service.NationService;
import com.light.ehr.service.PoliticsstatusService;
import com.light.ehr.service.system.position.DepartmentService;
import com.light.ehr.service.system.position.JobLevelService;
import com.light.ehr.service.system.position.PositionService;
import com.light.ehr.utils.PoiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/8
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;
    @GetMapping("/")
    public RespPageBean getAllEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             Employee employee,
                                             Date[] beginDateScope){
        return employeeService.getAllEmployeeByPage(page,size,employee,beginDateScope);
    }
    //根据id删除员工
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeById(@PathVariable Integer id){
        if (employeeService.deleteEmployeeById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
    //更新员工信息
    @PutMapping("/")
    public RespBean updateEmployee(@RequestBody Employee employee){
        if (employeeService.updateEmployee(employee)==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败o(╯□╰)o");
    }
    @PostMapping("/")
    public RespBean addEmplee(@RequestBody Employee employee){
        if(employeeService.addEmplee(employee)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败o(╯□╰)o");
    }
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }
    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }
    @GetMapping("/joblevels")
    public List<JobLevel> getAllJoblevel(){
        return jobLevelService.getAllJoblevels();
    }
    @GetMapping("/positons")
    public List<Position> getAllPositons(){
        return positionService.getAllPositon();
    }
    @GetMapping("/maxWorkId")
    public RespBean getMaxWorkId(){
        RespBean respBean= RespBean.build().setStatus(200)
                .setObj(String.format("%08d", employeeService.getMaxWorkId() + 1));
        return respBean;
    }
    @GetMapping("/deps")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDatas(){
        List<Employee> list= (List<Employee>) employeeService.getAllEmployeeByPage(null,null,null,null).getData();
        return PoiUtils.employee2Excel(list);
    }
    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Employee> list = PoiUtils.excel2Employee(file, nationService.getAllNations(), politicsstatusService.getAllPoliticsstatus(), departmentService.getAllDepartmentsWithOutChildren(), positionService.getAllPositon(), jobLevelService.getAllJoblevels());
        if (employeeService.addEmps(list) == list.size()) {
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }
    @DeleteMapping("/")
    public RespBean deleteEmpByids(Integer[] ids){
        if (employeeService.deleteEmpByids(ids)==ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
}
