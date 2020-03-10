package com.ucarinc.umeng.entity;

import java.io.Serializable;
import java.util.List;

public class EventData implements Serializable {
    public List<EventDataItem> eventData;

    public static class EventDataItem implements Serializable {
        public List<Integer> data;
        public List<String> dates;
    }
}
