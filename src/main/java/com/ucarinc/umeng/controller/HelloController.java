package com.ucarinc.umeng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventData;
import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.entity.EventProbabilityInfo;
import com.ucarinc.umeng.service.DateCountInfoService;
import com.ucarinc.umeng.service.EventInfoService;
import com.ucarinc.umeng.service.EventProbabilityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class HelloController {

    public static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    public static final String APP_KEY = "4009351";

    public static final String SEC_KEY = "ATZNZYZ6fT";

    public static final String SERVER_HOST = "gateway.open.umeng.com";

    ApiExecutor apiExecutor = new ApiExecutor(APP_KEY, SEC_KEY);

    HelloController() {
        Init();
    }

    void Init() {
        configApiExecutor();
    }

    void configApiExecutor() {
        apiExecutor.setServerHost(SERVER_HOST);
    }

    @Autowired
    EventInfoService eventInfoService;
    @Autowired
    DateCountInfoService dateCountInfoService;
    @Autowired
    EventProbabilityInfoService eventProbabilityInfoService;


    @ResponseBody
    @RequestMapping("/eventList")
    //根据版本号 时间段获取所有事件列表
    public String eventList(String startDate, String endDate,@RequestParam(defaultValue = "7.0.0") String version){
        configApiExecutor();
        String resultStr = UMengRequestCommon.umengUappEventList(apiExecutor,startDate,endDate,version);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);//字符串转json对象
        String data = jsonObject.getString("eventInfo");
        List<EventInfo> list=JSONObject.parseArray(data, EventInfo.class);

        Boolean deleteResult = eventInfoService.deleteAll();
        logger.info("删除事件表" + deleteResult);

        if(eventInfoService.insertEventInfo(list)){
            logger.info("数据入库成功");
        }else{
            logger.info("数据入库失败");
        }

        List<EventProbabilityInfo> probabilityInfos = new ArrayList<>();
        List<DateCountInfo> startUpInfos = new ArrayList<>();

        //遍历事件列表 请求一个时间段内该事件的统计数据
        for(int i = 0; i < list.size(); i++) {
            EventInfo eventInfo = list.get(i);


            //筛选出APP启动次数的数据
            if (eventInfo.getName().equals("XQ_App_StartUp")) {
                startUpInfos = eventDataFun(eventInfo.getName(), startDate, endDate);

            } else {
                eventDataFun(eventInfo.getName(), startDate, endDate);
            }
        }


        List<DateCountInfo> currentDateInfos = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < startUpInfos.size(); i++) {
            DateCountInfo startUpInfo = startUpInfos.get(i);
            //根据日期 取到该日期下的所有统计数据
            currentDateInfos = dateCountInfoService.selectDateCountInfo(startUpInfo.getDate());
            for (int j = 0;j < currentDateInfos.size();j++) {

                //遍历每一条数据 计算概率
                DateCountInfo dateCountInfo = currentDateInfos.get(j);
                EventProbabilityInfo eventProbabilityInfo = new EventProbabilityInfo();
                eventProbabilityInfo.setCount(dateCountInfo.getCount());
                eventProbabilityInfo.setDate(dateCountInfo.getDate());
                eventProbabilityInfo.setName(dateCountInfo.getName());
                eventProbabilityInfo.setProbabilit(df.format( (double)dateCountInfo.getCount() / startUpInfo.getCount()));
                probabilityInfos.add(eventProbabilityInfo);
            }
        }

        logger.info("概率计算完成");
        if(eventProbabilityInfoService.insertProbabilityInfo(probabilityInfos)) {
            logger.info("概率数据入库成功");
        } else  {
            logger.info("概率数据入库失败");
        }

        return resultStr;
    }

    @ResponseBody
    @RequestMapping("/getEventList")
    //根据版本号 时间段获取所有事件列表   http://localhost:8080/api/getEventList?startDate=2020-03-10&endDate=2020-03-11
    public String getEventList(String startDate, String endDate ,@RequestParam(defaultValue = "7.0.0") String version) {
        configApiExecutor();
        String resultStr = UMengRequestCommon.umengUappEventList(apiExecutor, startDate, endDate, version);
        return resultStr;
    }

    public List<DateCountInfo> eventDataFun(String eventName, String startDate, String endDate){
        String resultStr = UMengRequestCommon.umengUappEventGetData(apiExecutor,eventName, startDate, endDate);
        EventData jsonObject = JSON.parseObject(resultStr, EventData.class);//字符串转json对象
        EventData.EventDataItem item = jsonObject.eventData.get(0);
        if (item == null)
            return null;
        List<DateCountInfo> list = new ArrayList<>();


        for (int i = 0; i < item.data.size(); i++){
            DateCountInfo info = new DateCountInfo();
            info.setCount(item.data.get(i));
            info.setDate(item.dates.get(i));
            info.setName(eventName);
            list.add(info);

        }


        //原始数据入库
        if(dateCountInfoService.insertDateCountInfo(list)){
            logger.info("数据入库成功");
        }else{
            logger.info("数据入库失败");
        }

        return list;

    }


    @ResponseBody
    @RequestMapping("/eventData")
    public String eventData(String eventName, String startDate, String endDate){
        String resultStr = UMengRequestCommon.umengUappEventGetData(apiExecutor,eventName, startDate, endDate);
        return resultStr;
    }

    @ResponseBody
    @RequestMapping("/getLaunches")
    //查询类型（按日daily,按周weekly,按月monthly 查询）
    public String  getLaunches(String startDate, String endDate, @RequestParam(defaultValue = "daily") String periodType) {
        return UMengRequestCommon.umengUappGetLaunches(apiExecutor, startDate, endDate, periodType);
    }





}


