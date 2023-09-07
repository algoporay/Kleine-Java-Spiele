// Geschrieben von Rexhaj Arianit
package ProjektSpiele;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VierGewinnt extends JFrame implements ActionListener {

    public static void FunktionVierGewinnt() {
        VierGewinnt game = new VierGewinnt();
        game.setVisible(true);
    }

    private static final long serialVersionUID = 1L;
    private JButton[][] buttons;        // Array zur Darstellung der Spielsteine
    private JPanel gamePanel, startPanel, endPanel;    // Panels zur Organisation des Spielfensters
    private JLabel messageLabel;       // Label zur Anzeige von Spielinformationen
    private JButton startButton, resetButton;    // Buttons für Start und Zurücksetzen
    private int[][] board;              // Array zur internen Speicherung des Spielbretts
    private boolean player1Turn;        // Variable zur Verfolgung des Spielers am Zug
    private boolean gameOver;           // Variable zur Überprüfung des Spielendes

    public VierGewinnt() {
        super("4 Gewinnt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        gamePanel = new JPanel(new GridLayout(6, 7));    // Spielfeld mit 6 Reihen und 7 Spalten
        buttons = new JButton[6][7];                     // Initialisierung des Button-Arrays
        board = new int[6][7];                           // Initialisierung des Spielbrett-Arrays
        player1Turn = true;                              // Spieler 1 beginnt
        gameOver = false;                                // Spielstart, kein Spielende

        // Schleife zum Erzeugen der Buttons für das Spielfeld
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttons[row][col].addActionListener(this);
                gamePanel.add(buttons[row][col]);
            }
        }

        startPanel = new JPanel(new BorderLayout());
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startPanel.add(startButton, BorderLayout.CENTER);

        endPanel = new JPanel(new BorderLayout());
        messageLabel = new JLabel();
        resetButton = new JButton("Zurück zum Startmenü");
        resetButton.addActionListener(this);
        endPanel.add(messageLabel, BorderLayout.CENTER);
        endPanel.add(resetButton, BorderLayout.SOUTH);

        getContentPane().add(startPanel);

        validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            getContentPane().remove(startPanel);
            getContentPane().add(gamePanel);
            validate();
        } else if (e.getSource() == resetButton) {
            variablenreset();
            JFrame programmfenster = Startseite.getProgrammfenster();
            Startseite.startseite(programmfenster);
        } else if (!gameOver) {
            // Schleife zum Überprüfen, welcher Button gedrückt wurde
            for (int row = 5; row >= 0; row--) {
                for (int col = 0; col < 7; col++) {
                    if (e.getSource() == buttons[row][col]) {
                        if (board[row][col] == 0) {
                            int player = player1Turn ? 1 : 2;
                            buttons[row][col].setBackground(player == 1 ? Color.RED : Color.YELLOW);
                            board[row][col] = player;
                            if (checkWin(row, col)) {
                                gameOver = true;
                                messageLabel.setText("Spieler " + player + " hat gewonnen!");
                                getContentPane().remove(gamePanel);
                                getContentPane().add(endPanel);
                                validate();
                            } else if (checkTie()) {
                                gameOver = true;
                                messageLabel.setText("Unentschieden!");
                                getContentPane().remove(gamePanel);
                                getContentPane().add(endPanel);
                                validate();
                            } else {
                                player1Turn = !player1Turn;    // Wechsel zum anderen Spieler
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public void reset() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0;
            }
        }
        player1Turn = true;     // Spieler 1 beginnt
        gameOver = false;       // Spielstart, kein Spielende
        repaint();
    }
    
    public boolean checkTie() {
        // Überprüfung, ob das Spiel unentschieden ist
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 0) {
                    return false;    // Es gibt noch leere Felder, das Spiel ist nicht unentschieden
                }
            }
        }
        return true;    // Alle Felder sind belegt, das Spiel endet unentschieden
    }
    
    public boolean checkWin(int row, int col) {
        int player = board[row][col];
        int count = 0;

        // Überprüfung auf horizontalen Gewinn
        for (int i = col - 3; i <= col + 3; i++) {
            if (i >= 0 && i < 7 && board[row][i] == player) {
                count++;
                if (count == 4) {
                    return true;    // Vier aufeinanderfolgende Spielsteine gefunden, Gewinn
                }
            } else {
                count = 0;    // Unterbrechung der Kette, zurücksetzen des Zählers
            }
        }

        count = 0;
        
        // Überprüfung auf vertikalen Gewinn
        for (int i = row - 3; i <= row + 3; i++) {
            if (i >= 0 && i < 6 && board[i][col] == player) {
                count++;
                if (count == 4) {
                    return true;    // Vier aufeinanderfolgende Spielsteine gefunden, Gewinn
                }
            } else {
                count = 0;    // Unterbrechung der Kette, zurücksetzen des Zählers
            }
        }

        count = 0;
        
        // Überprüfung auf diagonalen Gewinn (aufsteigend)
        for (int i = -3; i <= 3; i++) {
            int r = row + i;
            int c = col + i;
            if (r >= 0 && r < 6 && c >= 0 && c < 7 && board[r][c] == player) {
                count++;
                if (count == 4) {
                    return true;    // Vier aufeinanderfolgende Spielsteine gefunden, Gewinn
                }
            } else {
                count = 0;    // Unterbrechung der Kette, zurücksetzen des Zählers
            }
        }

        count = 0;

        // Überprüfung auf diagonalen Gewinn (absteigend)
        for (int i = -3; i <= 3; i++) {
            int r = row - i;
            int c = col + i;
            if (r >= 0 && r < 6 && c >= 0 && c < 7 && board[r][c] == player) {
                count++;
                if (count == 4) {
                    return true;    // Vier aufeinanderfolgende Spielsteine gefunden, Gewinn
                }
            } else {
                count = 0;    // Unterbrechung der Kette, zurücksetzen des Zählers
            }
        }

        return false;    // Kein Gewinn
    }
    
    // Funktion zum Zurücksetzen der Variablen
    public void variablenreset() {
        buttons = null;
        gamePanel = null;
        startPanel = null;
        endPanel = null;
        messageLabel = null;
        startButton = null; 
        resetButton = null;
        board = null;
        player1Turn = false;
        gameOver = false;
    }
}
