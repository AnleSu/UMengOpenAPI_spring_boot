package com.ucarinc.umeng.service;

import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventProbabilityInfoService {

    Boolean insertProbabilityInfo(List<EventProbabilityInfo> probabilityInfos);
}
