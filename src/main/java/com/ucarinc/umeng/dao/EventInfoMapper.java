package com.ucarinc.umeng.dao;

import com.ucarinc.umeng.entity.EventInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventInfoMapper {
    int insertEventInfo(@Param("eventInfos") List<EventInfo> eventInfos);

    List<EventInfo> selectAllEventInfo();
}
