// LaberintoView.java (Vista)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LaberintoView extends JFrame {
    private JPanel gridPanel;
    private JPanel controlPanel;
    private JPanel actionPanel;
    private JButton createButton;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton recursivoButton;
    private JButton conCacheButton;
    private JButton clearButton;
    private JTextField rowsField;
    private JTextField colsField;
    private JLabel infoLabel;
    private JButton[][] gridButtons;

    private int startX = -1, startY = -1, endX = -1, endY = -1;

    public LaberintoView() {
        setTitle("Generador de Laberintos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridPanel = new JPanel();
        controlPanel = new JPanel();
        actionPanel = new JPanel();
        infoLabel = new JLabel("Información de la ruta");

        createButton = new JButton("Crear Cuadrícula");
        bfsButton = new JButton("BFS");
        dfsButton = new JButton("DFS");
        recursivoButton = new JButton("Recursivo");
        conCacheButton = new JButton("Con Cache");
        clearButton = new JButton("Limpiar");

        rowsField = new JTextField("Filas", 5);
        colsField = new JTextField("Columnas", 5);

        controlPanel.add(new JLabel("Filas:"));
        controlPanel.add(rowsField);
        controlPanel.add(new JLabel("Columnas:"));
        controlPanel.add(colsField);
        controlPanel.add(createButton);

        actionPanel.add(bfsButton);
        actionPanel.add(dfsButton);
        actionPanel.add(recursivoButton);
        actionPanel.add(conCacheButton);
        actionPanel.add(clearButton);

        add(controlPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        add(infoLabel, BorderLayout.WEST);

        createButton.addActionListener(e -> createGrid(Integer.parseInt(rowsField.getText()), Integer.parseInt(colsField.getText())));
    }

    public void createGrid(int rows, int cols) {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rows, cols));
        gridButtons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                int x = i, y = j;
                button.addActionListener(e -> {
                    if (button.getBackground() == Color.WHITE) {
                        button.setBackground(Color.BLACK);
                    } else if (button.getBackground() == Color.BLACK) {
                        button.setBackground(Color.WHITE);
                    }
                });

                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        if (evt.isControlDown()) {
                            setStart(x, y);
                            button.setBackground(Color.GREEN);
                        } else if (evt.isShiftDown()) {
                            setEnd(x, y);
                            button.setBackground(Color.RED);
                        }
                    }
                });

                gridPanel.add(button);
                gridButtons[i][j] = button;
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public JButton[][] getGridButtons() {
        return gridButtons;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getBfsButton() {
        return bfsButton;
    }

    public JButton getDfsButton() {
        return dfsButton;
    }

    public JButton getRecursivoButton() {
        return recursivoButton;
    }

    public JButton getConCacheButton() {
        return conCacheButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public int getRows() {
        return Integer.parseInt(rowsField.getText());
    }

    public int getCols() {
        return Integer.parseInt(colsField.getText());
    }

    public void setStart(int x, int y) {
        startX = x;
        startY = y;
    }

    public void setEnd(int x, int y) {
        endX = x;
        endY = y;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void updateInfo(String text) {
        infoLabel.setText(text);
    }
}
