/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.software;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Usuari
 */
public class App {

    private JFrame f;
    JPanel panell;
    private JMenuBar topBar;
    private JMenu m1;
    private JMenu m2;
    private JMenuItem m1Op1;
    private JMenuItem m1Op2;
    private JMenuItem m2Op1;
    private JMenuItem m2Op2;

    public App() {
        f = new JFrame("App Java");
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] noms = {"Sí", "No"};
                int opc
                        = JOptionPane.showOptionDialog(f, "Vols Sortir?", "Atenció!",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, noms, noms[0]);

                if (opc == JOptionPane.YES_OPTION) {
                    f.dispose();
                }
            }
        });
        f.setSize(new Dimension(800, 600));
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        carregarEntornGrafic();
        f.setVisible(true);
    }

    private void carregarEntornGrafic() {
        carregarMenu();
        carregarPaginaPrincipal();
    }

    private void carregarMenu() {
        topBar = new JMenuBar();
        m1 = new JMenu("Gestio");
        m2 = new JMenu("Ajuda");
        m1Op1 = new JMenuItem("Portal Web");
        m1Op2 = new JMenuItem("Usuaris");
        m2Op1 = new JMenuItem("Web");
        m2Op2 = new JMenuItem("Referent a...");
        m1.add(m1Op1);
        m1.add(m1Op2);
        m2.add(m2Op1);
        m2.add(m2Op2);
        topBar.add(m1);
        topBar.add(m2);
        f.setJMenuBar(topBar);

        m1Op1.addActionListener(new ObrirNavegador("http://localhost:8081"));

        m2Op1.addActionListener(new ObrirNavegador("https://www.google.com"));
        m2Op2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(f, "Aplicacio desenvolupada per Gerard Balsells Franquesa\nEstudiant de 2n del Grau Superior de DAW\nEn el Institut Milà i Fontanals Igualada", "Referent a...", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    private void carregarPaginaPrincipal() {
        panell = new JPanel();
        JPanel panellSuperior = new JPanel();
        JComboBox<Filter> filtratge = new JComboBox();
        filtratge.addItem(new Filter(0,"Sense Filtre"));
        filtratge.addItem(new Filter(1,"Filtre per Correu Electronic"));
        filtratge.addItem(new Filter(2,"Filtre per Nom-Cognom"));
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(500,25));
        JButton cerca = new JButton("Cercar");
        panellSuperior.add(filtratge);
        panellSuperior.add(txt);
        panellSuperior.add(cerca);
        panellSuperior.add(cerca);
        panell.add(panellSuperior);
        JPanel usersZone = new JPanel();
        JComboBox<String> userList = new JComboBox();
        userList.addItem("Helicopter de Combat");
        userList.addItem("Avio de Combat");
        userList.addItem("Pelicula");
        userList.addItem("Pedra");
        usersZone.add(userList);
        panell.add(usersZone);
        f.add(panell);
    }

    public static void main(String[] args) {
        App app = new App();
    }

    class ObrirNavegador implements ActionListener {

        private String url;

        public ObrirNavegador(String url) {
            this.url = url;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            openBrowser(url);
        }
    }

    private void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException x) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(panell, "No s'ha pogut obrir el navegador per defecte", "Error en intentar obrir el navegador per defecte", JOptionPane.ERROR);
            } catch (URISyntaxException x) {
                JOptionPane.showMessageDialog(panell, "No s'ha pogut obrir el navegador per defecte", "Error en intentar obrir el navegador per defecte", JOptionPane.ERROR);
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException x) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(panell, "No s'ha pogut obrir el navegador per defecte", "Error en intentar obrir el navegador per defecte", JOptionPane.ERROR);
            }
        }
    }

}

class Filter {
    private int id;
    private String nom;

    public Filter(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}