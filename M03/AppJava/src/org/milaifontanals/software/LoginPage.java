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
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Activitat;

/**
 *
 * @author Usuari
 */
public class LoginPage {

    private JDialog loginPage;
    private JFrame appPage;
    private IBaseDeDades db;

    public LoginPage(JFrame appPage, IBaseDeDades db) {
        this.appPage = appPage;
        this.db = db;
        carregarLogin();
    }

    public void carregarLogin() {
        loginPage = new JDialog(appPage, "Login", true);
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
        JTextField emailField = new JTextField(50);
        panellPrincipal.add(emailField, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        panellPrincipal.add(new JLabel("Password Usuari: "), gc);
        gc.gridx = 1;
        gc.gridy = 1;
        JPasswordField passwdField = new JPasswordField(50);
        panellPrincipal.add(passwdField, gc);
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        JButton login = new JButton("Login");
        JPanel buttonZone = new JPanel();
        buttonZone.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonZone.add(login);
        panellPrincipal.add(buttonZone, gc);
        loginPage.setLayout(new FlowLayout());
        loginPage.add(panellPrincipal);
        loginPage.setType(Window.Type.NORMAL);
        loginPage.setLocationRelativeTo(null);
        loginPage.setResizable(false);
        loginPage.pack();
        loginPage.setVisible(true);
    }

}
