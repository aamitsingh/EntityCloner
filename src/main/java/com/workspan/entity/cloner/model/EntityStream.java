package com.workspan.entity.cloner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EntityStream {
    @JsonProperty("entities")
    private List<Entity> entities;

    @JsonProperty("links")
    private List<Link> links;

    public EntityStream() {
    }

    public EntityStream(List<Entity> entities, List<Link> links) {
        this.entities = entities;
        this.links = links;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityStream that = (EntityStream) o;

        if (getEntities() != null ? !getEntities().equals(that.getEntities()) : that.getEntities() != null)
            return false;
        return getLinks() != null ? getLinks().equals(that.getLinks()) : that.getLinks() == null;
    }

    @Override
    public int hashCode() {
        int result = getEntities() != null ? getEntities().hashCode() : 0;
        result = 31 * result + (getLinks() != null ? getLinks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntityStream{" +
                "entities=" + entities +
                ", links=" + links +
                '}';
    }
}
