package com.ucarinc.umeng.service;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DateCountInfoService {
    Boolean insertDateCountInfo(@Param("dateCountInfos") List<DateCountInfo> dateCountInfos);
}
