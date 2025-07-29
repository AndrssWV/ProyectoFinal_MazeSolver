package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    JComboBox<String> methods;
    private int rows;
    private int cols;

    public MazeFrame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        setTitle("MathWorks Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);
        setJMenuBar(createMenuBar());
        initUI();
        pack();
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem nuevoLaberinto = new JMenuItem("Nuevo Laberinto");
        JMenuItem verResultados = new JMenuItem("Ver resultados");

        nuevoLaberinto.addActionListener(e -> {
            String stringRows = JOptionPane.showInputDialog(this, "Ingrese número de filas:", "Dimensión Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringRows == null) return;
            String stringCols = JOptionPane.showInputDialog(this, "Ingrese número de columnas:", "Dimensión Maze", JOptionPane.QUESTION_MESSAGE);
            if (stringCols == null) return;
            try {
                int newRows = Integer.parseInt(stringRows);
                int newCols = Integer.parseInt(stringCols);
                if (newRows <= 0 || newCols <= 0) {
                    JOptionPane.showMessageDialog(this, "Las dimensiones deben ser valores positivos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    new MazeFrame(newRows, newCols);
                });
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(this, "Dimensiones no válidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        verResultados.addActionListener(e -> {
            ResultadosDialog dialog = new ResultadosDialog(this);
            dialog.setVisible(true);
        });

        menuArchivo.add(nuevoLaberinto);
        menuArchivo.add(verResultados);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        acercaDe.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Proyecto Final - Maze Solver\nAutor: Tu Nombre\n2025",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        menuAyuda.add(acercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);

        topToolBar.add(createModeButton("Set Start"));
        topToolBar.add(createModeButton("Set End"));
        topToolBar.add(createModeButton("Toggle Wall"));

        mainPanel.add(topToolBar, BorderLayout.NORTH);

        mazePanel = new MazePanel(rows, cols);
        mainPanel.add(mazePanel, BorderLayout.CENTER);

        JToolBar bottomToolBar = new JToolBar();
        bottomToolBar.setFloatable(false);

        methods = new JComboBox<>(new String[]{"Recursivo", "Completo", "Completo BT", "BFS", "DFS"});
        bottomToolBar.add(new JLabel("Algoritmo:"));
        bottomToolBar.add(methods);

        bottomToolBar.addSeparator();
        bottomToolBar.add(createMazeButton("Resolver"));
        bottomToolBar.add(createMazeButton("Paso a paso"));
        bottomToolBar.add(createMazeButton("Limpiar"));

        mainPanel.add(bottomToolBar, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JButton createModeButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = ((JButton)e.getSource()).getText();
                switch (command) {
                    case "Set Start":
                        mazePanel.setMode(MazePanel.Mode.SET_START);
                        break;
                    case "Set End":
                        mazePanel.setMode(MazePanel.Mode.SET_END);
                        break;
                    case "Toggle Wall":
                        mazePanel.setMode(MazePanel.Mode.TOGGLE_WALL);
                        break;
                }
            }
        });
        return button;
    }

    private JButton createMazeButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = ((JButton)e.getSource()).getText();
                switch (command) {
                    case "Set Start":
                        mazePanel.setMode(MazePanel.Mode.SET_START);
                        break;
                    case "Set End":
                        mazePanel.setMode(MazePanel.Mode.SET_END);
                        break;
                    case "Toggle Wall":
                        mazePanel.setMode(MazePanel.Mode.TOGGLE_WALL);
                        break;
                    case "Resolver":
                        mazePanel.solveMaze(methods.getSelectedItem().toString());
                        break;
                    case "Paso a paso":
                        mazePanel.solveMazeBySteps(methods.getSelectedItem().toString());
                        break;
                    case "Limpiar":
                        mazePanel.clearMaze();
                        break;
                }
            }
        });
        return button;
    }


}