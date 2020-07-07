package com.light.ehr.controller.system.basic;

import com.light.ehr.model.JobLevel;
import com.light.ehr.model.RespBean;
import com.light.ehr.service.system.position.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/3
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJoblevels(){
        return jobLevelService.getAllJoblevels();
    }
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel){
        if (jobLevelService.addJobLevel(jobLevel)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败o(╯□╰)o");
    }
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jobLevel){
        if (jobLevelService.updateJobLevel(jobLevel)==1){
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败o(╯□╰)o");
    }
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if (jobLevelService.deleteJobLevelById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
    @DeleteMapping("/")
    public RespBean deleteJoblevelsByIds(Integer [] ids){
        if (jobLevelService.deleteJoblevelsByIds(ids)==ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
}
