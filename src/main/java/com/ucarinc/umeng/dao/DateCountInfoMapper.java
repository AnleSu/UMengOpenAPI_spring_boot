package com.ucarinc.umeng.dao;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DateCountInfoMapper {
    int insertDateCountInfo(@Param("dateCountInfo") List<DateCountInfo> dateCountInfo);

    List<DateCountInfo> selectDateCountInfo(String date);

}
