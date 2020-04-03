package com.ucarinc.umeng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;

import com.mysql.cj.xdevapi.JsonArray;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api")
public class HelloController {

    public static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    public static final String APP_KEY = "4009351";

    public static final String SEC_KEY = "ATZNZYZ6fT";

    public static final String SERVER_HOST = "gateway.open.umeng.com";

    public static final String[] eventList = new  String[] {
            "XQ_App_StartUp",
            "XQ_App_UserCenter",
            "XQ_APP_CustomerService",
           "XQ_ZCN_Home_BannerClick",
            "XQ_ZCN_Home_PickupCityClick",
            "XQ_ZCN_Home_PickupAddressClick",
            "XQ_ZCN_Home_ReturnCityClick",
            "XQ_ZCN_Home_ReturnAddressClick",
            "XQ_ZCN_Home_CrossDiffAddressSwitch",
            "XQ_ZCN_Home_LocationMeClick",
            "XQ_ZCN_Home_CalendarClick",
            "XQ_ZCN_Home_GotoBookCarClick",
            "XQ_ZCN_Home_WeekRentClick",
            "XQ_ZCN_Home_BottomMoreHitchhikingClick",
            "XQ_ZCN_Home_BottomHitchhikingClick",

    };

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
    @RequestMapping("/getHomePageEventProbabilit")
    //http://localhost:8080/api/getHomePageEventProbabilit?events=%20XQ_ZCN_Home_BannerClick,%20XQ_App_UserCenter&startDate=2020-04-01&endDate=2020-04-01
    //时间 必须是（yyyy-MM-dd）格式
    //endDate不传 只请求某一个的概率
    //startDate endDate都有值 则请求这个时间段内的 概率和
    public String getHomePageEventProbabilit(@RequestParam(value ="events", required = false) String[] events,String startDate, String endDate,@RequestParam(defaultValue = "7.0.0") String version) {
        if (events == null || events.length == 0) {
            events = eventList;

        }
        if (startDate.isEmpty()) return "开始时间参数为空";




        List<EventProbabilityInfo> resultArr = new ArrayList<>();
        if (StringUtils.isEmpty(endDate) || startDate.equals(endDate)) {
            for (int i = 0; i < events.length; i++) {
                String eventName = events[i];
                List<EventProbabilityInfo> tempArr = eventProbabilityInfoService.selectByNameAndDate(eventName, startDate, startDate);
                if (!tempArr.isEmpty()) {
                    resultArr.addAll(tempArr);

                }
            }
        } else {
//            Integer appstartCount = 0;
//            List<EventProbabilityInfo> appstartInfos = eventProbabilityInfoService.selectByNameAndDate("XQ_App_StartUp", startDate, endDate);
//            for(EventProbabilityInfo info : appstartInfos) {
//                appstartCount += info.getCount();
//            }
//            DecimalFormat df = new DecimalFormat("0.00");
//
//            for (int i = 0; i < events.length; i++) {
//                String eventName = events[i];
//                List<EventProbabilityInfo> tempArr = eventProbabilityInfoService.selectByNameAndDate(eventName, startDate, endDate);
//                if (!tempArr.isEmpty()) {
//                    Integer tempNum = 0;
//                    for(EventProbabilityInfo info : tempArr) {
//                        tempNum += info.getCount();
//                    }
//                    EventProbabilityInfo resultInfo = new EventProbabilityInfo();
//                    resultInfo.setName(eventName);
//                    resultInfo.setProbabilit(df.format(tempNum /appstartCount));
//                    resultArr.add(resultInfo);
//                }
//            }

            List<EventProbabilityInfo> appstartInfos = eventProbabilityInfoService.selectByNameAndDate("XQ_App_StartUp", startDate, endDate);
            if(CollectionUtils.isEmpty(appstartInfos)){
                //分母一般不为零的，如果为零看需要抛出什么一样
            }

            DecimalFormat df = new DecimalFormat("0.00");
            Integer sum = appstartInfos.stream().mapToInt(EventProbabilityInfo::getCount).sum();
            for (int i = 0; i < events.length; i++) {
                String eventName = events[i];
                List<EventProbabilityInfo> tempArr = eventProbabilityInfoService.selectByNameAndDate(eventName, startDate, endDate);
                if (!tempArr.isEmpty()) {
                    Integer sum1 = tempArr.stream().mapToInt(EventProbabilityInfo::getCount).sum();
                    EventProbabilityInfo resultInfo = new EventProbabilityInfo();
                    resultInfo.setName(eventName);
                    resultInfo.setCount(sum1);
                    resultInfo.setProbabilit(df.format(sum1 /sum));
                    resultArr.add(resultInfo);
                }
            }
        }



        //对象数组转json
        return JSON.toJSONString(resultArr);
    }

