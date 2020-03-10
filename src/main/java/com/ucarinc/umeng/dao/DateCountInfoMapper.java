package com.ucarinc.umeng.dao;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DateCountInfoMapper {
    int insertDateCountInfo(@Param("dateCountInfo") List<DateCountInfo> dateCountInfo);
}
