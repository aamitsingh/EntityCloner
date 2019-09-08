package com.workspan.entity.cloner.model;

import java.util.Map;
import java.util.Set;

/**
 * Graph containing the Vertex and edges of each entity along with the Parents of the given entityId
 */
public class GraphResult {
    Map<Integer, EntityGraph> entityGraphMap;
    Set<Integer> parents;

    public GraphResult(Map<Integer, EntityGraph> entityGraphMap, Set<Integer> parents) {
        this.entityGraphMap = entityGraphMap;
        this.parents = parents;
    }

    public Map<Integer, EntityGraph> getEntityGraphMap() {
        return entityGraphMap;
    }

    public Set<Integer> getParents() {
        return parents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphResult that = (GraphResult) o;

        if (getEntityGraphMap() != null ? !getEntityGraphMap().equals(that.getEntityGraphMap()) : that.getEntityGraphMap() != null)
            return false;
        return getParents() != null ? getParents().equals(that.getParents()) : that.getParents() == null;
    }

    @Override
    public int hashCode() {
        int result = getEntityGraphMap() != null ? getEntityGraphMap().hashCode() : 0;
        result = 31 * result + (getParents() != null ? getParents().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GraphResult{" +
                "entityGraphMap=" + entityGraphMap +
                ", parents=" + parents +
                '}';
    }
}
