package com.thesn.rss.aggregator;


import java.util.Date;
import java.util.LinkedList;

public class FluxState {
    private LinkedList<Event> actualEvents;

    private Date actualDate;

    public FluxState() {
        this.actualEvents = new LinkedList<>();
        actualDate = new Date();
    }

    public LinkedList<Event> getActualEvents() {
        return actualEvents;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }
}
