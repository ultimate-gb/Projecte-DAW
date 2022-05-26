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
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.Usuari;

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
                dataLabel.add(new JLabel("Email: "));
                dataLabel.add(new JLabel(user.getEmail()));
                JTextField emailT = new JTextField(15);
                emailT.setText(user.getEmail());
                emailT.setEditable(false);
                dataTarget.add(emailT);
                dataLabel.add(new JLabel("Nom: "));
                dataLabel.add(new JLabel(user.getNom()));
                JTextField nomT = new JTextField(15);
                nomT.setText(user.getNom());
                dataTarget.add(nomT);
                dataLabel.add(new JLabel("Cognom"));
                dataLabel.add(new JLabel(user.getCognoms()));
                JTextField cognomT = new JTextField(15);
                cognomT.setText(user.getCognoms());
                dataTarget.add(cognomT);
                dataLabel.add(new JLabel("Data Naixement: "));
                dataLabel.add(new JLabel(user.getData_naix().toString()));
                JTextField dataNaixT = new JTextField(15);
                dataNaixT.setText(user.getData_naix().toString());
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
                rol.setSelectedIndex(user.getRole());
                dataTarget.add(rol);
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
            JButton cancelarBtn = new JButton("Cancelar");
            cancelarBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                    CalendarPage calendarPage = new CalendarPage(userPage,db, cal, user);
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
