package com.luhuan.rxlibrary;

public class EventBean<T> {

    public static final String NORMAL="NORMAL";

    private T event;

    private String tag;

    public EventBean(T event, String tag) {
        this.event = event;
        this.tag = tag;
    }

    public T getEvent() {
        return event;
    }

    public void setEvent(T event) {
        this.event = event;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
