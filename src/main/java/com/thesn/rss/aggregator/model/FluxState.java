package com.thesn.rss.aggregator.model;


import java.util.Date;
import java.util.LinkedList;

class FluxState {
    private LinkedList<Event> actualEvents;

    private Date actualDate;

    public FluxState() {
        this.actualEvents = new LinkedList<>();
        this.actualDate = new Date();
    }

    public LinkedList<Event> getActualEvents() {
        return actualEvents;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(final Date actualDate) {
        this.actualDate = actualDate;
    }
}
