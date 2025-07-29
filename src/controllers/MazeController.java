package controllers;

import models.Cell;
import models.AlgorithmResult;
import solver.solverImpl.MazeSolverBFS;
import solver.solverImpl.MazeSolverDFS;
import solver.solverImpl.MazeSolverRecursivo;
import solver.solverImpl.MazeSolverRecursivoCompleto;
import solver.solverImpl.MazeSolverRecursivoCompletoBT;

/**
 * Controlador principal para la resolución de laberintos.
 * Esta clase actúa como fachada para coordinar diferentes algoritmos de resolución
 * de laberintos, proporcionando una interfaz unificada para acceder a múltiples
 * implementaciones de algoritmos de búsqueda.
 * 
 * Los algoritmos disponibles incluyen:
 * - Búsqueda recursiva básica
 * - Búsqueda recursiva completa        
 * - Búsqueda recursiva con backtracking completo
 * - Búsqueda en anchura (BFS)
 * - Búsqueda en profundidad (DFS)
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeController {
    /**
     * Instancia del algoritmo de resolución recursivo básico.
     * Utiliza un enfoque recursivo simple para encontrar un camino válido.
     */
    private MazeSolverRecursivo recursivo;
    
    /**
     * Instancia del algoritmo de resolución recursivo completo.
     * Explora de manera más exhaustiva las posibles rutas.
     */
    private MazeSolverRecursivoCompleto recursivoCompleto;
    
    /**
     * Instancia del algoritmo de resolución recursivo con backtracking completo.
     * Implementa backtracking para encontrar la solución óptima.
     */
    private MazeSolverRecursivoCompletoBT recursivoCompletoBT;
    
    /**
     * Instancia del algoritmo de búsqueda en anchura (Breadth-First Search).
     * Garantiza encontrar el camino más corto en términos de número de pasos.
     */
    private MazeSolverBFS bfs;
    
    /**
     * Instancia del algoritmo de búsqueda en profundidad (Depth-First Search).
     * Explora tan profundo como sea posible antes de retroceder.
     */
    private MazeSolverDFS dfs;

    /**
     * Constructor del controlador de laberintos.
     * Inicializa todas las instancias de los algoritmos de resolución disponibles,
     * preparándolos para su uso posterior.
     */
    public MazeController() {
        recursivo = new MazeSolverRecursivo();
        recursivoCompleto = new MazeSolverRecursivoCompleto();
        recursivoCompletoBT = new MazeSolverRecursivoCompletoBT();
        bfs = new MazeSolverBFS();
        dfs = new MazeSolverDFS();
    }

    /**
     * Obtiene la solución del laberinto utilizando el algoritmo recursivo básico.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica camino libre
     *             y false indica pared
     * @param start Celda de inicio del laberinto
     * @param end Celda de destino del laberinto
     * @return AlgorithmResult que contiene el resultado de la ejecución del algoritmo,
     *         incluyendo el camino encontrado, estadísticas de rendimiento y éxito de la operación
     */
    public AlgorithmResult obtainRecursiveSolve(boolean[][] grid, Cell start, Cell end) {
        return recursivo.getPath(grid, start, end);
    }

    /**
     * Obtiene la solución del laberinto utilizando el algoritmo recursivo completo.
     * Este algoritmo realiza una exploración más exhaustiva comparado con el recursivo básico.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica camino libre
     *             y false indica pared
     * @param start Celda de inicio del laberinto
     * @param end Celda de destino del laberinto
     * @return AlgorithmResult que contiene el resultado de la ejecución del algoritmo,
     *         incluyendo el camino encontrado, estadísticas de rendimiento y éxito de la operación
     */
    public AlgorithmResult obtainCompleteSolve(boolean[][] grid, Cell start, Cell end) {
        return recursivoCompleto.getPath(grid, start, end);
    }

    /**
     * Obtiene la solución del laberinto utilizando el algoritmo recursivo con backtracking completo.
     * Este algoritmo implementa backtracking para explorar todas las posibles rutas
     * y encontrar la solución óptima.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica camino libre
     *             y false indica pared
     * @param start Celda de inicio del laberinto
     * @param end Celda de destino del laberinto
     * @return AlgorithmResult que contiene el resultado de la ejecución del algoritmo,
     *         incluyendo el camino encontrado, estadísticas de rendimiento y éxito de la operación
     */
    public AlgorithmResult obtainCompleteBTSolve(boolean[][] grid, Cell start, Cell end) {
        return recursivoCompletoBT.getPath(grid, start, end);
    }

    /**
     * Obtiene la solución del laberinto utilizando el algoritmo de búsqueda en anchura (BFS).
     * Este algoritmo garantiza encontrar el camino más corto en términos de número de pasos,
     * explorando nivel por nivel desde el punto de inicio.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica camino libre
     *             y false indica pared
     * @param start Celda de inicio del laberinto
     * @param end Celda de destino del laberinto
     * @return AlgorithmResult que contiene el resultado de la ejecución del algoritmo,
     *         incluyendo el camino más corto encontrado, estadísticas de rendimiento y éxito de la operación
     */
    public AlgorithmResult obtainBFSSolve(boolean[][] grid, Cell start, Cell end) {
        return bfs.getPath(grid, start, end);
    }
    
    /**
     * Obtiene la solución del laberinto utilizando el algoritmo de búsqueda en profundidad (DFS).
     * Este algoritmo explora tan profundo como sea posible en cada rama antes de retroceder,
     * no garantiza encontrar el camino más corto pero puede ser más eficiente en memoria.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica camino libre
     *             y false indica pared
     * @param start Celda de inicio del laberinto
     * @param end Celda de destino del laberinto
     * @return AlgorithmResult que contiene el resultado de la ejecución del algoritmo,
     *         incluyendo el camino encontrado, estadísticas de rendimiento y éxito de la operación
     */
    public AlgorithmResult obtainDFSSolve(boolean[][] grid, Cell start, Cell end) {
        return dfs.getPath(grid, start, end);
    }
}
