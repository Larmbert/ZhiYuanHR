package com.light.ehr.utils;

import com.light.ehr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author Light
 * @CreateTime 2020/5/5
 */
public class HrUtils {
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
