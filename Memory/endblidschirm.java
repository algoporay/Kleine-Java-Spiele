// Geschrieben von Thomas Erny
package ProjektSpiele.Memory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ProjektSpiele.Startseite;

public class endblidschirm 
{
    private static String dateiname, inhaltname;

    // Methode zur Anzeige des Endbildschirms für das Memory-Spiel
    public static void memory(JFrame programmfenster, int punkte, String zeit) 
    {
        Dimension 
        buttongrösse = null;

        int 
        buttonwidth = (int) (Startseite.bildschirmbreite * .075),
        buttonheight = 20;
        JButton 
        bestätigen = new JButton("Bestätigen"),
        rangliste = new JButton("Rangliste");

        JTextField 
        name = new JTextField();

        GridBagConstraints 
        gbc = new GridBagConstraints();

        JPanel 
        endbildschirm = new JPanel(new GridBagLayout()),
        buttonpanel = new JPanel(new FlowLayout());

        JLabel 
        Gratulation = new JLabel("Congratulations!!!"),
        punktelabel = new JLabel("Ihre Punktzahl: "),
        zeitLabel,
        eingabe = new JLabel("Bitte geben Sie Ihren Namen an");

        // Einstellungen für das Endbildschirm-Panel
        endbildschirm.setBounds(0, 0, Startseite.bildschirmbreite, Startseite.bildschirmhöehe);
        endbildschirm.setBackground(Startseite.farbefenster);

        // Einstellungen für die "Congratulations!!!"-Beschriftung
        Gratulation.setForeground(Color.black);
        Gratulation.setFont(new Font("MV Boli", Font.PLAIN, 60));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        endbildschirm.add(Gratulation, gbc);

        // Einstellungen für die Punktzahl-Beschriftung
        punktelabel.setForeground(Color.black);
        punktelabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        gbc.gridy = 1;
        endbildschirm.add(punktelabel, gbc);

        // Einstellungen für die Zeit-Beschriftung
        zeitLabel = new JLabel("Ihre Zeit: " + zeit);
        zeitLabel.setForeground(Color.black);
        zeitLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        gbc.gridy = 2;
        endbildschirm.add(zeitLabel, gbc);

        // Einstellungen für die Eingabeaufforderung für den Namen
        eingabe.setForeground(Color.black);
        eingabe.setFont(new Font("MV Boli", Font.PLAIN, 20));
        gbc.gridy = 3;
        endbildschirm.add(eingabe, gbc);

        // Einstellungen für das Textfeld zur Namen-Eingabe
        name.setHorizontalAlignment(JTextField.CENTER);
        name.setFont(new Font("MV Boli", Font.PLAIN, 20));
        name.setBackground(Startseite.farbebutton);
        gbc.gridy = 4;
        gbc.ipadx = (int) (Startseite.bildschirmbreite * .15);
        endbildschirm.add(name, gbc);

        // Einstellungen für das Button-Panel
        gbc.gridy = 5;
        buttonpanel.setBackground(Startseite.farbefenster);
        endbildschirm.add(buttonpanel, gbc);

        // Einstellungen für den "Bestätigen"-Button
        buttongrösse = new Dimension(buttonwidth, buttonheight);
        bestätigen.setPreferredSize(buttongrösse);
        buttonpanel.add(bestätigen);

        // Einstellungen für den "Rangliste"-Button
        rangliste.setPreferredSize(buttongrösse);
        buttonpanel.add(rangliste);

        // ActionListener für den "Bestätigen"-Button
        bestätigen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent zurück) 
            {
                inhaltname = name.getText();
                if(inhaltname != null && !inhaltname.isEmpty() && inhaltname.matches("[a-zA-Z0-9 ]*"))
                {
                    dateiname = "ProjektSpiele/Memory/rangliste.txt";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateiname, true))) 
                    {
                        writer.write(inhaltname + "," + punkte + ", " + zeit);
                        writer.newLine();
                    } 
                    catch (IOException speicherfehler) 
                    {
                        JOptionPane.showMessageDialog(null, "Fehler beim Speichern: " + speicherfehler.getMessage());
                    }
    
                    programmfenster.remove(endbildschirm);
                    auflistung(programmfenster);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Bitte nur Buchstaben und Zahlen verwenden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener für den "Rangliste"-Button
        rangliste.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent zurück) 
            {
                auflistung(programmfenster);
            }
        });

        programmfenster.setContentPane(endbildschirm);
        programmfenster.repaint();
        programmfenster.revalidate();
    }


    // Methode zur Anzeige der Rangliste
    public static void auflistung(JFrame programmfenster) 
    {
        int 
        punkte;
        JPanel 
        rangliste = new JPanel(new GridBagLayout());
        rangliste.setBounds(0, 0, Startseite.bildschirmbreite, Startseite.bildschirmhöehe);
        rangliste.setBackground(Startseite.farbefenster);

        GridBagConstraints 
        constraintsrangliste = new GridBagConstraints();
        constraintsrangliste.anchor = GridBagConstraints.CENTER;

        JLabel rangLabel = new JLabel("Rangliste");
        rangLabel.setForeground(Color.black);
        rangLabel.setFont(new Font("MV Boli", Font.PLAIN, 60));
        constraintsrangliste.gridy = 0;
        rangliste.add(rangLabel, constraintsrangliste);

        // Liste zur Speicherung der Ranglistendaten
        ArrayList<String[]> 
        einträge = new ArrayList<>();

        String 
        zeit, 
        name;

        String[] 
        werte, 
        spaltennamen = { "Name", "Punktzahl", "Zeit" };

        // Erstellen des Tabellenmodells für die Rangliste
        DefaultTableModel 
        model = new DefaultTableModel(einträge.toArray(new String[0][0]), spaltennamen);

        // Erstellen der Tabelle für die Rangliste
        JTable 
        tabelle = new JTable(model);
        tabelle.setFont(new Font("MV Boli", Font.PLAIN, 20));
        tabelle.setBackground(Startseite.farbebutton);
        tabelle.getTableHeader().setBackground(Startseite.farbebutton);

        // Erstellen des Scroll-Panels für die Tabelle
        JScrollPane 
        scrollPane = new JScrollPane(tabelle);
        scrollPane.setBackground(new Color(200, 170, 100).brighter().brighter());
        scrollPane.setPreferredSize(null);
        constraintsrangliste.gridy = 1;
        rangliste.add(scrollPane, constraintsrangliste);

        // Datei für die Rangliste öffnen und Daten einlesen
        File 
        ranglistendatei = new File("ProjektSpiele/Memory/rangliste.txt");

        // Erstellen des "Zurück"-Buttons
        JButton 
        zurück = new JButton("Zurück");
        constraintsrangliste.gridy = 2;
        zurück.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent zurück) 
            {
                programmfenster.remove(rangliste);
                Startseite.startseite(programmfenster);
            }
        });
        rangliste.add(zurück, constraintsrangliste);

        try (BufferedReader br = new BufferedReader(new FileReader(ranglistendatei))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                werte = line.split(",");
                name = werte[0].trim();
                punkte = Integer.parseInt(werte[1].trim());
                zeit = werte[2].trim();

                String[] reihe = { name, Integer.toString(punkte), zeit };
                einträge.add(reihe);
            }

            // Sortieren der Ranglistendaten nach Punktzahl und Zeit
            Collections.sort(einträge, new Comparator<String[]>() 
            {
                @Override
                public int compare(String[] wert1, String[] wert2) 
                {
                    int punkte1 = Integer.parseInt(wert1[1]);
                    int punkte2 = Integer.parseInt(wert2[1]);
                    if (punkte1 != punkte2) 
                    {
                        return punkte2 - punkte1;
                    } 
                    else 
                    {
                        String zeit1 = wert1[2];
                        String zeit2 = wert2[2];
                        return zeit1.compareTo(zeit2);
                    }
                }
            });

            // Hinzufügen der sortierten Ranglistendaten zur Tabelle
            for (int i = 0; i < einträge.size(); i++) 
            {
                String[] row = einträge.get(i);
                row = new String[] { row[0], row[1], row[2] };
                model.addRow(row);
            }
        } 
        catch (FileNotFoundException e) 
        {
            JOptionPane.showMessageDialog(programmfenster,"Die Datei wurde nicht gefunden: " + ranglistendatei.getAbsolutePath());
        } 
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(programmfenster,"Fehler beim Lesen der Ranglisteist aufgetreten: " + ranglistendatei.getAbsolutePath());
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        // Anzeigen der Rangliste im Programmfenster
        programmfenster.setContentPane(rangliste);
        programmfenster.repaint();
        programmfenster.revalidate();
    }
}
