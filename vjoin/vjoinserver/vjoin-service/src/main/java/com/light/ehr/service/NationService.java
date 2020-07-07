package com.light.ehr.service;

import com.light.ehr.mapper.NationMapper;
import com.light.ehr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/10
 */
@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
