package com.light.ehr.service.system.position;

import com.light.ehr.mapper.DepartmentMapper;
import com.light.ehr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/5
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDept(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
    }

    public void deleteDeptById(Department department) {
        departmentMapper.deleteDepById(department);
    }
    public List<Department> getAllDepartmentsWithOutChildren(){
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
