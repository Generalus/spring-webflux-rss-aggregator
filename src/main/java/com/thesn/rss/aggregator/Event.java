package com.thesn.rss.aggregator;

import java.util.Date;

public class Event {

    private String url;    // represents event id

    private String title;  // stackoverflow post title

    private String source; // непосредственный источник события

    private Date date;

    public Event() {
    }

    public Event(String url, String title, String source, Date date) {
        this.url = url;
        this.title = title;
        this.source = source;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }


    public String getTitle() {
        return title;
    }


    public String getSource() {
        return source;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return url != null ? url.equals(event.url) : event.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}