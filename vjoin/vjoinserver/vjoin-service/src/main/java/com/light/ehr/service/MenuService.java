package com.light.ehr.service;

import com.light.ehr.mapper.MenuMapper;
import com.light.ehr.mapper.MenuRoleMapper;
import com.light.ehr.model.Hr;
import com.light.ehr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/4/21
 */
@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    public List<Menu> getMenuByHrId() {
       return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    //@Cacheable  可以将角色存储到Redis，不用每次加载
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMenuIdByRoleId(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if (mids==null||mids.length==0){
            return true;
        }
        Integer result=menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
    }
}
