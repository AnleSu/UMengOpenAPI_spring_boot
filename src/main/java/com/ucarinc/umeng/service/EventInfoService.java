package com.ucarinc.umeng.service;

import com.ucarinc.umeng.entity.EventInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventInfoService {

    Boolean insertEventInfo(@Param("eventInfos") List<EventInfo> eventInfos);

    Boolean deleteAll();
}
