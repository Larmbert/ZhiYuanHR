package com.light.ehr.controller.system;

import com.light.ehr.model.Hr;
import com.light.ehr.model.RespBean;
import com.light.ehr.model.Role;
import com.light.ehr.service.HrService;
import com.light.ehr.service.system.position.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/5
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;
    @Autowired
    RoleService roleService;
    //查询HR信息
    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }
    //更新人事信息
    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if (hrService.updateHr(hr)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败o(╯□╰)o");
    }
    //查询所有角色
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRolos();
    }
    //根据HRid，删除原来角色信息，添加新的角色信息
    @PutMapping("/role")
    public RespBean updateHrRoles(Integer hrid ,Integer[] rids){
        if (hrService.updateHrRoles(hrid,rids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败o(╯□╰)o");
    }
    //根据HRid删除HR
    @DeleteMapping("{hrid}")
    public RespBean deleteHrById(@PathVariable Integer hrid){
        if (hrService.deleteHrById(hrid)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
}
