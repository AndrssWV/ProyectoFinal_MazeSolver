package models;

import java.util.Objects;

/**
 * Representa una celda individual en el laberinto mediante sus coordenadas de fila y columna.
 * Esta clase es fundamental para el sistema de resolución de laberintos, ya que cada
 * posición en la matriz del laberinto está representada por una instancia de Cell.
 * 
 * La clase implementa correctamente los métodos equals() y hashCode() para permitir
 * su uso en colecciones como Set y Map, lo cual es esencial para el seguimiento
 * de celdas visitadas y la comparación de posiciones durante los algoritmos de búsqueda.
 * 
 * Las coordenadas siguen el sistema estándar de matrices:
 * - row: índice de fila (0 = primera fila)
 * - col: índice de columna (0 = primera columna)
 * 
 * @author [Valeria Guamán, Jamileth Kumpanam, Sebastián López, Andrés Villalta]
 * @version 1.0
 */
public class Cell {
    /**
     * Índice de fila de la celda en la matriz del laberinto.
     * El valor 0 representa la primera fila (parte superior).
     */
    public int row;
    
    /**
     * Índice de columna de la celda en la matriz del laberinto.
     * El valor 0 representa la primera columna (parte izquierda).
     */
    public int col;

    /**
     * Constructor que crea una nueva celda con las coordenadas especificadas.
     * 
     * @param row Índice de fila de la celda (0-based, donde 0 es la primera fila)
     * @param col Índice de columna de la celda (0-based, donde 0 es la primera columna)
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Proporciona una representación textual de la celda en formato (fila, columna).
     * Este método es útil para depuración y logging durante la ejecución de algoritmos.
     * 
     * @return String en formato "(row, col)" que representa las coordenadas de la celda
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    /**
     * Genera un código hash basado en las coordenadas de fila y columna.
     * Este método es fundamental para el correcto funcionamiento de la clase
     * en colecciones basadas en hash como HashSet y HashMap.
     * 
     * Dos celdas con las mismas coordenadas tendrán el mismo código hash,
     * cumpliendo con el contrato de hashCode() en relación con equals().
     * 
     * @return Código hash calculado a partir de las coordenadas row y col
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * Determina si esta celda es igual a otro objeto basándose en sus coordenadas.
     * Dos celdas se consideran iguales si tienen la misma fila y columna.
     * 
     * Este método implementa correctamente el contrato de equals():
     * - Es reflexivo: x.equals(x) retorna true
     * - Es simétrico: x.equals(y) retorna true si y solo si y.equals(x) retorna true
     * - Es transitivo: si x.equals(y) y y.equals(z), entonces x.equals(z)
     * - Es consistente: múltiples invocaciones retornan el mismo resultado
     * - x.equals(null) retorna false
     * 
     * @param obj Objeto a comparar con esta celda
     * @return true si el objeto representa la misma celda (mismas coordenadas),
     *         false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (row != other.row)
            return false;
        if (col != other.col)
            return false;
        return true;
    }
}
