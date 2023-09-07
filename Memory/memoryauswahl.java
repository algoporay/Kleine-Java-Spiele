// Geschrieben von Thomas Erny
package ProjektSpiele.Memory;

import java.awt.Color;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ProjektSpiele.Startseite;

// Deklaration der Klasse memoryauswahl
public class memoryauswahl 
{
     // JPanel mit GridBagLayout erstellen
    public static JPanel 
    memoryauswahl = new JPanel(new GridBagLayout());               

    // Methode zum Hinzufügen eines ActionListeners zu einem Button
    private static void addActionListener(Button button, int schwirigkeit, JFrame programmfenster) 
    {
        button.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent schwirigkeitauswahl) 
            {
                // Aufrufen der Methode resetMemory() und memory() aus der Klasse Memory
                Memory.resetMemory();
                Memory.memory(programmfenster, schwirigkeit);
                // Entfernen des memoryauswahl-Panels aus dem programmfenster
                programmfenster.remove(memoryauswahl);
            }
        });
    }

    // Methode zur Erstellung des Memory-Auswahlfensters
    public static void memoryauswahlfenster(JFrame programmfenster) 
    {
        // Anzahl der Buttons
        int 
        anzahlbuttons = 4;                                              

        // Array mit Buttons
        Button[] 
        buttons = new Button[anzahlbuttons];                            

        // Array mit Bezeichnungen für die Buttons
        String[] 
        benennung = {"Leicht", "Mittel", "Schwer", "Rangliste"};   

        // Objekt zur Steuerung der Layouts
        GridBagConstraints 
        constraints = new GridBagConstraints();                         

        // Festlegen der Größe und Farbe des memoryauswahl-Panels
        memoryauswahl.setBounds(0, 0, Startseite.bildschirmbreite, Startseite.bildschirmhöehe);
        memoryauswahl.setBackground(new Color(200, 240, 220));

        // Erstellung der Buttons und Hinzufügen zum memoryauswahl-Panel
        for(int i=0;i<anzahlbuttons;i++) 
        {
            buttons[i] = new Button(benennung[i]);
            constraints.gridy = i;
            constraints.ipadx = Startseite.buttonbreite;
            constraints.ipady = Startseite.buttonhöhe;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(0,0,30,0);
            buttons[i].setBackground(new Color(200, 170, 100).brighter().brighter());
            memoryauswahl.add(buttons[i],constraints);
        }

        // Hinzufügen eines ActionListeners zu jedem Button
        addActionListener(buttons[0], 10, programmfenster);
        addActionListener(buttons[1], 15, programmfenster);
        addActionListener(buttons[2], 25, programmfenster);

        // Hinzufügen eines ActionListeners zu Button 4
        buttons[3].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent viergewinnt) 
            {
                programmfenster.remove(memoryauswahl);
                Memory.resetMemory();
                endblidschirm.auflistung(programmfenster);
            }
        });

        // Setzen des memoryauswahl-Panels als Inhalt des programmfensters
        programmfenster.setContentPane(memoryauswahl);
    }

    //Resetmethode um sicherzustellen, dass das JPanel zurückgesetzt wird.
    public static void memoryauswahlreset() 
    {
        memoryauswahl = new JPanel(new GridBagLayout()); 
    }
}