package com.light.ehr.service;

import com.light.ehr.mapper.EmployeeMapper;
import com.light.ehr.model.Employee;
import com.light.ehr.model.RespBean;
import com.light.ehr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/8
 */
@Service
public class EmployeeService {

    public final static Logger logger= LoggerFactory.getLogger(EmployeeService.class);

    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
    DecimalFormat decimalFormat=new DecimalFormat("##.00");
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    EmployeeMapper employeeMapper;
    public RespPageBean getAllEmployeeByPage(Integer page, Integer size,Employee employee,Date[] beginDateScope) {
        if (page!=null && size!=null){
            page=(page-1)*size;
        }
        List<Employee> data=employeeMapper.getEmployeeByPage(page,size,employee,beginDateScope);
        Long total=employeeMapper.getTotal(employee,beginDateScope);
        RespPageBean respPageBean=new RespPageBean();
        respPageBean.setData(data);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    public Integer addEmplee(Employee employee) {
        Date beginDate=employee.getBeginContract();
        Date endDate=employee.getEndContract();
        Double month=(Double.parseDouble(yearFormat.format(endDate))
                -Double.parseDouble(yearFormat.format(beginDate)))*12
                +(Double.parseDouble(monthFormat.format(endDate))-Double.parseDouble(monthFormat.format(beginDate)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
        int result = employeeMapper.insertSelective(employee);
        if (result==1){
            Employee emp=employeeMapper.getEmployeeById(employee.getId());
            logger.info(emp.toString());
            rabbitTemplate.convertAndSend("light.mail",emp);
        }
        return result;
    }

    public Integer getMaxWorkId() {
        return employeeMapper.maxWorkID();
    }

    public Integer deleteEmployeeById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmployee(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }

    public Integer deleteEmpByids(Integer[] ids) {
        return employeeMapper.deleteEmpByids(ids);
    }

    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> employeeList=employeeMapper.getEmployeeByPageWithSalary(page,size);
        RespPageBean respPageBean=new RespPageBean();
        respPageBean.setTotal(employeeMapper.getTotal(null,null));
        respPageBean.setData(employeeList);
        return respPageBean;
    }

    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid,sid);
    }

    public List<Employee> getAllPersonals() {
        return  employeeMapper.getAllPersonals();
    }
}
