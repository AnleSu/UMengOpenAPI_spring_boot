package com.ucarinc.umeng.service;

import com.ucarinc.umeng.dao.EventInfoMapper;
import com.ucarinc.umeng.dao.EventProbabilityInfoMapper;
import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventProbilityInfoServiceImpl implements EventProbabilityInfoService {

    @Autowired
    EventProbabilityInfoMapper eventProbabilityInfoMapper ;
    @Override
    public Boolean insertProbabilityInfo(List<EventProbabilityInfo> probabilityInfos) {
        try{
            if(eventProbabilityInfoMapper.insertProbabilityInfo(probabilityInfos)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据入库异常");
            return false;
        }
    }

    @Override
    public List<EventProbabilityInfo> selectByNameAndDate(String name, String startDate, String endDate) {
        return eventProbabilityInfoMapper.selectByNameAndDate(name, startDate, endDate);
    }


}
