package com.ucarinc.umeng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.client.exception.OceanException;

import com.ucarinc.umeng.entity.DateCountInfo;
import com.ucarinc.umeng.entity.EventData;
import com.ucarinc.umeng.entity.EventInfo;
import com.ucarinc.umeng.service.DateCountInfoService;
import com.ucarinc.umeng.service.EventInfoService;
import com.umeng.uapp.param.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class HelloController {
    public static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    ApiExecutor apiExecutor = new ApiExecutor("123456", "xxxxxxx");



    @Autowired
    EventInfoService eventInfoService;
    @Autowired
    DateCountInfoService dateCountInfoService;

    //公开一个方法hello
    @ResponseBody
    @RequestMapping("/hello")
    public void hello(String name, @RequestParam(defaultValue = "33") int age){//加入参数 默认值

        umengUappGetTodayYesterdayData(apiExecutor);
    }


    @ResponseBody
    @RequestMapping("/hello/{name}/{age}")
    public String hello1(@PathVariable String name, @PathVariable int age){//路径变量来进行输入参数的填写
        return "hello1 " + name +" age is " + age;
    }



    @ResponseBody
    @RequestMapping("/eventList")
    public String eventList(){
        apiExecutor.setServerHost("gateway.open.umeng.com");

        String resultStr =umengUappEventList(apiExecutor);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);//字符串转json对象
        String data = jsonObject.getString("eventInfo");
        List<EventInfo> list=JSONObject.parseArray(data, EventInfo.class);
        if(eventInfoService.insertEventInfo(list)){
            logger.info("数据入库成功");
        }else{
            logger.info("数据入库失败");
        }

        return umengUappEventList(apiExecutor);

    }

    @ResponseBody
    @RequestMapping("/eventData")
    public String eventData(){
        apiExecutor.setServerHost("gateway.open.umeng.com");
        String resultStr =umengUappEventGetData(apiExecutor);
        EventData jsonObject = JSON.parseObject(resultStr, EventData.class);//字符串转json对象
        EventData.EventDataItem item = jsonObject.eventData.get(0);
        if (item == null)
            return null;
        int count = item.data.size();
        List<DateCountInfo> list = new ArrayList<>();
        for (int i =0 ; i<count;i++){
            DateCountInfo info = new DateCountInfo();
            info.setCount(item.data.get(i));
            info.setDate(item.dates.get(i));
            info.setName("519b040156240b7b3300d8b4");
            list.add(info);
        }

        if(dateCountInfoService.insertDateCountInfo(list)){
            logger.info("数据入库成功");
        }else{
            logger.info("数据入库失败");
        }




        return umengUappEventGetData(apiExecutor);
    }


    //获取事件统计数据
    public String umengUappEventGetData(ApiExecutor apiExecutor) {
        UmengUappEventGetDataParam param = new UmengUappEventGetDataParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey("519b040156240b7b3300d8b4");
        param.setStartDate("2020-03-01");
        param.setEndDate("2020-03-08");
        param.setEventName("XQ_ZCN_CarList_LeftMenuAll");

        try {
            UmengUappEventGetDataResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
            return JSONObject.toJSONString(result);
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
            return e.getErrorMessage();
        }
    }

    //获取所有App统计数据
    public String umengUappGetAllAppData(ApiExecutor apiExecutor) {
        UmengUappGetAllAppDataParam param = new UmengUappGetAllAppDataParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);

        try {
            UmengUappGetAllAppDataResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
            return JSONObject.toJSONString(result);
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
            return e.getErrorMessage();
        }
    }

    public void umengUappGetNewAccounts(ApiExecutor apiExecutor) {
        UmengUappGetNewAccountsParam param = new UmengUappGetNewAccountsParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey("519b040156240b7b3300d8b4");
        param.setStartDate("2018-01-01");
        param.setEndDate("2020-01-01");
        param.setPeriodType("monthly");
        param.setChannel("App%20Store");

        try {
            UmengUappGetNewAccountsResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
        }
    }

    //根据渠道或版本条件，获取App启动次数
    public void umengUappGetLaunchesByChannelOrVersion(ApiExecutor apiExecutor) {
        UmengUappGetLaunchesByChannelOrVersionParam param = new UmengUappGetLaunchesByChannelOrVersionParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey("519b040156240b7b3300d8b4");
        param.setStartDate("2018-01-01");
        param.setEndDate("2020-01-01");
        param.setPeriodType("daily");
        param.setChannels("App%20Store");
        param.setVersions("1.0.0");

        try {
            UmengUappGetLaunchesByChannelOrVersionResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
        }
    }

    //获取App今天与昨天的统计数据
    public void umengUappGetTodayYesterdayData(ApiExecutor apiExecutor) {
        UmengUappGetTodayYesterdayDataParam param = new UmengUappGetTodayYesterdayDataParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey("519b040156240b7b3300d8b4");

        try {
            UmengUappGetTodayYesterdayDataResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
        }
    }

    //获取事件列表
    public String umengUappEventList(ApiExecutor apiExecutor) {
        UmengUappEventListParam param = new UmengUappEventListParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey("519b040156240b7b3300d8b4");
        param.setStartDate("2020-03-01");
        param.setEndDate("2020-03-08");
        param.setPerPage(10);
        param.setPage(1);
        param.setVersion("");

        try {
            UmengUappEventListResult result = apiExecutor.execute(param);

            System.out.println(JSONObject.toJSONString(result));
            return JSONObject.toJSONString(result);
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
            return e.getErrorMessage();
        }
    }

}


