/* Notiz an Herr Lindemann, zu ändernde pfade im code für Bilder sind in den Dateien: 

Startseite:		Zeile 45 

Snake :		    Zeilen 82,85 und 88 

Memory :		Zeile 265 */


// Geschrieben von Thomas Erny
package ProjektSpiele;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ProjektSpiele.Memory.memoryauswahl;

// Definieren der Klasse "Startseite"
public class Startseite
{
    // Öffentliche Variablen für Farben erstellen
    public static final Color
    farbefenster = new Color(200, 240, 220),
    farbebutton = new Color(200, 170, 100).brighter().brighter();

    // Öffentliche Dimension der Fenstergrösse generieren
    public static final Dimension bildschirmgroesse = Toolkit.getDefaultToolkit().getScreenSize();
    // Öffentliche Variablen für die Fenstergrösse generieren 
    public static final int 
    bildschirmbreite = (int) bildschirmgroesse.getWidth(),
    bildschirmhöehe = (int) bildschirmgroesse.getHeight(),
    // Öffentliche Variablen für die Button-Elemente generieren
    buttonbreite = (int) (bildschirmbreite*.15),
    buttonhöhe = (int) (bildschirmhöehe*.025);

    public static JFrame programmfenster = new JFrame("Spiele by Natascha, Arianit and Thomas");

    // Hauptmethode des Programms
    public static void main(String[] args)
    {

        // Erstelle das Hauptfenster 
        // Lade ein Bild zur Verwendung als Fenster-Symbol
        ImageIcon image = new ImageIcon("ProjektSpiele/BilderMemory/benedict.png");
        // Setze das Fenster auf Vollbild-Modus
        programmfenster.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Mindestgrösse des Fensters festlegen
        programmfenster.setMinimumSize(new Dimension(bildschirmbreite, bildschirmhöehe));
        // Beende das Programm, wenn das Fenster geschlossen wird               
        programmfenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setze die Hintergrundfarbe des Fensters                     
        programmfenster.getContentPane().setBackground(farbefenster);
        // Setze das Fenster-Symbol                       
        programmfenster.setIconImage(image.getImage()); 
        // Setze das Layout des Fensters auf "null"                                    
        programmfenster.setLayout(null);  
        // Stelle sicher, dass die fenstergrösse nicht manuell verändert werden kann                                          
        programmfenster.setResizable(false);
        // Mache das Fenster sichtbar
        programmfenster.setVisible(true);                                                 
        // Rufe die Methode auf, die die Startseite erstellt
        Startseite.startseite(programmfenster);                                             
    }

    // Methode zur Erstellung der Startseite
    public static void startseite(JFrame programmfenster) 
    { 
        programmfenster.setVisible(true);
        // Anzahl der Buttons auf der Startseite
        int 
        anzahlbuttons = 4;                                                                  
    
        // Array für die Buttons auf der Startseite
        Button[] 
        buttons = new Button[anzahlbuttons];                                                
    
        // Array für die Benennung der Buttons
        String[] 
        benennung = {"Memory", "Snake","4Gewinnt","Beenden"};                 
    
        // Constraints für das GridBagLayout
        GridBagConstraints 
        constraints = new GridBagConstraints();                                             
    
        // Panel für die Startseite
        JPanel startpanel = new JPanel(new GridBagLayout());  
        // Setze die Größe und Position des Startseiten-Panels                              
        startpanel.setBounds(0, 0, bildschirmbreite, bildschirmhöehe);
        // Setze die Hintergrundfarbe des Startseiten-Panels                 
        startpanel.setBackground(farbefenster);                                             

        // Erstelle die Buttons für die Startseite
        for(int i=0;i<anzahlbuttons;i++)
        {
            buttons[i] = new Button(benennung[i]);
            constraints.gridy = i;
            constraints.ipadx = buttonbreite;
            constraints.ipady = buttonhöhe;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(0,0,30,0);
            buttons[i].setBackground(farbebutton);
            // Füge die Buttons zum Startseiten-Panel hinzu
            startpanel.add(buttons[i],constraints);
        }

        // Füge ActionListener zu Button 0 (Memory) hinzu
        buttons[0].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent memory) 
            {
                memoryauswahl.memoryauswahlreset();
                memoryauswahl.memoryauswahlfenster(programmfenster);
            }
        });

        // Füge ActionListener zu Button 1 (Snake) hinzu
        buttons[1].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent snake) 
            {
                Snake.Snakegame(programmfenster);
            }
        });
        
        // Füge ActionListener zu Button 2 (4Gewinnt) hinzu
        buttons[2].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent viergewinnt) 
            {
                VierGewinnt.FunktionVierGewinnt();
            }
        });
        
        // Füge ActionListener zu Button 3 (Beenden) hinzu
        buttons[3].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent beenden) 
            {
                System.exit(0);
            }
        });
        
        // Setze das Startseiten-Panel als Inhalt des Hauptfensters
        programmfenster.setContentPane(startpanel);
    }

    // Stellt das Programmfenster für andere Funrkionen bereit
    public static JFrame getProgrammfenster() 
    {
        return programmfenster;
    }
}
