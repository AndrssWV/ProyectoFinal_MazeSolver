package views;

import javax.swing.*;

import controllers.MazeController;
import models.Cell;
import models.CellState;
import models.AlgorithmResult;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Set;

public class MazePanel extends JPanel {
    public enum Mode { SET_START, SET_END, TOGGLE_WALL }
    private MazeController controller;
    private int rows;
    private int cols;
    private Mode currentMode = Mode.TOGGLE_WALL;
    private Cell start = null;
    private Cell end = null;
    private CellState[][] grid;

    public MazePanel(int rows, int cols) {
        controller = new MazeController();
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellState[rows][cols];
        initializeGrid();
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCellClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 600);
    }

    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = CellState.EMPTY;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellw = getWidth() / cols;
        int cellh = getHeight() / rows;
        int offsetCol = (getWidth() - (cellw * cols)) / 2;
        int offsetRow = (getHeight() - (cellh * rows)) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int drawCol = offsetCol + col * cellw;
                int drawRow = offsetRow + row * cellh;

                g.setColor(grid[row][col].getColor());
                g.fillRect(drawCol, drawRow, cellw, cellw);

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(drawCol, drawRow, cellw, cellh);

                if (grid[row][col] == CellState.START) g.setColor(Color.WHITE);
                else if (grid[row][col] == CellState.END) g.setColor(Color.WHITE);
            }
        }
    }
    
    private void handleCellClick(int mouseX, int mouseY) {  
        int row = mouseY / (getHeight() / rows);
        int col = mouseX / (getWidth() / cols);
        if (col >= cols || row >= rows) return;
        
        switch (currentMode) {
            case SET_START:
                if (start != null) grid[start.row][start.col] = CellState.EMPTY;
                start = new Cell(row, col);
                grid[row][col] = CellState.START;
                break;

            case SET_END:
                if (end != null) grid[end.row][end.col] = CellState.EMPTY;
                end = new Cell(row, col);
                grid[row][col] = CellState.END;
                break;

            case TOGGLE_WALL:
                if (grid[row][col] == CellState.WALL) {
                    grid[row][col] = CellState.EMPTY;
                } else if (grid[row][col] == CellState.EMPTY) {
                    grid[row][col] = CellState.WALL;
                }
                break;
        }
        repaint();
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    public void solveMaze(String method) {
        boolean[][] mazeBool = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                mazeBool[row][col] = grid[row][col] != CellState.WALL;
            }
        }
        AlgorithmResult solve = null;
        switch(method) {
            case "Recursivo":
                solve = controller.obtainRecursiveSolve(mazeBool, start, end);
                break;
            case "Completo":
                solve = controller.obtainCompleteSolve(mazeBool, start, end);
                break;
            case "Completo BT":
                solve = controller.obtainCompleteBTSolve(mazeBool, start, end);
                break;
            case "BFS":
                solve = controller.obtainBFSSolve(mazeBool, start, end);
                break;
            case "DFS":
                solve = controller.obtainDFSSolve(mazeBool, start, end);
                break;
            default:
                break;
        }
        if (solve.getPath().size()>0) setPath(solve);
        else JOptionPane.showMessageDialog(null, "Camino No Encontrado", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void setPath(AlgorithmResult solve) {
        int tiempoDeEsperaMs = 200;
        Iterator<Cell> visited = solve.getVisited().iterator();
        Timer visitTimer = new Timer(tiempoDeEsperaMs, e -> {
            if (visited.hasNext()) {
                Cell c = visited.next();
                if (grid[c.row][c.col] != CellState.START && grid[c.row][c.col] != CellState.END) {
                    grid[c.row][c.col] = CellState.VISITED;
                    repaint();
                }
            } else {
                ((Timer)e.getSource()).stop();
                Iterator<Cell> path = solve.getPath().iterator();
                Timer pathTimer = new Timer(tiempoDeEsperaMs, ev -> {
                    if (path.hasNext()) {
                        Cell c = path.next();
                        if (grid[c.row][c.col] != CellState.START && grid[c.row][c.col] != CellState.END) {
                            grid[c.row][c.col] = CellState.PATH;
                            repaint();
                        }
                    } else {
                        ((Timer)ev.getSource()).stop();
                    }
                });
                pathTimer.start();
            }
        });
        visitTimer.start();
    }

    public void stepSolve() {
        //solveMaze();
    }

    public void clearMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != CellState.START && grid[row][col] != CellState.END && grid[row][col] != CellState.WALL) {
                    grid[row][col] = CellState.EMPTY;
                }
            }
        }
        repaint();
    }
}