// Geschrieben von Thomas Erny
package ProjektSpiele.Memory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ProjektSpiele.Startseite;

// Klassendefinition für die Memory-Klasse
public class Memory
{
    // Deklaration von Klassenvariablen
    
    private static int
    ausgewählteKarten = 0,
    punkte = 0;

    private static long
    vergangenezeit = 0,
    startzeit =0,
    stunden = 0,
    minuten = 0,
    sekunden = 0;

    // Das Hauptpanel
    private static JPanel
    main = null;  

    private static String
    abschlusszeit;

    private static JButton
    // Der aktuelle Button, auf den geklickt wurde
    button = null,                                  
    auswahl1 = null,
    auswahl2 = null;

    private static JLabel 
    punktestand = null,     
    zeit = null;             
       

    private static ImageIcon 
    icon = null;

    private static Object 
    idauswahl1 = null,
    idauswahl2 = null;

    private static boolean 
    timerläuft = false;



// Die memory-Methode startet ein neues Memory-Spiel
public static void memory(JFrame programmfenster, int schwierigkeit) 
{

    // Deklaration der Funktionsvariablen

    final int
    // Horizontaler Abstand zwischen den Karten
    horizonzalinsets = 2, 
    // Vertikaler Abstand zwischen den Karten                          
    vertigcalinset= 2;                              

    int
    reihen = 0,
    kolumnen = 0,
    memorypanelhöhe = 0,
    kartenpanelhöhe = 0,
    kartenpanelbreite = 250;

    Timer countdown = null;

    // Liste der Karten-IDs
    ArrayList<Integer> 
    ids = new ArrayList<Integer>();                 

    GridBagConstraints 
    constraints = new GridBagConstraints();

    JPanel 
    memory = new JPanel(new FlowLayout()),
    head = new JPanel(new GridBagLayout()),        
    memorygrid = new JPanel();                      

    // Panel Array, welches dem head-Panel hinzugefügt wird
    JPanel[] 
    layouthead = new JPanel[5];

    JButton 
    zurück = new JButton("Zurück");            

    JLabel 
    header = new JLabel();                         

    // Je nach Schwierigkeitsgrad werden die Anzahl der Reihen und Spalten des Memory-Spiels festgelegt
    switch(schwierigkeit)
    {
        case 5:
        reihen = 2;
        kolumnen = 5;
        break;
        case 10:
        reihen = 4;
        kolumnen = 5;
        break;
        case 15:
        reihen = 5;
        kolumnen = 6;
        break;
        case 25:
        reihen = 5;
        kolumnen = 10;
        break;
    }

    // Hauptpanel Layout wird festgelegt
    main = new JPanel(new GridBagLayout());  
    main.setBackground(Startseite.farbefenster);

    // Das Head-Panel wird zum Hauptpanel hinzugefügt
    head.setBackground(Startseite.farbefenster);
    constraints.gridy = 0;
    constraints.weightx = 1.0;
    constraints.weighty = 0.02;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.NORTH;
    main.add(head, constraints);

    // Das Memory-Panel wird zum Hauptpanel hinzugefügt
    memory.setBackground(Startseite.farbefenster);
    memory.setLayout(new FlowLayout(FlowLayout.CENTER));
    constraints.gridy = 1;
    constraints.weightx = 1.0;
    constraints.weighty = 0.98;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.NORTH;
    main.add(memory, constraints);
    programmfenster.setContentPane(main);
    programmfenster.revalidate();
    programmfenster.repaint();

    // Die Höhe des Memory-Panels und des Panels, das die Karten enthält, werden berechnet
    memorypanelhöhe = memory.getHeight();
    kartenpanelhöhe =(int) (memorypanelhöhe*.9);
    kartenpanelbreite+=kartenpanelhöhe;
    
    // Das GridLayout für die Karten wird initialisiert und zum Memory-Panel hinzugefügt
    memorygrid.setLayout(new GridLayout(reihen,kolumnen));
    memorygrid.setPreferredSize(new Dimension(kartenpanelbreite,kartenpanelhöhe));
    memorygrid.setBackground(Color.white);
    ((GridLayout)memorygrid.getLayout()).setHgap(horizonzalinsets);
    ((GridLayout)memorygrid.getLayout()).setVgap(vertigcalinset); 
    memorygrid.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
    constraints.anchor = GridBagConstraints.SOUTH;
    memory.add(memorygrid,constraints);

    // Ein neues JPanel-Array wird erstellt, um den Header zu layouten
    for(int i=0;i<5;i++)
    {
            layouthead[i] = new JPanel(new GridBagLayout());
            constraints.gridx = i;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;
            layouthead[i].setBackground(Startseite.farbefenster);
            head.add(layouthead[i],constraints);
    }

    // Der Zurück-Button wird zum Header hinzugefügt und ein ActionListener wird registriert
    constraints.anchor = GridBagConstraints.CENTER;
    constraints.fill = GridBagConstraints.CENTER;
    layouthead[0].add(zurück,constraints);
    zurück.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent zurückgeclickt)
        {
            programmfenster.remove(main);
            Startseite.startseite(programmfenster);
        }
    });

    // Die Überschrift "Memory" wird zum Header hinzugefügt
    header.setVerticalAlignment(JLabel.CENTER);
    header.setHorizontalAlignment(JLabel.CENTER);
    header.setText("Memory");
    header.setForeground(Color.black);
    header.setFont(new Font("MV Boli",Font.PLAIN,40));
    layouthead[2].add(header);

    // Der Timer-Label wird zum Header hinzugefügt und ein Timer wird initialisiert
    startzeit = System.currentTimeMillis();
    countdown = new Timer(1000, e -> 
    {
        vergangenezeit = System.currentTimeMillis() - startzeit;
        stunden = vergangenezeit / (60 * 60 * 1000) % 24;
        minuten = vergangenezeit / (60 * 1000) % 60;
        sekunden = vergangenezeit / 1000 % 60;
        zeit.setText(String.format("%02d:%02d:%02d", stunden, minuten, sekunden));
    });

    zeit = new JLabel("00:00:00");
    zeit.setVerticalAlignment(JLabel.CENTER);
    zeit.setHorizontalAlignment(JLabel.CENTER);
    zeit.setForeground(Color.black);
    zeit.setFont(new Font("MV Boli",Font.PLAIN,20));
    countdown.start();
    constraints.gridx = 0;
    constraints.gridy = 1;
    layouthead[4].add(zeit,constraints);

    // Der Punktestand-Label wird zum Header hinzugefügt
    punktestand = new JLabel("Punktestand "+ punkte);
    punktestand.setVerticalAlignment(JLabel.CENTER);
    punktestand.setHorizontalAlignment(JLabel.CENTER);
    punktestand.setForeground(Color.black);
    punktestand.setFont(new Font("MV Boli",Font.PLAIN,20));
    constraints.gridx = 0;
    constraints.gridy = 0;
    layouthead[4].add(punktestand,constraints);
    
    // Eine ArrayList von Integer wird erstellt, die die IDs der Karten enthält
    for(int i = 1; i<=schwierigkeit; i++)
    {
        ids.add(i);
        ids.add(i);
    }
    Collections.shuffle(ids);

    // Ein ActionListener für die Karten wird erstellt
    ActionListener buttonListener = new ActionListener() 
    {
        public void actionPerformed(ActionEvent Memorygespielt) 
        {
            button = (JButton) Memorygespielt.getSource();
            // Wenn bereits zwei Karten ausgewählt wurden, wird nichts weiter gemacht
            if (button == auswahl1 || ausgewählteKarten == 3) 
            {
                return;
            }
    
            // Die gedrückte Karte wird ausgewählt und deren Icon wird aufgedeckt

            icon = new ImageIcon("ProjektSpiele/BilderMemory/bild ("+button.getClientProperty("id")+").jpg");

            if (idauswahl1 == null) 
            {
                ausgewählteKarten++;
                auswahl1 = button;
                idauswahl1 = button.getClientProperty("id");
                bildaufdecken(auswahl1, icon);
            } 

            else if (idauswahl2 == null) 
            {
                // Die zweite Karte wird ausgewählt und deren Icon wird aufgedeckt
                ausgewählteKarten++;
                auswahl2 = button;
                idauswahl2 = button.getClientProperty("id");
                bildaufdecken(auswahl2, icon);
                
                // Wenn ein Timer bereits läuft, wird er abgebrochen
                if(timerläuft)
                {
                    Thread.currentThread().interrupt();
                }

                // Ein neuer Thread wird gestartet, um den Timer zu implementieren
                new Thread(() -> 
                {
                    try 
                    {
                        timerläuft = true;
                        Thread.sleep(1000);
                    } 
                    catch (InterruptedException z) 
                    {

                    }
                    // Wenn der Timer abgelaufen ist, werden die ausgewählten Karten überprüft und das Spiel wird fortgesetzt
                    SwingUtilities.invokeLater(() -> 
                    {
                        timerläuft = false;
                        kartenabgleich(auswahl1, auswahl2, idauswahl1, idauswahl2, schwierigkeit, programmfenster);
                        ausgewählteKarten = 0;
                        idauswahl1 = null;
                        idauswahl2 = null;
                    });
                }).start();
            }
        }
    };
        // Für jede Karte wird ein JButton erstellt und zur Memorygrid hinzugefügt
        for (int i = 0; i < schwierigkeit * 2; i++) 
        {
            int id = ids.get(i);
            JButton button = new JButton();
            button.setBackground(Startseite.farbebutton);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.putClientProperty("id", id);
            memorygrid.add(button);
            button.addActionListener(buttonListener);
        }
        programmfenster.revalidate();
        programmfenster.repaint();
    }
    
    // Diese Methode deckt das Icon einer Karte auf
    public static void bildaufdecken(JButton button, ImageIcon icon)
    {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        button.setIcon(newIcon);
    }
    
    // Diese Methode vergleicht die IDs von zwei ausgewählten Karten
    public static void kartenabgleich(JButton auswahl1, JButton auswahl2, Object idauswahl1, Object idauswahl2,
    int schwierigkeit, JFrame programmfenster)
    {
        // Wenn die IDs gleich sind und die ausgewählten Karten nicht identisch sind, bleiben die Karten aufgedeckt und der Punktestand wird erhöht
        if(idauswahl1.equals(idauswahl2) && auswahl1 != auswahl2)
        {
            auswahl1.setEnabled(false);
            auswahl2.setEnabled(false);
            punkte++;
            punktestand.setText("Punktestand "+ punkte);
            if(punkte == schwierigkeit)
            {
                abschlusszeit = zeit.getText();
                programmfenster.remove(main);
                endblidschirm.memory(programmfenster, punkte, abschlusszeit);
            }
        }
        // Wenn die IDs nicht gleich sind, werden die Karten umgedreht
        else
        {
            auswahl1.setIcon(null);
            auswahl2.setIcon(null);
        }
    
    }

    // Die Methode resetMemory setzt alle Variablen zurück
    public static void resetMemory() 
    {
        ausgewählteKarten = 0;
        punkte = 0;
        vergangenezeit = 0;
        startzeit = 0;
        idauswahl1 = null;
        idauswahl2 = null;
        icon = null;
        timerläuft = false;
        button = null;
        auswahl1 = null;
        auswahl2 = null;
        punktestand = null;
        zeit = null;
        main = null;
    }
}
    

