package com.ucarinc.umeng.service;

import com.ucarinc.umeng.dao.DateCountInfoMapper;
import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateCountInfoServeImpl implements DateCountInfoService{

    @Autowired
    DateCountInfoMapper dateCountInfoMapper;
    @Override
    public Boolean insertDateCountInfo(List<DateCountInfo> dateCountInfos) {

        try{
            if(dateCountInfoMapper.insertDateCountInfo(dateCountInfos) > 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据入库异常");
            return false;
        }
    }
    @Override
    public List<DateCountInfo> selectDateCountInfo(String date) {
        try{
            List<DateCountInfo> dateCountInfos = dateCountInfoMapper.selectDateCountInfo(date);
            if(dateCountInfos.size() > 0){
                return dateCountInfos;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据查找异常");
            return null;
        }
    }



}
