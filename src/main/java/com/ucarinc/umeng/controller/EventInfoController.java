package com.ucarinc.umeng.controller;

import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.service.EventInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class EventInfoController {
    @Autowired
    private EventInfoService eventInfoService;

    @RequestMapping("/")
    public int insertEventInfo(@Param("eventInfos") List<EventInfo> eventInfos) {
        eventInfoService.insertEventInfo(eventInfos);
        return 0;
    }

    public List<EventInfo> selectAllEventInfo(){
        return eventInfoService.selectAllEventInfo();
    }

}
