/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.software;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.models.Usuari;
import org.milaifontanals.iface.ProjecteDawException;

/**
 *
 * @author Usuari
 */
public class App {

    private JFrame f;
    private JPanel panell;
    private JDialog userPage;
    private JMenuBar topBar;
    private JMenu m1;
    private JMenu m2;
    private JMenuItem m1Op1;
    private JMenuItem m1Op2;
    private JMenuItem m2Op1;
    private JMenuItem m2Op2;
    public IBaseDeDades db;

    public App() {
        /* Inicialitzant La Finestra */
        f = new JFrame("App Java");
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        /* Establint procediment en tancar la finestra */
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
                    try {
                        db.tancarConnexio();
                    } catch (ProjecteDawException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        /* Connectant a la base de dades */
        String nomCapa = "org.milaifontanals.bdr.MySql";     // Aquest nom s'ha d'obtenir
        try {
            // des de fitxer de propietats o via args. Mai per codi
            db = (IBaseDeDades) Class.forName(nomCapa).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(f, "No es pot obrir la capa on hi ha el model per connectar a la base de dades", "Error En Obrir Aplicacio", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, ex.getMessage(), "Error En Obrir Aplicacio", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        f.setSize(new Dimension(800, 600));
        f.setLocationRelativeTo(null);
        carregarEntornGrafic();
        f.setVisible(true);

    }

    /* S'encarrega de carregar el entorn grafic */
    private void carregarEntornGrafic() {
        carregarMenu();
    }

    /* Carrega el menú superior de la finestra */
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

        m1Op2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarPaginaUsuari();
            }

        });

