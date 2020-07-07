package com.light.ehr.service.system.position;

import com.light.ehr.mapper.RoleMapper;
import com.light.ehr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/4
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    public List<Role> getAllRolos() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insertSelective(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
