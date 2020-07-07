package com.light.ehr.service.system.position;

import com.light.ehr.mapper.PositionMapper;
import com.light.ehr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author Light
 * @CreateTime 2020/5/2
 */
@Service
public class PositionService {

    @Autowired
    PositionMapper positionMapper;
    //查找所有职位
    public List<Position> getAllPositon() {
        return positionMapper.getAllPositions();
    }
    //添加职位
    public Integer addPositon(Position position){
        position.setCreateDate(new Date());
        position.setEnabled(true);
       return positionMapper.insertSelective(position);
    }

    public Integer updatePositon(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public Integer deletePositionById(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }

    public Integer deletePositionByIds(Integer[] ids) {
        return positionMapper.deletePositionsByIds(ids);
    }
}
