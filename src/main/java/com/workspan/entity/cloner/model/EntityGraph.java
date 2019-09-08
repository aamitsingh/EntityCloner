package com.workspan.entity.cloner.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity to its edges relationship
 */
public class EntityGraph {
    private int id;
    private Set<EntityGraph> edges = new HashSet<>();

    public EntityGraph(int id) {
        this.id = id;
        this.edges = edges;
    }

    public int getId() {
        return id;
    }

    public Set<EntityGraph> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityGraph that = (EntityGraph) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
