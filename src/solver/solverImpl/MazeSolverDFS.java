
package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

/**
 * Implementación del algoritmo de búsqueda en profundidad (Depth-First Search) para resolver laberintos.
 * Esta clase implementa la interfaz MazeSolver utilizando DFS, que explora tan profundo como
 * sea posible en cada rama antes de retroceder (backtracking).
 * 
 * Características del algoritmo DFS:
 * - Explora en profundidad siguiendo un camino hasta que no puede continuar
 * - No garantiza encontrar el camino más corto (a diferencia de BFS)
 * - Utiliza una pila (Stack) para procesar las celdas en orden LIFO
 * - Puede ser más eficiente en memoria que BFS en laberintos profundos
 * - Mantiene un mapa de padres para reconstruir el camino encontrado
 * - Marca las celdas como visitadas para evitar ciclos infinitos
 * 
 * Complejidad temporal: O(V + E) donde V es el número de celdas y E el número de conexiones
 * Complejidad espacial: O(V) para almacenar la pila, visitados y padres
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeSolverDFS implements MazeSolver {
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
     * Conjunto de celdas visitadas durante la ejecución del algoritmo.
     * Se utiliza HashSet para verificación rápida de membresía y evitar ciclos.
     */
    private Set<Cell> visited;
    
    /**
     * Celda de destino del laberinto. Se almacena para facilitar la comparación
     * durante la búsqueda y determinar cuándo se ha alcanzado el objetivo.
     */
    private Cell end;
    
    /**
     * Matriz que define las direcciones de movimiento posibles en el laberinto.
     * Representa los movimientos: arriba, abajo, izquierda, derecha.
     */
    private final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * Implementación del algoritmo DFS para encontrar un camino en el laberinto.
     * Este método utiliza una pila para explorar en profundidad desde el punto de inicio
     * hasta encontrar el destino. A diferencia de BFS, no garantiza el camino más corto,
     * pero puede ser más eficiente en memoria para laberintos muy grandes.
     * 
     * Proceso del algoritmo:
     * 1. Inicializa las estructuras de datos y agrega el punto de inicio a la pila
     * 2. Mientras la pila no esté vacía, procesa cada celda:
     *    - Extrae la celda del tope de la pila (LIFO)
     *    - Si es el destino, reconstruye y retorna el camino
     *    - Si no, explora las celdas vecinas válidas y las agrega a la pila
     * 3. Si la pila se vacía sin encontrar el destino, retorna un camino vacío
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica
     *             celda transitable y false indica pared
     * @param start Celda de inicio de la búsqueda
     * @param end Celda de destino a alcanzar
     * @return AlgorithmResult que contiene el camino encontrado y
     *         el conjunto de celdas visitadas durante la búsqueda
     */
    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        // Inicializar estructuras de datos para nueva búsqueda
        this.grid = grid;
        this.path = new ArrayList<>();
        this.parents = new HashMap<>();
        this.visited = new HashSet<>();
        this.end = end;
        
        // Validación de entrada
        if (grid == null || grid.length == 0 || !isInMaze(start) || !isInMaze(end)) {
            return new AlgorithmResult(new ArrayList<>(), new HashSet<>());
        }
        
        // Inicializar DFS con pila y punto de inicio
        Stack<Cell> stack = new Stack<>();
        stack.push(start);
        visited.add(start);
        parents.put(start, null);

        // Bucle principal de DFS
        while (!stack.isEmpty()) {
            Cell current = stack.pop();  // LIFO: Last In, First Out
            
            // Verificar si se alcanzó el destino
            if (current.equals(end)) {
                // Reconstruir camino mediante backtracking
                Cell node = end;
                while (node != null) {
                    path.add(0, node);
                    node = parents.get(node);
                }
                return new AlgorithmResult(path, visited);
            }
            
            // Explorar celdas vecinas en todas las direcciones
            for (int[] dir : directions) {
                Cell nextCell = new Cell(current.row + dir[0], current.col + dir[1]);
                if (isInMaze(nextCell) && isValid(nextCell)) {
                    visited.add(nextCell);
                    parents.put(nextCell, current);
                    stack.push(nextCell);  // Agregar a la pila para exploración posterior
                }
            }
        }
        
        // No se encontró camino al destino
        return new AlgorithmResult(new ArrayList<>(), visited);
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