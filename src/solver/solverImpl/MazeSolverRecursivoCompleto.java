package solver.solverImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import models.Cell;
import models.AlgorithmResult;
import solver.MazeSolver;

/**
 * Implementación del algoritmo de resolución recursiva completa para laberintos.
 * Esta clase utiliza un enfoque recursivo más exhaustivo que el algoritmo recursivo básico,
 * proporcionando una exploración más sistemática del laberinto.
 * 
 * Características del algoritmo recursivo completo:
 * - Utiliza recursión para explorar el laberinto en profundidad
 * - Realiza una exploración más exhaustiva que el algoritmo recursivo básico
 * - Mantiene un registro completo de todas las celdas visitadas durante la búsqueda
 * - Construye el camino agregando celdas cuando encuentra una ruta exitosa
 * - Explora sistemáticamente todas las direcciones posibles desde cada celda
 * - No implementa backtracking verdadero (a diferencia de MazeSolverRecursivoCompletoBT)
 * 
 * La diferencia principal con el algoritmo recursivo básico es que este mantiene
 * un seguimiento más detallado del proceso de búsqueda y proporciona información
 * más completa sobre las celdas exploradas.
 * 
 * Complejidad temporal: O(4^(N*M)) en el peor caso, donde N y M son las dimensiones del laberinto
 * Complejidad espacial: O(N*M) para la pila de recursión y estructuras de datos
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class MazeSolverRecursivoCompleto implements MazeSolver {
    /**
     * Matriz booleana que representa el laberinto.
     * true indica una celda transitable, false indica una pared u obstáculo.
     */
    private boolean[][] grid;
    
    /**
     * Lista que almacena el camino encontrado desde el inicio hasta el destino.
     * Se construye dinámicamente durante la búsqueda, agregando celdas cuando
     * se encuentra una ruta exitosa hacia el destino.
     */
    private List<Cell> path;
    
    /**
     * Conjunto ordenado de todas las celdas visitadas durante la búsqueda.
     * Se utiliza LinkedHashSet para mantener el orden de visita y evitar
     * revisitar celdas, previniendo ciclos infinitos en la recursión.
     */
    private Set<Cell> visited;
    
    /**
     * Celda de destino del laberinto. Se utiliza para determinar cuándo
     * se ha alcanzado el objetivo durante la búsqueda recursiva.
     */
    private Cell end;
    
    /**
     * Matriz que define las direcciones de movimiento posibles en el laberinto.
     * Representa los movimientos: arriba, derecha, abajo, izquierda.
     */
    private static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    /**
     * Constructor que inicializa todas las estructuras de datos necesarias.
     * Prepara las colecciones vacías que serán utilizadas durante la búsqueda recursiva.
     */
    public MazeSolverRecursivoCompleto() {
        grid = new boolean[][] {};
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
    }

    /**
     * Método principal que inicia la búsqueda recursiva completa en el laberinto.
     * Configura las estructuras de datos y delega la búsqueda al método recursivo,
     * proporcionando un enfoque más exhaustivo que el algoritmo recursivo básico.
     * 
     * Este método implementa el patrón de inicialización y delegación, preparando
     * el entorno para la búsqueda recursiva y manejando casos especiales de entrada.
     * 
     * @param grid Matriz booleana que representa el laberinto donde true indica
     *             celda transitable y false indica pared
     * @param start Celda de inicio de la búsqueda
     * @param end Celda de destino a alcanzar
     * @return AlgorithmResult que contiene el camino encontrado (si existe) y
     *         todas las celdas visitadas durante la búsqueda exhaustiva
     */
    @Override
    public AlgorithmResult getPath(boolean[][] grid, Cell start, Cell end) {
        // Reinicializar estructuras de datos para nueva búsqueda
        path = new ArrayList<>();
        visited = new LinkedHashSet<>();
        this.grid = grid;
        this.end = end;
        
        // Validación de entrada
        if (grid == null || grid.length == 0) return new AlgorithmResult(path, visited);
        
        // Iniciar búsqueda recursiva
        if (findPath(start)) {
            return new AlgorithmResult(path, visited);
        }
        
        // No se encontró camino
        return new AlgorithmResult(new ArrayList<>(), new LinkedHashSet<>());
    }

    /**
     * Método recursivo que implementa la búsqueda completa en el laberinto.
     * Este método explora recursivamente todas las posibles rutas desde la celda actual
     * hasta encontrar el destino, construyendo el camino de manera progresiva.
     * 
     * Características de este algoritmo recursivo completo:
     * - Marca cada celda como visitada antes de explorarla
     * - Cuando alcanza el destino, agrega la celda al camino y retorna éxito
     * - Explora sistemáticamente todas las direcciones posibles
     * - Construye el camino agregando celdas cuando encuentra una ruta exitosa
     * - No implementa backtracking: las celdas visitadas permanecen marcadas
     * 
     * Proceso del algoritmo:
     * 1. Verifica si la celda actual es válida (dentro del laberinto y transitable)
     * 2. Marca la celda como visitada para evitar ciclos
     * 3. Si la celda actual es el destino, la agrega al camino y retorna éxito
     * 4. Explora recursivamente todas las direcciones posibles
     * 5. Si alguna dirección lleva al éxito, agrega la celda actual al camino
     * 6. Si ninguna dirección funciona, retorna falso sin modificar el camino
     * 
     * @param current Celda actual siendo explorada en la recursión
     * @return true si desde esta celda se puede alcanzar el destino, false en caso contrario
     */
    private boolean findPath(Cell current) {
        // Verificar validez de la celda actual
        if (!isInMaze(current) || !isValid(current)) return false;
        
        // Marcar celda como visitada
        visited.add(current);
        
        // Caso base: se alcanzó el destino
        if (current.equals(end)) {
            path.add(current);  // Agregar destino al camino
            return true;
        }
        
        // Explorar recursivamente todas las direcciones
        for (int[] dir : directions) {            
            if (findPath(new Cell(current.row + dir[0], current.col + dir[1]))) {
                // Si se encontró un camino válido, agregar celda actual al path
                path.add(current);
                return true;
            }
        }
        
        // No se encontró camino desde esta celda
        return false;
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
     * Verifica si una celda es válida para ser visitada durante la búsqueda recursiva.
     * Una celda es válida si:
     * - Es transitable (grid[row][col] == true)
     * - No ha sido visitada previamente en la búsqueda actual
     * 
     * Esta verificación es fundamental para evitar ciclos infinitos en la recursión
     * y asegurar que solo se exploren celdas que pueden formar parte de un camino válido.
     * 
     * @param current Celda a verificar
     * @return true si la celda es transitable y no ha sido visitada,
     *         false en caso contrario
     */
    private boolean isValid(Cell current) {
        return grid[current.row][current.col] && !visited.contains(current);
    }
    
}