    @ResponseBody
    @RequestMapping("/eventList")
    //根据版本号 时间段获取所有事件列表
    public String eventList(String startDate, String endDate,@RequestParam(defaultValue = "7.0.0") String version, Integer page){
        configApiExecutor();
        //数据量太大
//        String resultStr = UMengRequestCommon.umengUappEventList(apiExecutor,startDate,endDate,version,page);
//        JSONObject jsonObject = JSONObject.parseObject(resultStr);//字符串转json对象
//        String data = jsonObject.getString("eventInfo");
//        List<EventInfo> list=JSONObject.parseArray(data, EventInfo.class);
//
////        Boolean deleteResult = eventInfoService.deleteAll();
////        logger.info("删除事件表" + deleteResult);
//
//        if(eventInfoService.insertEventInfo(list)){
//            logger.info("数据入库成功");
//        }else{
//            logger.info("数据入库失败");
//        }

        //只请求首页的所有事件数据



        List<EventProbabilityInfo> probabilityInfos = new ArrayList<>();
        List<DateCountInfo> startUpInfos = new ArrayList<>();

        //遍历事件列表 请求一个时间段内该事件的统计数据
        for(int i = 0; i < eventList.length; i++) {
            String eventName = eventList[i];


            //筛选出APP启动次数的数据
            if (eventName.equals("XQ_App_StartUp")) {
                startUpInfos = eventDataFun(eventName, startDate, endDate);

            } else {
                eventDataFun(eventName, startDate, endDate);
            }
        }


        List<DateCountInfo> currentDateInfos = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < startUpInfos.size(); i++) {
            DateCountInfo startUpInfo = startUpInfos.get(i);
            //根据日期 取到该日期下的所有统计数据
            currentDateInfos = dateCountInfoService.selectDateCountInfo(startUpInfo.getDate());
            if (!currentDateInfos.isEmpty()) {
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

        }

        logger.info("概率计算完成");
        if(eventProbabilityInfoService.insertProbabilityInfo(probabilityInfos)) {
            logger.info("概率数据入库成功");
        } else  {
            logger.info("概率数据入库失败");
        }

        return JSON.toJSONString(probabilityInfos);
    }

    @ResponseBody
    @RequestMapping("/getEventList")
    //根据版本号 时间段获取所有事件列表   http://localhost:8080/api/getEventList?startDate=2020-03-10&endDate=2020-03-11
    public String getEventList(String startDate, String endDate ,@RequestParam(defaultValue = "7.0.0") String version,Integer page) {
        configApiExecutor();
        String resultStr = UMengRequestCommon.umengUappEventList(apiExecutor, startDate, endDate, version,page);
        return resultStr;
    }

    @ResponseBody
    @RequestMapping("/getEventParamList")
    public String getEventParamList(String startDate, String endDate ,String eventId) {
        configApiExecutor();
        String resultStr = UMengRequestCommon.umengUappEventParamList(apiExecutor,eventId, startDate, endDate);
        return resultStr;
    }

    public List<DateCountInfo> eventDataFun(String eventName, String startDate, String endDate){
        String resultStr = UMengRequestCommon.umengUappEventGetData(apiExecutor,eventName, startDate, endDate);
        logger.info("请求结果是 " + resultStr);
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


