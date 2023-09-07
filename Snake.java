// Geschrieben von Natascha Spiess
package ProjektSpiele;
// Importieren der benötigten Klassen und Pakete
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener {
/*  Alle Konstanten die im Spiel verwendet werden:
    Die Grösse vom Punkt (Schlange und Apfel):                                                                                                */
    private static final int DOT_SIZE = 20;
//  Die Breite und Länge vom Spielfeld                                                                                                        
    private static final int B_WIDTH = DOT_SIZE * 20;
    private static final int B_HEIGHT = DOT_SIZE * 20;
//  Die Maximale Anzahl der möglichen Punkte auf dem Spielfeld wird definiert, damit man eine Zufällige Position für den Apfel berrechnet.    
    private final int ALL_DOTS = 20 * 20;
    private final int RAND_POS = 19;
//  Die Geschwindigkeit des Spiels wird bestummen. Je kleiner desto schneller bewegt sich die Schlange.                                       
    private final int DELAY = 140;
//  Diese Arrays speichern die x- und y-Koordinaten aller Gelenke der Schlange.                                                               
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
//  Die Variablen für die Punkte, Apfel und Punktestand werden Initialisiert.                                                                    
    private int dots;
    private int apple_x;
    private int apple_y;
    private int score;
//  Variabeln werden initialisiert.
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean buttonsAdded = false;
//  instanzvariablen werden Initialisiert.
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    private static JPanel snakehintergrund = new JPanel(new GridBagLayout());
    private static GridBagConstraints gbc = new GridBagConstraints();
    private JButton tryAgainButton;
    private JButton exitButton;

    public static void Snakegame(JFrame programmfenster) {
        Snake snake = new Snake();
        snake.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        snake.setBackground(Color.BLACK);
//      hintergrund panel für programmfenster bereitstellen, hinzufügen des snake spiels in das panel
        gbc.anchor = GridBagConstraints.CENTER;
        snakehintergrund.setBackground(Color.DARK_GRAY.darker().darker());
        snakehintergrund.add(snake, gbc);
//      hintergrundpanel des snake spiels zum programmfenster hinzufügen
        programmfenster.setBackground(Color.black);
        programmfenster.setContentPane(snakehintergrund);
        programmfenster.revalidate();
        programmfenster.repaint();
//      restliche funktionien aufrufen
        snake.loadImages();
        snake.initGame();
        TAdapter tAdapter = snake.new TAdapter();
        snake.addKeyListener(tAdapter);
    }
//  In dieser Methode erhalte ich PNG-Bilder für das Spiel.                                                                                   
    private void loadImages() {
        ImageIcon iid = new ImageIcon("ProjektSpiele/BilderSnake/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("ProjektSpiele/BilderSnake/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("ProjektSpiele/BilderSnake/head.png");
        head = iih.getImage();
    }
//  Die Schlange wird erstellt, zufällig wird ein Apfel positioniert, und startet den Timer.initGame()
    private void initGame() {
        dots = 3;
        score = 0;
        inGame = true;

        for (int z = 0; z < dots; z++) {
            x[z] = DOT_SIZE * 10 - z * DOT_SIZE;
            y[z] = DOT_SIZE * 10;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
        requestFocusInWindow();                                                       //Das Spiel reagierte nicht mit der Taste (dies korrigiert es)

        buttonsAdded = false;                                                         // Zurücksetzen der Flag-Variable
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

//      Buttons werden nur hinzugefügen, wenn sie noch nicht hinzugefügt wurden
        if (!buttonsAdded && !inGame) {
            addButtons();
            buttonsAdded = true;
        }
    }
/*  Die Spielobjekte (Apfel, Kopf und Körper der Schlange) auf dem Spielfeld, 
    synchronisiert die Darstellung und ruft bei Spielende den Game Over Bildschirm sowie die Darstellung des Punktestands auf.                */
    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
        drawScore(g);
    }
//  Grafik & Position für Score wird erstellt.
    private void drawScore(Graphics g) {
        String scoreText = "Score: " + score;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString(scoreText, 10, 20);
    }
//  Grafik & Position für GameOver wird erstellt und aufgerufen wenn Spiel Vorbei ist. Danach auch Für die Buttons Probiers Nochmals und Spiel Beenden
    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics metr = getFontMetrics(small);
    
        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        drawScore(g);
    
        int buttonWidth = 120;
        int buttonHeight = 40;
    
        int tryAgainX = (B_WIDTH - buttonWidth) / 2;
        int tryAgainY = B_HEIGHT / 2 + 50;
    
        int exitGameX = (B_WIDTH - buttonWidth) / 2;
        int exitGameY = tryAgainY + buttonHeight + 10;
//      Die Versuchs nochmals Button wird aufgerufen und positioniert   
        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.setBounds(tryAgainX, tryAgainY, buttonWidth, buttonHeight);
        tryAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        add(tryAgainButton);
//      Die Spiel Beenden Button wird aufgerufen und positioniert
        JButton exitGameButton = new JButton("Exit Game");
        exitGameButton.setBounds(exitGameX, exitGameY, buttonWidth, buttonHeight);
        exitGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
        add(exitGameButton);
    }
    
/*  Wenn der Apfel mit dem Kopf kollidiert, wird die Anzahl der Gelenke der Schlange erhöht.                
    Die Methode die ein neues Apfelobjekt zufällig positioniert wird erwähnt                                                                   */
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score++;
            locateApple();
        }
    }
/*  In der Methode ist der Schlüsselalgorithmus des Spiels. 
    Der Kopf der Schlange wird Kontrolliert und der rest kommt mit, 
    indem der erste eine richtung schlägt der zweite gelenk geht dort wo der erste war der dritte geht dort wo der zweiter war usw.            */
    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
//      Hier geht der Kopf zb nach links usw mit den unteren.
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
//  Hier wird bestimmt ob die Schlange sich selbst oder die Wände getroffen hat. Wenn dies Passiert ist das Spiel vorbei.
    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }
//  Die Apfel werden Zufällig Positioniert.
    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
/*  Hier wird überprüft ob der Apfel gegessen wurde, die Schlange sich selbst oder wand getroffen hat, sich bewegt,
    und aktualisiert somit gleich den Zustand. Bei repaint wird das spiel visuel aktualisiert.                                                */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }
//  Hier wird die Tastatureingaben entsprechend dem Spiel angepasst (zb wenn Pfeiltaste nach oben Geklickt wird, geht der Schlangenkopf nach oben)
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
//  Die buttons Probiers nochmals und Spiel Beenden wird zur actionlistener hinzugefügt und bestimmt gleich was die macht.
    private void addButtons() {
        tryAgainButton = new JButton("Try Again");
        tryAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        snakehintergrund.add(tryAgainButton, gbc);
        snakehintergrund.add(exitButton, gbc);
    }
//  Hier wird das spiel neugestarten und alles zurückgesetzt.
    private void restartGame() {
        varreset();
        JFrame programmfenster = Startseite.getProgrammfenster();
        Snakegame(programmfenster);

    }
//  Hier kehrt man zurück zur Startseite
    private void exitGame() {
        varreset();
        JFrame programmfenster = Startseite.getProgrammfenster();
        Startseite.startseite(programmfenster);
    }
    private void varreset()
    {
        dots = 0;
        apple_x = 0;
        apple_y = 0;
        score = 0;

        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
        inGame = true;
        buttonsAdded = false; // Flag-Variable für die Buttons

        timer = null;
        ball = null;
        apple = null;
        head = null;

        snakehintergrund.removeAll();
        tryAgainButton  = null;
        exitButton  = null;
    }
}

