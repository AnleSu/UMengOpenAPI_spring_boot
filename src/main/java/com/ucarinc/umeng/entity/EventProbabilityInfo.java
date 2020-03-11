package com.ucarinc.umeng.entity;

public class EventProbabilityInfo {
    private String date;
    private Integer count;
    private String name;
    private Double probabilit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getProbabilit() {
        return probabilit;
    }

    public void setProbabilit(Double probabilit) {
        this.probabilit = probabilit;
    }
}