        m2Op1.addActionListener(new ObrirNavegador("https://www.google.com"));
        /* Opcio menu que obra una finestra de informacio sobre la aplicacio */
        m2Op2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(f, "Aplicacio desenvolupada per Gerard Balsells Franquesa\nEstudiant de 2n del Grau Superior de DAW\nEn el Institut Milà i Fontanals Igualada", "Referent a...", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    /* Aquesta funcio s'encarrega de carregar la pagina principal */
    private void carregarPaginaUsuari() {
        userPage = new JDialog(f, "Pagina Usuari");
        /* Inicialitza el jpanel */
        panell = new JPanel();
        /* S'estableix el Layout que es vol utilitzar per visualitzar la pagina web */
        panell.setLayout(new BorderLayout());
        JPanel panellSuperior = new JPanel();
        panellSuperior.setLayout(new BoxLayout(panellSuperior, BoxLayout.Y_AXIS));
        JComboBox<Filter> filtratge = new JComboBox();
        filtratge.addItem(new Filter(0, "Sense Filtre"));
        filtratge.addItem(new Filter(1, "Filtre per Correu Electronic"));
        filtratge.addItem(new Filter(2, "Filtre per Nom-Cognom"));
        filtratge.setSize(600, 25);
        JPanel filterForm = new JPanel();
        filterForm.add(filtratge);
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        JTextField email = new JTextField(50);
        JTextField nom = new JTextField(50);
        JTextField cognom = new JTextField(50);
        JButton cerca = new JButton("Cercar");
        JComboBox<Usuari> jUserList = new JComboBox();
        try {
            ArrayList<Usuari> userList = db.getAllUsers();
            for (Usuari user : userList) {
                jUserList.addItem(user);
            }
            jUserList.setSelectedIndex(-1);
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(userPage, "Error en la cerca " + ex.getMessage(), "Error En Cerca", JOptionPane.ERROR_MESSAGE);
        }
        filtratge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox filSel = (JComboBox) e.getSource();
                Filter fil = (Filter) filSel.getSelectedItem();
                form.removeAll();
                email.setText("");;
                nom.setText("");
                cognom.setText("");
                if (fil.getId() == 1) {
                    JPanel p1 = new JPanel();
                    p1.setLayout(new GridBagLayout());
                    GridBagConstraints gc = new GridBagConstraints();
                    gc.fill = GridBagConstraints.HORIZONTAL;
                    gc.insets = new Insets(10, 10, 10, 10);
                    gc.gridx = 0;
                    gc.gridy = 0;
                    JLabel label1 = new JLabel("Email: ");
                    label1.setHorizontalAlignment(SwingConstants.LEFT);
                    p1.add(label1, gc);

                    gc.gridx = 1;
                    gc.gridy = 0;
                    p1.add(email, gc);

                    gc.gridx = 0;
                    gc.gridy = 1;
                    gc.gridwidth = 2;
                    JPanel cercaZone = new JPanel();
                    cercaZone.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    cercaZone.add(cerca);
                    p1.add(cercaZone, gc);
                    //p1.add(email);
                    form.add(p1);
                    //form.add(cerca);
                } else if (fil.getId() == 2) {
                    JPanel p1 = new JPanel();
                    p1.setLayout(new GridBagLayout());
                    GridBagConstraints gc = new GridBagConstraints();
                    gc.fill = GridBagConstraints.HORIZONTAL;
                    gc.insets = new Insets(10, 10, 10, 10);
                    gc.gridx = 0;
                    gc.gridy = 0;
                    JLabel label1 = new JLabel("Nom: ");
                    label1.setHorizontalAlignment(SwingConstants.LEFT);
                    p1.add(label1, gc);
                    gc.gridx = 1;
                    gc.gridy = 0;
                    p1.add(nom, gc);
                    gc.gridx = 0;
                    gc.gridy = 1;
                    JLabel label2 = new JLabel("Cognom: ");
                    label2.setHorizontalAlignment(SwingConstants.LEFT);
                    p1.add(label2, gc);
                    gc.gridx = 1;
                    gc.gridy = 1;
                    p1.add(cognom, gc);
                    gc.gridx = 0;
                    gc.gridy = 2;
                    gc.gridwidth = 2;
                    JPanel cercaZone = new JPanel();
                    cercaZone.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    cercaZone.add(cerca);
                    p1.add(cercaZone, gc);
                    form.add(p1);
                } else if (fil.getId() == 0) {
                    try {
                        ArrayList<Usuari> userList = db.getAllUsers();
                        jUserList.removeAllItems();
                        for (Usuari user : userList) {
                            jUserList.addItem(user);
                        }
                        jUserList.setSelectedIndex(-1);
                    } catch (ProjecteDawException ex) {
                        JOptionPane.showMessageDialog(userPage, "Error en la cerca " + ex.getMessage(), "Error En Cerca", JOptionPane.ERROR_MESSAGE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(userPage);
            }
        });

        cerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Filter fil = (Filter) filtratge.getSelectedItem();
                ArrayList<Usuari> userList = new ArrayList();
                if (fil.getId() == 1) {
                    try {
                        userList = db.cercaUserByEmail(email.getText());
                    } catch (ProjecteDawException ex) {
                        JOptionPane.showMessageDialog(userPage, "Error en la cerca " + ex.getMessage(), "Error En Cerca", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (fil.getId() == 2) {
                    try {
                        userList = db.cercaByNomCognom(nom.getText(), cognom.getText());
                    } catch (ProjecteDawException ex) {
                        JOptionPane.showMessageDialog(userPage, "Error en la cerca " + ex.getMessage(), "Error En Cerca", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (userList != null) {
                    if (userList.size() > 0) {
                        jUserList.removeAllItems();
                        for (Usuari user : userList) {
                            jUserList.addItem(user);
                        }
                        jUserList.setSelectedIndex(-1);
                    } else {
                        JOptionPane.showMessageDialog(f, "No s'han trobat cap Usuari que contingui en el Correu: " + email.getText(), "Info Cerca", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(f, "No s'han trobat cap resultat", "Info Cerca", JOptionPane.INFORMATION_MESSAGE);
                }
                SwingUtilities.updateComponentTreeUI(userPage);
            }
        });
        jUserList.setSelectedIndex(-1);
        jUserList.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox selUser = (JComboBox) e.getSource();
                if(selUser.getSelectedIndex() > -1) {
                    Usuari user = (Usuari) selUser.getSelectedItem();
                    JOptionPane.showMessageDialog(f, "Has seleccionat a " +user.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panellSuperior.add(filterForm);
        panellSuperior.add(form);
        //panellSuperior.setPreferredSize(new Dimension(800,250));
        panell.add(panellSuperior, BorderLayout.NORTH);
        JPanel usersZone = new JPanel();
        usersZone.add(jUserList);
        panell.add(usersZone);
        userPage.add(panell);
        userPage.setSize(800, 250);
        userPage.setType(Window.Type.NORMAL);
        userPage.setLocationRelativeTo(null);
        userPage.setResizable(false);
        userPage.setVisible(true);

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

    @Override
    public String toString() {
        return nom;
    }
}
