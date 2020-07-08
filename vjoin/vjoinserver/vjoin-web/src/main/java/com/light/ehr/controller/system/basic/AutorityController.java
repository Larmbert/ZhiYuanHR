package com.light.ehr.controller.system.basic;

import com.light.ehr.model.Menu;
import com.light.ehr.model.RespBean;
import com.light.ehr.model.Role;
import com.light.ehr.service.MenuService;
import com.light.ehr.service.system.position.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/4
 */
@RestController
@RequestMapping("/system/basic/permiss")
public class AutorityController {
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
    @GetMapping("/")
    public List<Role> getAllRolos(){
        return roleService.getAllRolos();
    }
    @GetMapping("/menu")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }
    @GetMapping("/mids/{rid}")
    public List<Integer> getMenuIdByRoleId(@PathVariable Integer rid){
        return menuService.getMenuIdByRoleId(rid);
    }
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        if (menuService.updateMenuRole(rid,mids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败o(╯□╰)o");
    }
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if (roleService.addRole(role)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败o(╯□╰)o");
    }
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid){
        if (roleService.deleteRoleById(rid)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
}
