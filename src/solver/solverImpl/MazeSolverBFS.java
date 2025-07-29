package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

/**
 * Implementación del algoritmo de búsqueda en anchura (Breadth-First Search) para resolver laberintos.
 * Esta clase implementa la interfaz MazeSolver utilizando BFS, que garantiza encontrar el camino
 * más corto en términos de número de pasos desde el punto de inicio hasta el destino.
 * 
 * Características del algoritmo BFS:
 * - Explora nivel por nivel desde el punto de inicio
 * - Garantiza encontrar el camino más corto (en número de pasos)
 * - Utiliza una cola (Queue) para procesar las celdas en orden FIFO
 * - Mantiene un mapa de padres para reconstruir el camino encontrado
 * - Marca las celdas como visitadas para evitar ciclos infinitos
 * 
 * Complejidad temporal: O(V + E) donde V es el número de celdas y E el número de conexiones
 * Complejidad espacial: O(V) para almacenar la cola, visitados y padres
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeSolverBFS implements MazeSolver {
    /**
     * Matriz booleana que representa el laberinto.
     * true indica una celda transitable, false indica una pared u obstáculo.
     */
    private boolean[][] grid;
    
    /**
     * Lista que almacena el camino encontrado desde el inicio hasta el destino.
     * Se construye al final del algoritmo mediante backtracking usando el mapa de padres.
     */
    private List<Cell> path;
    
    /**
     * Mapa que mantiene la relación padre-hijo entre celdas durante la búsqueda.
     * Permite reconstruir el camino desde el destino hasta el inicio una vez
     * que se encuentra la solución.
     */
    private Map<Cell, Cell> parents;
    
    /**
     * Conjunto ordenado de celdas visitadas durante la ejecución del algoritmo.
     * Se utiliza LinkedHashSet para mantener el orden de inserción y evitar duplicados.
     */
    private Set<Cell> visited;
    
    /**
     * Celda de destino del laberinto. Se almacena para facilitar la comparación
     * durante la búsqueda y determinar cuándo se ha alcanzado el objetivo.
     */
    private Cell end;
    
    /**
     * Matriz que define las direcciones de movimiento posibles en el laberinto.
     * Representa los movimientos: arriba, derecha, abajo, izquierda.
     */
    private static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    /**
     * Constructor que inicializa todas las estructuras de datos necesarias para el algoritmo BFS.
     * Prepara las colecciones vacías que serán utilizadas durante la ejecución del algoritmo.
     */
    public MazeSolverBFS() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        parents = new HashMap<>();
        visited = new LinkedHashSet<>();
    }

    /**
     * Implementación del algoritmo BFS para encontrar el camino más corto en el laberinto.
     * Este método utiliza una cola para explorar nivel por nivel desde el punto de inicio
     * hasta encontrar el destino, garantizando el camino más corto en número de pasos.
     * 
     * Proceso del algoritmo:
     * 1. Inicializa las estructuras de datos y agrega el punto de inicio a la cola
     * 2. Mientras la cola no esté vacía, procesa cada celda:
     *    - Extrae la celda actual de la cola
     *    - Si es el destino, reconstruye y retorna el camino
     *    - Si no, explora las celdas vecinas válidas
     * 3. Si la cola se vacía sin encontrar el destino, retorna un camino vacío
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica
     *             celda transitable y false indica pared
     * @param start Celda de inicio de la búsqueda
     * @param end Celda de destino a alcanzar
     * @return AlgorithmResult que contiene el camino más corto encontrado y
     *         el conjunto de celdas visitadas durante la búsqueda
     */
    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        // Reinicializar estructuras de datos para nueva búsqueda
        path = new ArrayList<>();
        parents = new HashMap<>();
        visited = new LinkedHashSet<>();
        this.grid = grid;
        this.end = end;
        
        // Validación de entrada
        if (grid == null || grid.length == 0) return new AlgorithmResult(path, visited);
        
        // Inicializar BFS con cola y punto de inicio
        Queue<Cell> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        parents.put(start, null);
        
        // Bucle principal de BFS
        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            
            // Verificar si se alcanzó el destino
            if (current.equals(end)) {
                // Reconstruir camino mediante backtracking
                while (current != null) {
                    path.add(0, current);
                    current = parents.get(current);
                }
                return new AlgorithmResult(path, visited);
            }
            
            // Explorar celdas vecinas
            findPath(current, queue);
        }
        
        // No se encontró camino al destino
        return new AlgorithmResult(new ArrayList<>(), visited);
    }

    /**
     * Explora las celdas vecinas de la celda actual y las agrega a la cola si son válidas.
     * Este método recorre las cuatro direcciones posibles (arriba, derecha, abajo, izquierda)
     * y para cada celda vecina válida:
     * - La marca como visitada
     * - Establece la relación padre-hijo para reconstrucción del camino
     * - La agrega a la cola para procesamiento posterior
     * 
     * @param current Celda actual desde la cual explorar los vecinos
     * @param queue Cola de BFS donde se agregan las celdas vecinas válidas
     */
    private void findPath(Cell current, Queue<Cell> queue) {
        for (int[] dir : directions) {
            Cell nextCell = new Cell(current.row + dir[0], current.col + dir[1]);
            if (isInMaze(nextCell) && isValid(nextCell)) {
                visited.add(nextCell);
                parents.put(nextCell, current);
                queue.add(nextCell);
            }
        }
    }

    /**
     * Verifica si una celda está dentro de los límites del laberinto.
     * Comprueba que las coordenadas de fila y columna estén dentro del rango
     * válido de la matriz del laberinto.
     * 
     * @param current Celda a verificar
     * @return true si la celda está dentro de los límites del laberinto,
     *         false si está fuera de los límites o si current es null
     */
    private boolean isInMaze(Cell current) {
        return current != null && 
               current.row >= 0 && 
               current.col >= 0 && 
               current.row < grid.length && 
               current.col < grid[0].length;
    }
    
    /**
     * Verifica si una celda es válida para ser visitada.
     * Una celda es válida si:
     * - Es transitable (grid[row][col] == true)
     * - No ha sido visitada previamente
     * 
     * @param current Celda a verificar
     * @return true si la celda es transitable y no ha sido visitada,
     *         false en caso contrario
     */
    private boolean isValid(Cell current) {
        return grid[current.row][current.col] && !visited.contains(current);
    }
}