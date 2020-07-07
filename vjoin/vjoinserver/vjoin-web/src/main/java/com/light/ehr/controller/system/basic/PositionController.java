package com.light.ehr.controller.system.basic;

import com.light.ehr.model.Position;
import com.light.ehr.model.RespBean;
import com.light.ehr.service.system.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/2
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    PositionService positionService;
    //查询职位信息
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositon();
    }
    //添加职位信息
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
         if (positionService.addPositon(position)==1){
             return RespBean.ok("添加成功");
         }
         return RespBean.error("添加失败o(╯□╰)o");
    }
    //更新职位信息
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (positionService.updatePositon(position)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败o(╯□╰)o");
    }
    //根据id删除职位信息
    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id){
        if(positionService.deletePositionById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
    //批量删除职位信息
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        if (positionService.deletePositionByIds(ids)==ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败o(╯□╰)o");
    }
}
