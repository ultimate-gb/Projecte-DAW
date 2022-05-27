/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.software;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Activitat;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.TipusActivitat;
import org.milaifontanals.models.Usuari;

/**
 *
 * @author Usuari
 */
public class ActivitatPage {

    private JDialog activitatPage;
    private JDialog calendarPage;
    private IBaseDeDades db;
    private Activitat act;
    private Calendari cal;
    private Usuari user;

    public ActivitatPage(JDialog calendarPage, IBaseDeDades db, Activitat act, Calendari cal, Usuari user, int tipus) {
        this.calendarPage = calendarPage;
        this.db = db;
        this.act = act;
        this.cal = cal;
        this.user = user;
        if (tipus == 0) {
            carregarVistaAfegir();
        } else if (tipus == 1) {
            carregarVistaModificar();
        }
    }

    public void carregarVistaModificar() {
        activitatPage = new JDialog(calendarPage, "Modificant Activitat: " + act.getNom(), true);
        activitatPage.setLayout(new FlowLayout());
        JPanel panellPrincipal = new JPanel();
        panellPrincipal.setLayout(new GridBagLayout());
        ArrayList<JLabel> dataLabel = new ArrayList();
        ArrayList<JComponent> dataTarget = new ArrayList();
        dataLabel.add(new JLabel("Nom: "));
        JTextField nomT = new JTextField(15);
        nomT.setText(act.getNom());
        dataTarget.add(nomT);
        dataLabel.add(new JLabel("Data Inici: "));
        JTextField dataIniciT = new JTextField(15);
        dataIniciT.setText(act.getDateInici().toString());
        dataTarget.add(dataIniciT);
        dataLabel.add(new JLabel("Data Final: "));
        JTextField dataFiT = new JTextField(15);
        dataFiT.setText(act.getDateFi().toString());
        dataTarget.add(dataFiT);
        dataLabel.add(new JLabel("Descripcio: "));
        JTextArea descT = new JTextArea(50,100);
        descT.setText(act.getDescripcio());
        dataTarget.add(descT);
        dataLabel.add(new JLabel("Tipus Activitat: "));
        JComboBox<TipusActivitat> tp = new JComboBox();
        try {
            ArrayList<TipusActivitat> tpList = db.getTipusActivitats(cal.getUser());
            for (TipusActivitat tip : tpList) {
                tp.addItem(tip);
                System.out.println(tip);
            }
            tp.setSelectedIndex(act.getTipus().getCodi());
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(activitatPage, "Error en Obrir Editar Activitat", "Error En Obrir Editar Activitat", JOptionPane.ERROR_MESSAGE);
        }
        dataTarget.add(tp);
        JPanel editZone = new JPanel();
        editZone.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton guardarBtn = new JButton("Guardar");
        editZone.add(guardarBtn);
        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activitatPage.setVisible(false);
            }
        });
        editZone.add(cancelarBtn);
        carregarForm(panellPrincipal, dataLabel, dataTarget, editZone);
        activitatPage.add(panellPrincipal);
        activitatPage.setResizable(false);
        activitatPage.setType(Window.Type.POPUP);
        activitatPage.pack();
        activitatPage.setLocationRelativeTo(null);
        activitatPage.setResizable(false);
        activitatPage.setVisible(true);
    }

    public void carregarVistaAfegir() {
        activitatPage = new JDialog(calendarPage, "Afegint Activitat", true);
        activitatPage.setLayout(new FlowLayout());
        JPanel panellPrincipal = new JPanel();
        panellPrincipal.setLayout(new GridBagLayout());
        ArrayList<JLabel> dataLabel = new ArrayList();
        ArrayList<JComponent> dataTarget = new ArrayList();
        dataLabel.add(new JLabel("Nom: "));
        JTextField nomT = new JTextField(15);
        dataTarget.add(nomT);
        dataLabel.add(new JLabel("Data Inici: "));
        JTextField dataIniciT = new JTextField(15);
        dataTarget.add(dataIniciT);
        dataLabel.add(new JLabel("Data Final: "));
        JTextField dataFiT = new JTextField(15);
        dataTarget.add(dataFiT);
        dataLabel.add(new JLabel("Descripcio: "));
        JTextField descT = new JTextField(15);
        dataTarget.add(descT);
        dataLabel.add(new JLabel("Tipus Activitat: "));
        JComboBox<TipusActivitat> tp = new JComboBox();
        try {
            ArrayList<TipusActivitat> tpList = db.getTipusActivitats(cal.getUser());
            for (TipusActivitat tip : tpList) {
                tp.addItem(tip);
                System.out.println(tip);
            }
        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(activitatPage, "Error en Obrir Editar Activitat", "Error En Obrir Editar Activitat", JOptionPane.ERROR_MESSAGE);
        }
        dataTarget.add(tp);
        JPanel editZone = new JPanel();
        editZone.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton guardarBtn = new JButton("Guardar");
        editZone.add(guardarBtn);
        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activitatPage.setVisible(false);
            }
        });
        editZone.add(cancelarBtn);
        carregarForm(panellPrincipal, dataLabel, dataTarget, editZone);
        activitatPage.add(panellPrincipal);
        activitatPage.setResizable(false);
        activitatPage.setType(Window.Type.POPUP);
        activitatPage.pack();
        activitatPage.setLocationRelativeTo(null);
        activitatPage.setResizable(false);
        activitatPage.setVisible(true);
    }

    public void carregarForm(JPanel panellPrincipal, ArrayList<JLabel> dataLabel, ArrayList<JComponent> dataTarget, JPanel editZone) {
        panellPrincipal.removeAll();
        panellPrincipal.repaint();
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        int y = 0;
        gc.gridx = 0;
        for (JLabel l : dataLabel) {
            gc.gridy = y;
            panellPrincipal.add(l, gc);
            y++;
        }
        gc.gridx = 1;
        y = 0;
        for(JComponent c : dataTarget) {
            gc.gridy = y;
            if (c instanceof JTextField) {
                JTextField text = (JTextField) c;
                panellPrincipal.add(text, gc);
            } else if (c instanceof JComboBox) {
                JComboBox jcbb = (JComboBox) c;
                panellPrincipal.add(jcbb, gc);
            }
            else {
                JTextArea text = (JTextArea) c;
                panellPrincipal.add(text, gc);
            }
            y++;
        }
        gc.gridx = 0;
        gc.gridy = y + 1;
        gc.gridwidth = 2;
        panellPrincipal.add(editZone, gc);
    }

}
