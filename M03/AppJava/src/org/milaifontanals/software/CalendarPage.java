/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.software;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Activitat;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.Tipus_Activitat;
import org.milaifontanals.models.Usuari;

/**
 *
 * @author Usuari
 */
public class CalendarPage {

    private JDialog userPage;
    private JDialog calendarPage;
    private IBaseDeDades db;
    private Calendari cal;
    private Usuari user;

    public CalendarPage(JDialog userPage, IBaseDeDades db, Calendari cal, Usuari user) {
        this.userPage = userPage;
        this.db = db;
        this.cal = cal;
        this.user = user;
        carregarVista();
    }

    private void carregarVista() {
        calendarPage = new JDialog(this.userPage, "Editant Calendari:  " + cal.getNom(), true);
        JPanel panellPrincipal = new JPanel();
        panellPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.gridx = 0;
        gc.gridy = 0;
        panellPrincipal.add(new JLabel("Email Usuari: "), gc);
        gc.gridx = 1;
        gc.gridy = 0;
        panellPrincipal.add(new JLabel(user.getEmail()), gc);
        gc.gridx = 0;
        gc.gridy = 1;
        panellPrincipal.add(new JLabel("Nom Usuari: "), gc);
        gc.gridx = 1;
        gc.gridy = 1;
        panellPrincipal.add(new JLabel(user.getNom()), gc);
        gc.gridx = 0;
        gc.gridy = 2;
        panellPrincipal.add(new JLabel("Cognoms Usuari: "), gc);
        gc.gridx = 1;
        gc.gridy = 2;
        panellPrincipal.add(new JLabel(user.getCognoms()), gc);
        gc.gridx = 0;
        gc.gridy = 3;
        panellPrincipal.add(new JLabel("Calendari Nom: "), gc);
        gc.gridx = 1;
        gc.gridy = 3;
        panellPrincipal.add(new JLabel(cal.getNom()), gc);
        gc.gridx = 0;
        gc.gridy = 4;
        panellPrincipal.add(new JLabel("Calendari Data Creacio: "), gc);
        gc.gridx = 1;
        gc.gridy = 4;
        panellPrincipal.add(new JLabel(cal.getDataCreacio().toString()), gc);
        gc.gridx = 0;
        gc.gridy = 5;
        panellPrincipal.add(new JLabel("Tipus Usuari: "), gc);
        gc.gridx = 1;
        gc.gridy = 5;
        try {
            if (db.esPropietariCalendari(user, cal)) {
                panellPrincipal.add(new JLabel("Propietari"), gc);
            } else if (db.esAjudantCalendari(user, cal)) {
                panellPrincipal.add(new JLabel("Ajudant"), gc);
            }
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(userPage, "Error en obtenir si es propietari o ajudant del calendari " + cal.getNom(), "Error En Obrir el info calendari", JOptionPane.ERROR_MESSAGE);
        }
        try {
            ArrayList<Activitat> actList = db.getActivitatsCalendari(cal, user);
            JTable activitatTable = crearTaulaCalendari(actList, calendarPage, user);
            JScrollPane actTableScrol = new JScrollPane(activitatTable,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gc.gridx = 0;
            gc.gridy = 6;
            gc.gridwidth = 4;
            actTableScrol.setPreferredSize(new Dimension(700, 250));
            panellPrincipal.add(actTableScrol, gc);
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(calendarPage, ex.getMessage(), "Error En Obirr el info Calendari", JOptionPane.ERROR_MESSAGE);
        }
        JButton add = new JButton("Afegir Activitat");
        JButton mod = new JButton("Editar Activitat");
        JButton del = new JButton("Esborrar Activitat");            
        JPanel buttonZone = new JPanel();
        buttonZone.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonZone.add(add);
        buttonZone.add(mod);
        buttonZone.add(del);
        gc.gridx = 0;
        gc.gridy = 7;
        gc.gridwidth = 4;
        panellPrincipal.add(buttonZone, gc);
        calendarPage.setLayout(new FlowLayout());
        calendarPage.add(panellPrincipal);
        calendarPage.setType(Window.Type.NORMAL);
        calendarPage.setLocationRelativeTo(null);
        calendarPage.setResizable(false);
        calendarPage.pack();
        calendarPage.setVisible(true);
    }

    public JTable crearTaulaCalendari(ArrayList<Activitat> actList, JDialog pare, Usuari user) {
        DefaultTableModel modelTaula = new DefaultTableModel();
        JTable activitat = new JTable(modelTaula) {
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
                    case 2:
                        clazz = String.class;
                        break;
                    case 3:
                        clazz = String.class;
                        break;
                    case 4:
                        clazz = Tipus_Activitat.class;
                        break;
                    case 5:
                        clazz = String.class;
                        break;
                }
                return clazz;
            }

        };
        modelTaula.addColumn("Nom");
        modelTaula.addColumn("Data Inici");
        modelTaula.addColumn("Data Fi");
        modelTaula.addColumn("Tipus Activitat");
        modelTaula.addColumn("Publicada");
        activitat.getColumnModel().getColumn(0).setPreferredWidth(170);
        activitat.getColumnModel().getColumn(1).setPreferredWidth(170);
        activitat.getColumnModel().getColumn(2).setPreferredWidth(170);
        activitat.getColumnModel().getColumn(3).setPreferredWidth(107);
        activitat.getColumnModel().getColumn(4).setPreferredWidth(80);
        activitat.setRowHeight(25);
        activitat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (actList.size() > 0) {
            for (Activitat act : actList) {
                String dataFi;
                if(act.getDateFi() == null) {
                    dataFi = "";
                }
                else {
                    dataFi = act.getDateFi().toString();
                }
                String publicada;
                if(act.isPublicada()) {
                    publicada = "Si";
                }
                else {
                    publicada = "No";
                }
                modelTaula.addRow(new Object[]{act.getNom(), act.getDateInici().toString(), dataFi,act.getTipus(), publicada});
            }
        }
        activitat.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Activitat cal = (Activitat) actList.get(table.getSelectedRow());
                    //CalendarPage calendarPage = new CalendarPage(userPage,db, cal, user);
                }
            }
        });
        return activitat;
    }
}
