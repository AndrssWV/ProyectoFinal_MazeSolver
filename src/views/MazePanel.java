package views;

import javax.swing.*;

import controllers.MazeController;
import models.Cell;
import models.CellState;
import models.SolveResults;
import models.AlgorithmResult;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

/**
 * Panel personalizado que maneja la visualización y edición interactiva del laberinto.
 * Esta clase extiende JPanel y proporciona una interfaz gráfica completa para:
 * crear, editar, visualizar y resolver laberintos utilizando diferentes algoritmos.
 * 
 * Funcionalidades principales:
 * - Visualización gráfica del laberinto con colores diferenciados por estado
 * - Edición interactiva mediante clics del mouse (inicio, fin, paredes)
 * - Ejecución de algoritmos de resolución con visualización de resultados
 * - Animaciones para mostrar el proceso de búsqueda paso a paso
 * - Modo de resolución automática y modo paso a paso
 * - Limpieza y reinicio del laberinto
 * 
 * Estados de celda soportados:
 * - EMPTY: Celda transitable (blanco)
 * - WALL: Pared u obstáculo (negro)
 * - START: Punto de inicio (verde)
 * - END: Punto de destino (rojo)
 * - VISITED: Celda explorada durante búsqueda (gris claro)
 * - PATH: Celda que forma parte del camino solución (azul)
 * 
 * Modos de edición:
 * - SET_START: Permite establecer el punto de inicio
 * - SET_END: Permite establecer el punto de destino
 * - TOGGLE_WALL: Permite alternar entre pared y celda vacía
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazePanel extends JPanel {
    /**
     * Enumeración que define los diferentes modos de edición del laberinto.
     * Cada modo determina qué acción se realiza cuando el usuario hace clic en una celda.
     */
    public enum Mode { 
        /** Modo para establecer el punto de inicio del laberinto */
        SET_START, 
        /** Modo para establecer el punto de destino del laberinto */
        SET_END, 
        /** Modo para alternar entre pared y celda vacía */
        TOGGLE_WALL 
    }
    
    /**
     * Controlador que maneja la lógica de resolución de laberintos.
     * Proporciona acceso a todos los algoritmos de resolución disponibles.
     */
    private MazeController controller;
    
    /**
     * Número de filas del laberinto.
     */
    private int rows;
    
    /**
     * Número de columnas del laberinto.
     */
    private int cols;
    
    /**
     * Modo de edición actual del panel.
     * Determina qué acción se ejecuta al hacer clic en una celda.
     */
    private Mode currentMode = Mode.SET_START;
    
    /**
     * Celda que representa el punto de inicio del laberinto.
     * null si no se ha establecido un punto de inicio.
     */
    private Cell start = null;
    
    /**
     * Celda que representa el punto de destino del laberinto.
     * null si no se ha establecido un punto de destino.
     */
    private Cell end = null;
    
    /**
     * Matriz que almacena el estado actual de cada celda del laberinto.
     * Cada celda puede tener uno de los estados definidos en CellState.
     */
    private CellState[][] grid;

    /**
     * Almacena el resultado del algoritmo para el modo de resolución paso a paso.
     * Permite continuar la visualización desde donde se quedó.
     */
    private AlgorithmResult solveBySteps;
    
    /**
     * Nombre del método/algoritmo que se está ejecutando en modo paso a paso.
     * Se utiliza para verificar si se cambió el algoritmo entre pasos.
     */
    private String methodBySteps;

    /**
     * Constructor que inicializa el panel del laberinto con las dimensiones especificadas.
     * Configura todos los componentes necesarios incluyendo el controlador, la grilla,
     * la apariencia visual y los manejadores de eventos del mouse.
     * 
     * @param rows Número de filas del laberinto (debe ser mayor que 0)
     * @param cols Número de columnas del laberinto (debe ser mayor que 0)
     */
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

    /**
     * Define el tamaño preferido del panel para la visualización del laberinto.
     * Establece dimensiones fijas que proporcionan un buen balance entre
     * visibilidad y espacio de pantalla.
     * 
     * @return Dimension con ancho de 900 píxeles y alto de 600 píxeles
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 600);
    }

    /**
     * Inicializa todas las celdas del laberinto al estado EMPTY.
     * Este método prepara la grilla para su uso, estableciendo
     * todas las celdas como transitables por defecto.
     */
    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = CellState.EMPTY;
            }
        }
    }
    
    /**
     * Renderiza la representación visual del laberinto.
     * Este método dibuja cada celda con el color correspondiente a su estado
     * y agrega bordes para delimitar visualmente las celdas.
     * 
     * Características del renderizado:
     * - Calcula automáticamente el tamaño de las celdas basado en las dimensiones del panel
     * - Centra el laberinto en el panel si hay espacio sobrante
     * - Utiliza los colores definidos en CellState para cada tipo de celda
     * - Dibuja bordes grises para delimitar las celdas
     * - Ajusta el color del texto para los estados START y END para mejor legibilidad
     * 
     * @param g Contexto gráfico donde se dibuja el laberinto
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Calcular dimensiones de celda y offset para centrado
        int cellw = getWidth() / cols;
        int cellh = getHeight() / rows;
        int offsetCol = (getWidth() - (cellw * cols)) / 2;
        int offsetRow = (getHeight() - (cellh * rows)) / 2;

        // Dibujar cada celda del laberinto
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int drawCol = offsetCol + col * cellw;
                int drawRow = offsetRow + row * cellh;

                // Dibujar el fondo de la celda con el color correspondiente a su estado
                g.setColor(grid[row][col].getColor());
                g.fillRect(drawCol, drawRow, cellw, cellh);

                // Dibujar el borde de la celda
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(drawCol, drawRow, cellw, cellh);

                // Ajustar color para mejor contraste en celdas especiales
                if (grid[row][col] == CellState.START) g.setColor(Color.WHITE);
                else if (grid[row][col] == CellState.END) g.setColor(Color.WHITE);
            }
        }
    }
    
    /**
     * Maneja los eventos de clic del mouse en el panel del laberinto.
     * Convierte las coordenadas del mouse en coordenadas de celda y ejecuta
     * la acción correspondiente según el modo de edición actual.
     * 
     * Comportamiento por modo:
     * - SET_START: Establece la celda clickeada como punto de inicio,
     *              removiendo el punto de inicio anterior si existe
     * - SET_END: Establece la celda clickeada como punto de destino,
     *            removiendo el punto de destino anterior si existe
     * - TOGGLE_WALL: Alterna entre pared y celda vacía, solo si la celda
     *                no es punto de inicio o destino
     * 
     * @param mouseX Coordenada X del clic del mouse en píxeles
     * @param mouseY Coordenada Y del clic del mouse en píxeles
     */
    private void handleCellClick(int mouseX, int mouseY) {  
        // Convertir coordenadas de mouse a coordenadas de celda
        int row = mouseY / (getHeight() / rows);
        int col = mouseX / (getWidth() / cols);
        
        // Verificar que las coordenadas estén dentro del laberinto
        if (col >= cols || row >= rows) return;
        
        // Ejecutar acción según el modo actual
        switch (currentMode) {
            case SET_START:
                // Limpiar punto de inicio anterior si existe
                if (start != null) grid[start.row][start.col] = CellState.EMPTY;
                start = new Cell(row, col);
                grid[row][col] = CellState.START;
                break;

            case SET_END:
                // Limpiar punto de destino anterior si existe
                if (end != null) grid[end.row][end.col] = CellState.EMPTY;
                end = new Cell(row, col);
                grid[row][col] = CellState.END;
                break;

            case TOGGLE_WALL:
                // Alternar entre pared y celda vacía
                if (grid[row][col] == CellState.WALL) {
                    grid[row][col] = CellState.EMPTY;
                } else if (grid[row][col] == CellState.EMPTY) {
                    grid[row][col] = CellState.WALL;
                }
                break;
        }
        repaint(); // Actualizar la visualización
    }

    /**
     * Establece el modo de edición del panel.
     * Este modo determina qué acción se ejecuta cuando el usuario hace clic en una celda.
     * 
     * @param mode Nuevo modo de edición (SET_START, SET_END, o TOGGLE_WALL)
     */
    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    /**
     * Ejecuta el algoritmo de resolución especificado y retorna el resultado.
     * Convierte la grilla visual de estados de celda a una matriz booleana
     * que pueden usar los algoritmos, y delega la resolución al controlador apropiado.
     * 
     * @param method Nombre del algoritmo a utilizar:
     *               "Recursivo", "Completo", "Completo BT", "BFS", o "DFS"
     * @return AlgorithmResult con el camino encontrado y las celdas visitadas,
     *         o null si el método no es reconocido
     */
    private AlgorithmResult getResult(String method) {
        // Convertir grilla de estados a matriz booleana para los algoritmos
        boolean[][] mazeBool = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // true = transitable, false = pared
                mazeBool[row][col] = grid[row][col] != CellState.WALL;
            }
        }
        
        // Ejecutar el algoritmo seleccionado
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
        return solve;
    }

    /**
     * Resuelve el laberinto con el algoritmo especificado y muestra el resultado con animación.
     * Limpia cualquier resolución anterior, ejecuta el algoritmo y visualiza
     * el proceso de búsqueda y el camino encontrado con temporizadores.
     * 
     * @param method Nombre del algoritmo a utilizar para resolver el laberinto
     */
    public void solveMaze(String method) {
        clearMaze(); // Limpiar resultados anteriores
        solveBySteps = null; // Resetear modo paso a paso
        methodBySteps = null;
        
        AlgorithmResult solve = getResult(method);
        setPath(solve); // Iniciar animación de resultados
    }

    /**
     * Visualiza el resultado del algoritmo con animación temporal.
     * Muestra primero todas las celdas visitadas durante la búsqueda,
     * y luego el camino encontrado, utilizando temporizadores para crear
     * una animación que ilustra el proceso de resolución.
     * 
     * Secuencia de animación:
     * 1. Muestra celdas visitadas una por una (intervalo: 100ms)
     * 2. Si se encuentra un camino, muestra el camino celda por celda (intervalo: 50ms)
     * 3. Si no hay camino, muestra un mensaje de error
     * 
     * @param solve Resultado del algoritmo con celdas visitadas y camino encontrado
     */
    private void setPath(AlgorithmResult solve) {
        Iterator<Cell> visited = solve.getVisited().iterator();
        
        // Timer para mostrar celdas visitadas
        Timer visitTimer = new Timer(100, e -> {
            if (visited.hasNext()) {
                Cell c = visited.next();
                // Solo colorear si no es inicio o fin
                if (grid[c.row][c.col] != CellState.START && grid[c.row][c.col] != CellState.END) {
                    grid[c.row][c.col] = CellState.VISITED;
                    repaint();
                }
            } else {
                // Finalizar timer de celdas visitadas
                ((Timer)e.getSource()).stop();
                
                // Verificar si se encontró un camino
                if (solve.getPath().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Camino No Encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Iniciar animación del camino encontrado
                Iterator<Cell> path = solve.getPath().iterator();
                Timer pathTimer = new Timer(50, ev -> {
                    if (path.hasNext()) {
                        Cell c = path.next();
                        // Solo colorear si no es inicio o fin
                        if (grid[c.row][c.col] != CellState.START && grid[c.row][c.col] != CellState.END) {
                            grid[c.row][c.col] = CellState.PATH;
                            repaint();
                        }
                    } else {
                        // Finalizar timer del camino
                        ((Timer) ev.getSource()).stop();
                    }
                });
                pathTimer.start();
            }
        });
        visitTimer.start();
    }

    /**
     * Ejecuta la resolución del laberinto en modo paso a paso.
     * Permite al usuario avanzar manualmente a través del proceso de resolución,
     * mostrando una celda visitada o una celda del camino en cada invocación.
     * 
     * Comportamiento:
     * - Si es la primera vez o cambió el método: inicia nueva resolución
     * - Si continúa el mismo método: muestra el siguiente paso
     * - Prioriza mostrar celdas visitadas antes que el camino
     * - Notifica cuando se completa la resolución
     * 
     * Estados del proceso:
     * 1. Mostrando celdas visitadas (una por invocación)
     * 2. Mostrando camino encontrado (una celda por invocación)
     * 3. Proceso completado (muestra mensaje y resetea)
     * 
     * @param method Nombre del algoritmo a utilizar para resolver el laberinto
     */
    public void solveMazeBySteps(String method) {
        if (solveBySteps != null) {
            // Continuar con el proceso paso a paso existente
            if (method.equals(methodBySteps)) {
                // Mostrar celdas visitadas primero
                if (solveBySteps.getVisited().size() > 0) {
                    Set<Cell> visited = solveBySteps.getVisited();
                    Cell step = visited.iterator().next();
                    visited.remove(step);
                    solveBySteps.setVisited(visited);
                    
                    // Solo colorear si no es inicio o fin
                    if (grid[step.row][step.col] != CellState.START && grid[step.row][step.col] != CellState.END) {
                        grid[step.row][step.col] = CellState.VISITED;
                        repaint();
                    }
                } 
                // Luego mostrar el camino
                else if (solveBySteps.getPath().size() > 0) {
                    List<Cell> path = solveBySteps.getPath();
                    Cell step = path.removeFirst();
                    solveBySteps.setPath(path);
                    
                    // Solo colorear si no es inicio o fin
                    if (grid[step.row][step.col] != CellState.START && grid[step.row][step.col] != CellState.END) {
                        grid[step.row][step.col] = CellState.PATH;
                        repaint();
                    }
                } 
                // Proceso completado
                else {
                    JOptionPane.showMessageDialog(null, "Camino se ha completado", "info", JOptionPane.INFORMATION_MESSAGE);
                    solveBySteps = null;
                    methodBySteps = null;
                    return;
                }
            } else {
                // Cambió el método: iniciar nueva resolución
                clearMaze();
                solveBySteps = getResult(method);
                methodBySteps = method;
                solveMazeBySteps(method); // Recursión para mostrar primer paso
            }
        } else {
            // Iniciar nueva resolución paso a paso
            clearMaze();
            solveBySteps = getResult(method);
            methodBySteps = method;
            solveMazeBySteps(method); // Recursión para mostrar primer paso
        }
    }

    /**
     * Limpia el laberinto de todos los resultados de resolución.
     * Remueve las celdas visitadas y el camino encontrado, manteniendo
     * solo las paredes, el punto de inicio y el punto de destino.
     * 
     * Estados preservados:
     * - START: Punto de inicio
     * - END: Punto de destino  
     * - WALL: Paredes del laberinto
     * 
     * Estados removidos:
     * - VISITED: Celdas exploradas durante búsqueda
     * - PATH: Celdas del camino solución
     * 
     * Las celdas limpiadas vuelven al estado EMPTY (transitable).
     */
    public void clearMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Preservar solo inicio, fin y paredes
                if (grid[row][col] != CellState.START && 
                    grid[row][col] != CellState.END && 
                    grid[row][col] != CellState.WALL) {
                    grid[row][col] = CellState.EMPTY;
                }
            }
        }
        repaint(); // Actualizar visualización
    }
}