package com.ucarinc.umeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.client.exception.OceanException;
import com.umeng.uapp.param.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UMengRequestCommon {
    //    租车iOS app_key
    public static final String ZC_APP_KEY = "519b040156240b7b3300d8b4";


    //获取事件统计数据
    public static String umengUappEventGetData(ApiExecutor apiExecutor, String eventName, String startDate, String endDate) {
        UmengUappEventGetDataParam param = new UmengUappEventGetDataParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey(ZC_APP_KEY);
        param.setStartDate(startDate == null || startDate.isEmpty() ? getYesterday() : startDate);
        param.setEndDate(endDate == null || endDate.isEmpty() ? getYesterday() : endDate);
        param.setEventName(eventName);

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
        param.setAppkey(ZC_APP_KEY);
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
        param.setAppkey(ZC_APP_KEY);
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
    public static void umengUappGetTodayYesterdayData(ApiExecutor apiExecutor) {
        UmengUappGetTodayYesterdayDataParam param = new UmengUappGetTodayYesterdayDataParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey(ZC_APP_KEY);

        try {
            UmengUappGetTodayYesterdayDataResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
        }
    }

    //获取事件列表
    public static String umengUappEventList(ApiExecutor apiExecutor, String startDate, String endDate, String version) {
        UmengUappEventListParam param = new UmengUappEventListParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey(ZC_APP_KEY);
        param.setStartDate(startDate == null || startDate.isEmpty() ? getYesterday() : startDate);
        param.setEndDate(endDate == null || endDate.isEmpty() ? getYesterday() : endDate);
        param.setPerPage(100);//暂时设置为最大一百 一页
        param.setPage(1);
        param.setVersion(version);

        try {
            UmengUappEventListResult result = apiExecutor.execute(param);

            System.out.println(JSONObject.toJSONString(result));
            return JSONObject.toJSONString(result);
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
            return e.getErrorMessage();
        }
    }

    //获取App启动次数
    public static String umengUappGetLaunches(ApiExecutor apiExecutor, String startDate, String endDate, String periodType) {
        UmengUappGetLaunchesParam param = new UmengUappGetLaunchesParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey(ZC_APP_KEY);
        param.setStartDate(startDate == null || startDate.isEmpty() ? getYesterday() : startDate);
        param.setEndDate(endDate == null || endDate.isEmpty() ? getYesterday() : endDate);
        param.setPeriodType(periodType);

        try {
            UmengUappGetLaunchesResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
            return JSONObject.toJSONString(result);
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
            return e.getErrorMessage();
        }
    }

    static String getYesterday() {
        Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = simpleDateFormat.format(today);//获取昨天日期
        System.out.println("yesterday is " + yesterday);
        return yesterday;
    }
}
