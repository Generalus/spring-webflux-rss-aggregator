package com.thesn.search.aggregator;

public class Event {
    private String content;

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public Event() {
    }

    public Event(String content) {
        this.content = content;
        number = 10;
    }

    public void setContent(String content) {
        this.content = content;
    }

}