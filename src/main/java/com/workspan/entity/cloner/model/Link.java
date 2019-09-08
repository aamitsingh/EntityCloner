package com.workspan.entity.cloner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty("from")
    private int from;

    @JsonProperty("to")
    private int to;

    public Link() {
    }

    public Link(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (getFrom() != link.getFrom()) return false;
        return getTo() == link.getTo();
    }

    @Override
    public int hashCode() {
        int result = getFrom();
        result = 31 * result + getTo();
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
