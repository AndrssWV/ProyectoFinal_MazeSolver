package models;

import java.util.List;
import java.util.Set;

public class AlgorithmResult {
    private List<Cell> path;
    private Set<Cell> visited;

    public AlgorithmResult(List<Cell> path, Set<Cell> visited) {
        this.path = path;
        this.visited = visited;
    }

    public List<Cell> getPath() {
        return path;
    }

    public Set<Cell> getVisited() {
        return visited;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
    }

    public void setVisited(Set<Cell> visited) {
        this.visited = visited;
    }

    
}
