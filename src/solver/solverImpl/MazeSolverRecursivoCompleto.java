package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

public class MazeSolverRecursivoCompleto implements MazeSolver {
    private boolean[][] grid;
    private List<Cell> path;
    private Set<Cell> visited;
    private Cell end;

    public MazeSolverRecursivoCompleto() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
    }

    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
        this.grid = grid;
        this.end = end;
        if (grid == null || grid.length == 0) return new AlgorithmResult(path, visited);
        if (findPath(start)) return new AlgorithmResult(path, visited);
        return new AlgorithmResult(new ArrayList<>(), new LinkedHashSet<>());
    }

    private boolean findPath(Cell current) {
        if (!isInMaze(current) || !isValid(current)) return false;
        visited.add(current);
        if (current.equals(end)) {
            path.add(current);
            return true;
        }
        for (int[] dir : directions) {            
            if (findPath(new Cell(current.row + dir[0], current.col + dir[1]))) {
                path.add(current);
                return true;
            }
        }
        return false;
    }

    private boolean isInMaze(Cell current) {
        return current != null && 
               current.row >= 0 && 
               current.col >= 0 && 
               current.row < grid.length && 
               current.col < grid[0].length;
    }

    private boolean isValid(Cell current) {
        return grid[current.row][current.col] && !visited.contains(current);
    }
    
}
