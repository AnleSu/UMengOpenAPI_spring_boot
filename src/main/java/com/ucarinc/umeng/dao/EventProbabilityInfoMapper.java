package com.ucarinc.umeng.dao;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventProbabilityInfoMapper {

    Boolean insertProbabilityInfo(@Param("probabilityInfos") List<EventProbabilityInfo> probabilityInfos);
}
