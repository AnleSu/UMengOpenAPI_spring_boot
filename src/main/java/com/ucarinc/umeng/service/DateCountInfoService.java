package com.ucarinc.umeng.service;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DateCountInfoService {
    Boolean insertDateCountInfo(@Param("dateCountInfos") List<DateCountInfo> dateCountInfos);

    List<DateCountInfo> selectDateCountInfo(@Param("date") String date);

}
