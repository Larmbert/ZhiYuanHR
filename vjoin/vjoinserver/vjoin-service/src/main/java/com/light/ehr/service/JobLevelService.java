package com.light.ehr.service.system.position;

import com.light.ehr.mapper.JobLevelMapper;
import com.light.ehr.model.JobLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/3
 */
@Service
public class JobLevelService {
    @Autowired
    JobLevelMapper jobLevelMapper;
    public List<JobLevel> getAllJoblevels() {
        return jobLevelMapper.getAllJobLevels();
    }

    public Integer addJobLevel(JobLevel jobLevel) {
        jobLevel.setCreateDate(new Date());
        jobLevel.setEnabled(true);
        return jobLevelMapper.insertSelective(jobLevel);
    }

    public Integer updateJobLevel(JobLevel jobLevel) {
        return jobLevelMapper.updateByPrimaryKeySelective(jobLevel);
    }

    public Integer deleteJobLevelById(Integer id) {
        return jobLevelMapper.deleteByPrimaryKey(id);
    }

    public Integer deleteJoblevelsByIds(Integer[] ids) {
        return jobLevelMapper.deleteJobLevelsByIds(ids);
    }
}
