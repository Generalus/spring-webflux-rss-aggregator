package com.thesn.rss.aggregator.generators;

import com.thesn.rss.aggregator.Event;

import java.util.Date;
import java.util.LinkedList;

class FluxState {
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
