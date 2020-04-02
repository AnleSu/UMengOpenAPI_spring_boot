package com.ucarinc.umeng.dao;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventProbabilityInfoMapper {

    Boolean insertProbabilityInfo(@Param("probabilityInfos") List<EventProbabilityInfo> probabilityInfos);

    List<EventProbabilityInfo> selectByNameAndDate(String name, String startDate, String endDate);
}
