/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.software;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TextAction;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.Usuari;
import org.milaifontanals.models.Nacionalitat;

/**
 *
 * @author Usuari
 */
public class UserPage {

    private JFrame f;
    private JDialog userPage;
    private IBaseDeDades db;

    public UserPage(JFrame f, IBaseDeDades db) {
        this.f = f;
        this.db = db;
        carregarPaginaUsuari();
    }

    /* Aquesta funcio s'encarrega de carregar la pagina principal */
    private void carregarPaginaUsuari() {
        userPage = new JDialog(f, "Pagina Usuari", true);
        /* Inicialitza el jpanel */
        JPanel panell = new JPanel();
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
                aplicarFiltre((JComboBox) e.getSource(), form, email, nom, cognom, jUserList, cerca);
            }
        });

        cerca.addActionListener(new CercaFiltre(filtratge, email, nom, cognom, jUserList));
        jUserList.setSelectedIndex(-1);
        jUserList.addActionListener(new SeeUsers());
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

    public void aplicarFiltre(JComboBox filSel, JPanel form, JTextField email, JTextField nom, JTextField cognom, JComboBox<Usuari> jUserList, JButton cerca) {
        Filter fil = (Filter) filSel.getSelectedItem();
        form.removeAll();
        email.setText("");;
        nom.setText("");
        cognom.setText("");
        jUserList.setSelectedIndex(-1);
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
                jUserList.removeActionListener(jUserList.getActionListeners()[0]);
                for (Usuari user : userList) {
                    jUserList.addItem(user);
                }
                jUserList.setSelectedIndex(-1);
                jUserList.addActionListener(new SeeUsers());
            } catch (ProjecteDawException ex) {
                JOptionPane.showMessageDialog(userPage, "Error en la cerca " + ex.getMessage(), "Error En Cerca", JOptionPane.ERROR_MESSAGE);
            }
        }
        SwingUtilities.updateComponentTreeUI(userPage);
    }

    class SeeUsers implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox selUser = (JComboBox) e.getSource();
            if (selUser.getSelectedIndex() > -1) {
                Usuari user = (Usuari) selUser.getSelectedItem();
                JDialog dadesUser = new JDialog(userPage, "Dades Usuari " + user.toString(), true);
                dadesUser.setLayout(new FlowLayout());
                JPanel panellPrincipal = new JPanel();
                panellPrincipal.setLayout(new GridBagLayout());
                ArrayList<JLabel> dataLabel = new ArrayList();
                ArrayList<JComponent> dataTarget = new ArrayList();
                generarDataLT(dataLabel, dataTarget, user);
                visitarUser(dataLabel, panellPrincipal, user, dataTarget, dadesUser);
                dadesUser.add(panellPrincipal);
                dadesUser.setResizable(false);
                dadesUser.setType(Window.Type.POPUP);
                dadesUser.pack();
                dadesUser.setLocationRelativeTo(null);
                dadesUser.setResizable(false);
                dadesUser.setVisible(true);
            }
        }
    }

    public void generarDataLT(ArrayList<JLabel> dataLabel, ArrayList<JComponent> dataTarget, Usuari user) {
        if (dataLabel.size() > 0) {
            dataLabel.clear();
        }
        if (dataTarget.size() > 0) {
            dataTarget.clear();
        }
        dataLabel.add(new JLabel("Email: "));
        dataLabel.add(new JLabel(user.getEmail()));
        JTextField emailT = new JTextField(15);
        emailT.setText(user.getEmail());
        emailT.setName("email");
        emailT.setEditable(false);
        dataTarget.add(emailT);
        dataLabel.add(new JLabel("Nom: "));
        dataLabel.add(new JLabel(user.getNom()));
        JTextField nomT = new JTextField(15);
        nomT.setText(user.getNom());
        nomT.setName("nom");
        dataTarget.add(nomT);
        dataLabel.add(new JLabel("Cognom"));
        dataLabel.add(new JLabel(user.getCognoms()));
        JTextField cognomT = new JTextField(15);
        cognomT.setText(user.getCognoms());
        cognomT.setName("cognoms");
        dataTarget.add(cognomT);
        dataLabel.add(new JLabel("Data Naixement: "));
        dataLabel.add(new JLabel(user.getData_naix().toString()));
        JTextField dataNaixT = new JTextField(15);
        dataNaixT.setText(user.getData_naix().toString());
        dataNaixT.setName("dataNaix");
        dataTarget.add(dataNaixT);
        dataLabel.add(new JLabel("Genere"));
        JComboBox<String> gen = new JComboBox();
        gen.addItem("Home");
        gen.addItem("Dona");
        gen.addItem("Desconegut");
        if (user.getGenere() == 'H') {
            dataLabel.add(new JLabel("Home"));
            gen.setSelectedIndex(0);
        } else if (user.getGenere() == 'D') {
            dataLabel.add(new JLabel("Dona"));
            gen.setSelectedIndex(1);
        } else {
            dataLabel.add(new JLabel("No Conegut"));
            gen.setSelectedIndex(2);
        }
        gen.setName("genere");
        dataTarget.add(gen);
        dataLabel.add(new JLabel("Telefon"));
        JTextField telefonT = new JTextField(15);
        if (user.getTelefon() != null) {
            dataLabel.add(new JLabel(user.getTelefon().toString()));
            telefonT.setText(user.getTelefon().toString());
        } else {
            dataLabel.add(new JLabel(""));
            telefonT.setText("");;
        }
        telefonT.setName("telefon");
        dataTarget.add(telefonT);
        dataLabel.add(new JLabel("Bloquejat: "));
        JCheckBox bloq = new JCheckBox();
        if (user.isBloquejat()) {
            dataLabel.add(new JLabel("Si"));
            bloq.setSelected(true);
        } else {
            dataLabel.add(new JLabel("No"));
            bloq.setSelected(false);
        }
        bloq.setName("bloq");
        dataTarget.add(bloq);
        dataLabel.add(new JLabel("Role: "));
        JComboBox<String> rol = new JComboBox();
        rol.addItem("Usuari");
        rol.addItem("Admin");
        if (user.getRole() == 0) {
            dataLabel.add(new JLabel("Usuari"));
        } else {
            dataLabel.add(new JLabel("Admin"));
        }
        rol.setName("role");
        rol.setSelectedIndex(user.getRole());
        dataTarget.add(rol);
        dataLabel.add(new JLabel("Token:"));
        dataLabel.add(new JLabel(user.getToken()));
        JTextField tokenT = new JTextField(15);
        tokenT.setText(user.getToken());
        tokenT.setName("token");
        dataTarget.add(tokenT);
        dataLabel.add(new JLabel("Validat: "));
        JCheckBox val = new JCheckBox();
        if (user.isValidat()) {
            dataLabel.add(new JLabel("Si"));
            val.setSelected(true);
        } else {
            dataLabel.add(new JLabel("No"));
            val.setSelected(false);
        }
        val.setName("val");
        dataTarget.add(val);
        dataLabel.add(new JLabel("Nacionalitat: "));
        dataLabel.add(new JLabel(user.getNacionalitat().getNom()));
        JComboBox<Nacionalitat> nacionalitatT = new JComboBox();
        try {
            ArrayList<Nacionalitat> nacionalitatList = db.getAllNacionalitats();
            int nacionalitatPersona = -1;
            int i = 0;
            for (Nacionalitat nat : nacionalitatList) {
                nacionalitatT.addItem(nat);
                if (nat.getCodi().equals(user.getNacionalitat().getCodi())) {
                    nacionalitatPersona = i;
                }
                i++;
            }
            nacionalitatT.setSelectedIndex(nacionalitatPersona);
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(userPage, "Error en obtenir totes les nacionalitats.\nLi recomanem que tanqui la finestra i la torni a obrir.", "Error En Obrir Pagina Usuari", JOptionPane.ERROR_MESSAGE);
        }
        nacionalitatT.setName("nacionalitat");
        dataTarget.add(nacionalitatT);
    }

    class EditAction implements ActionListener {

        private Usuari user;
        private ArrayList<JLabel> dataLabel;
        private ArrayList<JComponent> dataTarget;
        private JPanel panellPrincipal;
        private JDialog dadesUser;

        public EditAction(Usuari user, ArrayList<JLabel> dataLabel, ArrayList<JComponent> dataTarget, JPanel panellPrincipal, JDialog dadesUser) {
            this.dataLabel = dataLabel;
            this.user = user;
            this.panellPrincipal = panellPrincipal;
            this.dataTarget = dataTarget;
            this.dadesUser = dadesUser;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.insets = new Insets(10, 10, 10, 10);
            panellPrincipal.removeAll();
            int x = 0;
            int y = 0;
            for (JLabel l : dataLabel) {
                gc.gridx = x;
                gc.gridy = y;
                gc.gridwidth = 1;
                if (x == 1) {
                    if (dataTarget.get(y) instanceof JTextField) {
                        JTextField text = (JTextField) dataTarget.get(y);
                        panellPrincipal.add(text, gc);
                    } else if (dataTarget.get(y) instanceof JComboBox) {
                        JComboBox jcbb = (JComboBox) dataTarget.get(y);
                        panellPrincipal.add(jcbb, gc);
                    } else {
                        JCheckBox jckb = (JCheckBox) dataTarget.get(y);
                        panellPrincipal.add(jckb, gc);
                    }
                } else {
                    panellPrincipal.add(l, gc);
                }

                x++;
                if (x > 1) {
                    y++;
                    x = 0;
                }
            }
            gc.gridx = 0;
            gc.gridy = y;
            gc.gridwidth = 2;
            JPanel editZone = new JPanel();
            editZone.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton guardarBtn = new JButton("Guardar");
            editZone.add(guardarBtn);
            guardarBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int validades = 0;
                    int i = 0;
                    int errorsTrobats = 0;
                    String campsInvalids = "";
                    Usuari u = new Usuari();
                    for (JComponent jc : dataTarget) {
                        if (jc instanceof JTextField) {
                            JTextField text = (JTextField) jc;
                            String name = text.getName();
                            if (name.equals("nom")) {
                                if (text.getText().length() <= 40 && text.getText().matches("^[A-Z][a-z]+\\s?[A-Z]?[a-z]*$")) {
                                    validades++;
                                    u.setNom(text.getText());
                                } else {
                                    errorsTrobats++;
                                    if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                        campsInvalids += ",\n";
                                    }
                                    campsInvalids += "nom";
                                }
                            } else if (name.equals("cognoms")) {
                                if (text.getText().length() <= 100 && text.getText().matches("^[A-Z][a-z]+\\s?[A-Z]?[a-z]*$")) {
                                    validades++;
                                    u.setCognoms(text.getText());
                                } else {
                                    errorsTrobats++;
                                    if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                        campsInvalids += ",\n";
                                    }
                                    campsInvalids += "cognoms";
                                }
                            } else if (name.equals("dataNaix")) {
                                if (text.getText().matches("^\\d{4}\\-\\d{2}\\-\\d{2}$")) {
                                    int year = Integer.parseInt(text.getText().substring(0, 4));
                                    int mes = Integer.parseInt(text.getText().substring(5, 7));
                                    int dia = Integer.parseInt(text.getText().substring(8));
                                    java.util.Date today = new java.util.Date();
                                    java.sql.Date todayDate = new java.sql.Date(today.getTime());
                                    java.sql.Date data = new java.sql.Date(year - 1900, mes - 1, dia);
                                    if (todayDate.getYear() - data.getYear() >= 18) {
                                        validades++;
                                        u.setData_naix(data);
                                    } else {
                                        errorsTrobats++;
                                        if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                            campsInvalids += ",\n";
                                        }
                                        campsInvalids += "Data Naixement: Ha de ser major de 18 anys";
                                    }

                                } else {
                                    errorsTrobats++;
                                    if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                        campsInvalids += ",\n";
                                    }
                                    campsInvalids += "Data Naixement(Format Any-Mes-Dia)";
                                }
                            } else if (name.equals("telefon")) {
                                if (text.getText().length() == 0) {
                                    validades++;
                                } else if (text.getText().matches("^[1-9][0-9]{8}$")) {
                                    validades++;
                                    u.setTelefon(text.getText());
                                } else {
                                    errorsTrobats++;
                                    if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                        campsInvalids += ",\n";
                                    }
                                    campsInvalids += "telefon";
                                }
                            } else if (name.equals("token")) {
                                if (!text.getText().equals(user.getToken())) {
                                    Object[] noms = {"S??", "No"};
                                    int opc
                                            = JOptionPane.showOptionDialog(f, "S'ha detectat que has modiificat el token, estas segur que vols continuar?", "Atenci??!",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE, null, noms, noms[0]);

                                    if (opc == JOptionPane.YES_OPTION) {
                                        u.setToken(text.getText());
                                    } else {
                                        text.setText(user.getToken());
                                        u.setToken(user.getToken());
                                    }
                                }
                            } else if (name.equals("email")) {
                                u.setEmail(text.getText());
                            }
                        } else if (jc instanceof JComboBox) {
                            JComboBox jcbb = (JComboBox) jc;
                            if (jcbb.getSelectedIndex() > -1) {
                                validades++;
                                if (jcbb.getName().equals("role")) {
                                    u.setRole(jcbb.getSelectedIndex());
                                } else if (jcbb.getName().equals("genere")) {
                                    int tGenere = jcbb.getSelectedIndex();
                                    if (tGenere == 0) {
                                        u.setGenere('H');
                                    } else if (tGenere == 1) {
                                        u.setGenere('D');
                                    } else {
                                        u.setGenere('I');
                                    }
                                } else if (jcbb.getName().equals("nacionalitat")) {
                                    Nacionalitat nact = (Nacionalitat) jcbb.getSelectedItem();
                                    u.setNacionalitat(nact);
                                }
                            } else {
                                errorsTrobats++;
                                if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                    campsInvalids += ",\n";
                                }
                                campsInvalids += jcbb.getName();
                            }
                        } else {
                            JCheckBox jckb = (JCheckBox) jc;
                            if (jckb.getName().equals("bloq")) {
                                u.setBloquejat(jckb.isSelected());
                            } else if (jckb.getName().equals("val")) {
                                u.setValidat(jckb.isSelected());
                            }
                        }
                        i++;
                    }
                    if (validades < 7) {
                        JOptionPane.showMessageDialog(userPage, "Dades Modificades Invalides.\nCamps invalids: " + campsInvalids, "Error Al Validar Les Dades", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        if (db.updateUser(u) > 0) {
                            db.aplicarCanvis();
                            u.setId(user.getId());
                            user = u;
                            generarDataLT(dataLabel, dataTarget, user);
                            visitarUser(dataLabel, panellPrincipal, user, dataTarget, dadesUser);
                            dadesUser.pack();
                        } else {
                            JOptionPane.showMessageDialog(userPage, "Error en Actualitzar.", "Error Al Actualitzar", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (ProjecteDawException ex) {
                        JOptionPane.showMessageDialog(userPage, "Error en Actualitzar.", "Error Al Actualitzar", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            JButton cancelarBtn = new JButton("Cancelar");
            cancelarBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JComponent jc : dataTarget) {
                        if (jc instanceof JTextField) {
                            JTextField text = (JTextField) jc;
                            String name = text.getName();
                            if (name.equals("nom")) {
                                text.setText(user.getNom());
                            } else if (name.equals("cognoms")) {
                                text.setText(user.getCognoms());
                            } else if (name.equals("dataNaix")) {
                                text.setText(user.getData_naix().toString());
                            } else if (name.equals("telefon")) {
                                text.setText(user.getTelefon().toString());
                            } else if (name.equals("token")) {
                                text.setText(user.getToken());
                            }

                        } else if (jc instanceof JComboBox) {
                            JComboBox jcbb = (JComboBox) jc;
                            if (jcbb.getName().equals("role")) {
                                jcbb.setSelectedIndex(user.getRole());
                            } else if (jcbb.getName().equals("genere")) {
                                if (user.getGenere() == 'H') {
                                    jcbb.setSelectedIndex(0);
                                } else if (user.getGenere() == 'D') {
                                    jcbb.setSelectedIndex(1);
                                } else {
                                    jcbb.setSelectedIndex(1);
                                }
                            } else if (jcbb.getName().equals("nacionalitat")) {
                                try {
                                    ArrayList<Nacionalitat> nacionalitatList = db.getAllNacionalitats();
                                    int nacionalitatPersona = -1;
                                    int i = 0;
                                    for (Nacionalitat nat : nacionalitatList) {
                                        if (nat.getCodi().equals(user.getNacionalitat().getCodi())) {
                                            nacionalitatPersona = i;
                                        }
                                        i++;
                                    }
                                    jcbb.setSelectedIndex(nacionalitatPersona);
                                } catch (ProjecteDawException ex) {
                                    JOptionPane.showMessageDialog(userPage, "Error en obtenir totes les nacionalitats.\nLi recomanem que tanqui la finestra i la torni a obrir.", "Error En Obrir Pagina Usuari", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JCheckBox jckb = (JCheckBox) jc;
                            if (jckb.getName().equals("bloq")) {
                                jckb.setSelected(user.isBloquejat());
                            } else if (jckb.getName().equals("val")) {
                                jckb.setSelected(user.isValidat());
                            }
                        }
                    }
                    visitarUser(dataLabel, panellPrincipal, user, dataTarget, dadesUser);
                    dadesUser.pack();
                }
            });
            editZone.add(cancelarBtn);

            panellPrincipal.add(editZone, gc);
            JPanel tableZone = generarPanellTaules(user, dadesUser);
            gc.gridx = 0;
            gc.gridy = y + 2;
            gc.gridwidth = 2;
            panellPrincipal.add(tableZone, gc);
            dadesUser.setTitle("Modficant Dades Usuari " + user.toString());
            SwingUtilities.updateComponentTreeUI(dadesUser);

            dadesUser.pack();
        }
    }

    public JPanel generarPanellTaules(Usuari user, JDialog dadesUser) {
        JPanel tableZone = new JPanel();
        try {
            ArrayList<Calendari> owner = this.db.cercaCalendariPropietari(user);
            JTable calendarOwner = crearTaulaCalendari(owner, dadesUser, user);
            JScrollPane ownerCalScrol = new JScrollPane(calendarOwner,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            ownerCalScrol.setPreferredSize(new Dimension(400, 250));
            tableZone.add(ownerCalScrol);
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(userPage, "Error en obrir la taula de calendari propietari", "Error En Obrir Usuari", JOptionPane.ERROR_MESSAGE);
        }
        try {
            ArrayList<Calendari> helper = this.db.cercaCalendariAjudant(user);
            JTable calendarHelper = crearTaulaCalendari(helper, dadesUser, user);

            JScrollPane helperCalScrol = new JScrollPane(calendarHelper,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            helperCalScrol.setPreferredSize(new Dimension(400, 250));
            tableZone.add(helperCalScrol);
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(userPage, "Error en obrir la taula de calendari d'ajudant", "Error En Obrir Usuari", JOptionPane.ERROR_MESSAGE);
        }
        return tableZone;
    }

    public JTable crearTaulaCalendari(ArrayList<Calendari> calList, JDialog pare, Usuari user) {
        DefaultTableModel modelTaula = new DefaultTableModel();
        JTable calendar = new JTable(modelTaula) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                Class clazz = String.class;
                switch (column) {
                    case 0:
                        clazz = String.class;
                        break;
                    case 1:
                        clazz = java.sql.Date.class;
                        break;
                }
                return clazz;
            }

        };
        modelTaula.addColumn("Nom");
        modelTaula.addColumn("Data_Creacio");
        calendar.getColumnModel().getColumn(0).setPreferredWidth(225);
        calendar.getColumnModel().getColumn(1).setPreferredWidth(172);
        calendar.setRowHeight(25);
        calendar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (calList.size() > 0) {
            for (Calendari cal : calList) {
                modelTaula.addRow(new Object[]{cal.getNom(), cal.getDataCreacio()});
            }
        }
        calendar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Calendari cal = (Calendari) calList.get(table.getSelectedRow());
                    CalendarPage calendarPage = new CalendarPage(userPage, db, cal, user);
                }
            }
        });
        return calendar;
    }

    public void visitarUser(ArrayList<JLabel> dataLabel, JPanel panellPrincipal, Usuari user, ArrayList<JComponent> dataTarget, JDialog dadesUser) {
        panellPrincipal.removeAll();
        panellPrincipal.repaint();
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        int x = 0;
        int y = 0;
        for (JLabel l : dataLabel) {
            gc.gridx = x;
            gc.gridy = y;
            panellPrincipal.add(l, gc);
            x++;
            if (x > 1) {
                y++;
                x = 0;
            }
        }
        gc.gridx = 0;
        gc.gridy = y + 1;
        gc.gridwidth = 2;
        JPanel editZone = new JPanel();
        editZone.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton editBtn = new JButton("Editar");
        editZone.add(editBtn);
        editBtn.addActionListener(new EditAction(user, dataLabel, dataTarget, panellPrincipal, dadesUser));
        panellPrincipal.add(editZone, gc);
        JPanel tableZone = generarPanellTaules(user, dadesUser);
        gc.gridx = 0;
        gc.gridy = y + 2;
        gc.gridwidth = 2;
        panellPrincipal.add(tableZone, gc);
        dadesUser.setTitle("Dades Usuari " + user.toString());
        SwingUtilities.updateComponentTreeUI(dadesUser);
    }

    class CercaFiltre implements ActionListener {

        private JComboBox<Filter> filtratge;
        private JTextField email;
        private JTextField nom;
        private JTextField cognom;
        private JComboBox<Usuari> jUserList;

        public CercaFiltre(JComboBox<Filter> filtratge, JTextField email, JTextField nom, JTextField cognom, JComboBox<Usuari> userlist) {
            this.filtratge = filtratge;
            this.email = email;
            this.nom = nom;
            this.cognom = cognom;
            this.jUserList = userlist;
        }

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
                    jUserList.removeActionListener(jUserList.getActionListeners()[0]);
                    for (Usuari user : userList) {
                        jUserList.addItem(user);
                    }
                    jUserList.setSelectedIndex(-1);
                    jUserList.addActionListener(new SeeUsers());
                } else {
                    JOptionPane.showMessageDialog(f, "No s'han trobat cap Usuari que contingui en el Correu: " + email.getText(), "Info Cerca", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(f, "No s'han trobat cap resultat", "Info Cerca", JOptionPane.INFORMATION_MESSAGE);
            }

            SwingUtilities.updateComponentTreeUI(userPage);
        }
    }
}
