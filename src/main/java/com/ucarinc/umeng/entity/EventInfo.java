package com.ucarinc.umeng.entity;

import java.io.Serializable;

public class EventInfo implements Serializable {

    private String name;

    private Integer count;

    private String id;

    private String displayName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  Integer getCount() {
        return  count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "EventInfo name" + name + "count" + count + "id" + id + "displayname" + displayName;
    }
}
