import javax.swing.*;
import views.MazeFrame;

/**
 * Clase principal de la aplicación Maze Solver.
 * 
 * <p>Esta es la clase de entrada que inicia la aplicación de resolución de laberintos.
 * Proporciona una interfaz de usuario simple para que el usuario especifique las 
 * dimensiones del laberinto antes de lanzar la ventana principal de la aplicación.</p>
 * 
 * <p>Características principales:</p>
 * <ul>
 *   <li>Solicita al usuario las dimensiones del laberinto (filas y columnas)</li>
 *   <li>Valida que las dimensiones sean valores numéricos positivos</li>
 *   <li>Inicia la interfaz gráfica principal del laberinto</li>
 *   <li>Maneja errores de entrada del usuario con mensajes apropiados</li>
 * </ul>
 * 
 * @author Sistema de Resolución de Laberintos
 * @version 1.0
 * @since 2025
 */
public class MazeApp {
    /**
     * Método principal de la aplicación.
     * 
     * <p>Este método es el punto de entrada de la aplicación que:</p>
     * <ol>
     *   <li>Solicita al usuario el número de filas para el laberinto</li>
     *   <li>Solicita al usuario el número de columnas para el laberinto</li>
     *   <li>Valida que ambas dimensiones sean números enteros positivos</li>
     *   <li>Crea e inicia la ventana principal del laberinto con las dimensiones especificadas</li>
     * </ol>
     * 
     * <p>El método utiliza {@code SwingUtilities.invokeLater()} para asegurar que 
     * la interfaz gráfica se ejecute en el Event Dispatch Thread.</p>
     * 
     * <p>Validaciones implementadas:</p>
     * <ul>
     *   <li>Verifica que el usuario no cancele los diálogos de entrada</li>
     *   <li>Valida que las entradas sean números enteros válidos</li>
     *   <li>Asegura que las dimensiones sean valores positivos (mayor que 0)</li>
     * </ul>
     * 
     * @param args argumentos de línea de comandos (no utilizados en esta aplicación)
     * 
     * @throws NumberFormatException si el usuario ingresa valores no numéricos
     * @throws IllegalArgumentException si las dimensiones son valores no positivos
     * 
     * @see MazeFrame#MazeFrame(int, int)
     * @see SwingUtilities#invokeLater(Runnable)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Solicitar número de filas al usuario
            String stringRows = JOptionPane.showInputDialog(null, "Ingrese numero de filas:", "Dimesion Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringRows==null) System.exit(0);
            
            // Solicitar número de columnas al usuario
            String stringCols = JOptionPane.showInputDialog(null, "Ingrese numero de columnas:", "Dimesion Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringCols==null) System.exit(0);
            
            try {
                // Convertir las cadenas de entrada a enteros
                int rows = Integer.parseInt(stringRows);
                int cols = Integer.parseInt(stringCols);
                if (rows<=0 || cols<=0) {
                    JOptionPane.showMessageDialog(null, "Las dimesiones deben ser valores positivos","Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                
                // Crear e iniciar la ventana principal del laberinto
                new MazeFrame(rows, cols);
            } catch (NumberFormatException err) {
                // Manejar error de formato numérico inválido
                JOptionPane.showMessageDialog(null, "Dimensiones No Validas", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}