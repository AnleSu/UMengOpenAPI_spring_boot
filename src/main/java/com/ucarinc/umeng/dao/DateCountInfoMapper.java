package com.ucarinc.umeng.dao;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateCountInfoMapper {
    int insertDateCountInfo(@Param("dateCountInfo") List<DateCountInfo> dateCountInfo);

    List<DateCountInfo> selectDateCountInfo(@Param("date") String date);

}
