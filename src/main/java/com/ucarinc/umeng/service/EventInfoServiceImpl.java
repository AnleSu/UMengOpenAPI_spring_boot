package com.ucarinc.umeng.service;

import com.ucarinc.umeng.dao.EventInfoMapper;
import com.ucarinc.umeng.entity.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventInfoServiceImpl  implements EventInfoService{

    @Autowired
    EventInfoMapper eventInfoMapper;
    @Override
    public Boolean insertEventInfo(List<EventInfo> eventInfos) {

        try{
            if(eventInfoMapper.insertEventInfo(eventInfos)>0){
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
    public List<EventInfo> selectAllEventInfo() {
        return null;
    }
}
