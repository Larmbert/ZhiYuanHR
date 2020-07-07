package com.light.ehr.exception;

import com.light.ehr.model.RespBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @Author Light
 * @CreateTime 2020/5/3
 */
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if (e instanceof MySQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
