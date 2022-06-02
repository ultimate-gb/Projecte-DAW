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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
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

    public JDialog activitatPage;
    private JDialog calendarPage;
    private IBaseDeDades db;
    private Activitat act;
    private Calendari cal;
    private Usuari user;
    private CalendarPage calendariPage;
    private DefaultTableModel modelTaula;
    private int selectedRowTable;
    private ArrayList<Activitat> actList;

    public ActivitatPage(JDialog calendarPage, IBaseDeDades db, Activitat act, Calendari cal, Usuari user, int tipus, CalendarPage calendariPage, DefaultTableModel modelTaula, int selectedRowTable, ArrayList<Activitat> actList) {
        this.calendarPage = calendarPage;
        this.db = db;
        this.act = act;
        this.cal = cal;
        this.user = user;
        this.calendariPage = calendariPage;
        this.modelTaula = modelTaula;
        this.selectedRowTable = selectedRowTable;
        this.actList = actList;
        if (act.isPublicada() != true) {
            if (tipus == 0) {
                carregarVistaAfegir();
            } else if (tipus == 1) {
                carregarVistaModificar();
            }
        }
        else {
            JOptionPane.showMessageDialog(activitatPage, "Error: No pots editar una activitat publicada", "Error En Obrir Editar Activitat", JOptionPane.ERROR_MESSAGE);
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
        nomT.setName("nom");
        dataTarget.add(nomT);
        dataLabel.add(new JLabel("Data Inici: "));
        JTextField dataIniciT = new JTextField(15);
        dataIniciT.setText(act.getDateInici().toString());
        dataIniciT.setName("dataInici");
        dataTarget.add(dataIniciT);
        dataLabel.add(new JLabel("Data Final: "));
        JTextField dataFiT = new JTextField(15);
        if (act.getDateFi() == null) {
            dataFiT.setText("");
        } else {
            dataFiT.setText(act.getDateFi().toString());
        }
        dataFiT.setName("dataFi");
        dataTarget.add(dataFiT);
        dataLabel.add(new JLabel("Descripcio: "));
        JTextArea descT = new JTextArea(25, 50);
        descT.setText(act.getDescripcio());
        descT.setName("desc");
        dataTarget.add(descT);
        dataLabel.add(new JLabel("Tipus Activitat: "));
        JComboBox<TipusActivitat> tp = new JComboBox();
        try {
            ArrayList<TipusActivitat> tpList = db.getTipusActivitats(cal.getUser());
            int i = 0;
            int selectedIndex = -1;
            for (TipusActivitat tip : tpList) {
                tp.addItem(tip);
                if (tip.getCodi() == act.getTipus().getCodi()) {
                    selectedIndex = i;
                }
                i++;
            }
            tp.setSelectedIndex(selectedIndex);

        } catch (ProjecteDawException ex) {
            JOptionPane.showMessageDialog(activitatPage, "Error en Obrir Editar Activitat", "Error En Obrir Editar Activitat", JOptionPane.ERROR_MESSAGE);
        }
        tp.setName("tipusAct");
        dataTarget.add(tp);
        JPanel editZone = new JPanel();
        editZone.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton guardarBtn = new JButton("Guardar");
        editZone.add(guardarBtn);
        guardarBtn.addActionListener(new EditarActivitat(dataLabel, dataTarget, cal, user, act));
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
        nomT.setName("nom");
        dataTarget.add(nomT);
        dataLabel.add(new JLabel("Data Inici: "));
        JTextField dataIniciT = new JTextField(15);
        dataIniciT.setName("dataInici");
        dataTarget.add(dataIniciT);
        dataLabel.add(new JLabel("Data Final: "));
        JTextField dataFiT = new JTextField(15);
        dataFiT.setName("dataFi");
        dataTarget.add(dataFiT);
        dataLabel.add(new JLabel("Descripcio: "));
        JTextArea descT = new JTextArea(25, 50);
        descT.setName("desc");
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
        tp.setName("tipusAct");
        dataTarget.add(tp);
        JPanel editZone = new JPanel();
        editZone.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(new InserirActivitat(dataLabel, dataTarget, cal, user));
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
        activitatPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        for (JComponent c : dataTarget) {
            gc.gridy = y;
            if (c instanceof JTextField) {
                JTextField text = (JTextField) c;
                panellPrincipal.add(text, gc);
            } else if (c instanceof JComboBox) {
                JComboBox jcbb = (JComboBox) c;
                panellPrincipal.add(jcbb, gc);
            } else {
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

    class InserirActivitat implements ActionListener {

        private ArrayList<JLabel> dataLabel;
        private ArrayList<JComponent> dataTarget;
        private Calendari cal;
        private Usuari user;

        public InserirActivitat(ArrayList<JLabel> dataLabel, ArrayList<JComponent> dataTarget, Calendari cal, Usuari user) {
            this.dataLabel = dataLabel;
            this.dataTarget = dataTarget;
            this.cal = cal;
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String campsInvalids = "";
            Activitat act = new Activitat();
            int validades = 0;
            int errorsTrobats = 0;
            int i = 0;
            for (JComponent c : dataTarget) {
                if (c instanceof JTextField) {
                    JTextField text = (JTextField) c;
                    if (text.getName().equals("nom")) {
                        if (text.getText().length() <= 250) {
                            validades++;
                            act.setNom(text.getText());
                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "nom";
                        }

                    } else if (text.getName().equals("dataInici")) {
                        if (text.getText().matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}(\\.\\d)?$")) {
                            java.util.Date today = new java.util.Date();
                            Timestamp todayTmp = new Timestamp(today.getTime());
                            int year = Integer.parseInt(text.getText().substring(0, 4));
                            int mes = Integer.parseInt(text.getText().substring(5, 7));
                            int dia = Integer.parseInt(text.getText().substring(8, 10));
                            int hora = Integer.parseInt(text.getText().substring(11, 13));
                            int minuts = Integer.parseInt(text.getText().substring(14, 16));
                            int segons = Integer.parseInt(text.getText().substring(17, 19));
                            Timestamp dataInici = new Timestamp(year - 1900, mes - 1, dia, hora, minuts, segons, 0);
                            if (todayTmp.compareTo(dataInici) <= 0) {
                                validades++;
                                act.setDateInici(dataInici);
                            } else {
                                errorsTrobats++;
                                if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                    campsInvalids += ",\n";
                                }
                                campsInvalids += "Data Inici, la data ha de ser posterior a la actual";
                            }
                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "Data Inici(Format Ivalid Any-Mes-Dia Hora:minuts:segons)";
                        }

                    } else if (text.getName().equals("dataFi")) {
                        if (text.getText().length() == 0 || text.getText().matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}(\\.\\d)?$")) {
                            if (text.getText().length() != 0) {
                                java.util.Date today = new java.util.Date();
                                Timestamp todayTmp = new Timestamp(today.getTime());
                                int year = Integer.parseInt(text.getText().substring(0, 4));
                                int mes = Integer.parseInt(text.getText().substring(5, 7));
                                int dia = Integer.parseInt(text.getText().substring(8, 10));
                                int hora = Integer.parseInt(text.getText().substring(11, 13));
                                int minuts = Integer.parseInt(text.getText().substring(14, 16));
                                int segons = Integer.parseInt(text.getText().substring(17, 19));
                                Timestamp dataFi = new Timestamp(year - 1900, mes - 1, dia, hora, minuts, segons, 0);
                                System.out.println(dataFi.toString());
                                if (todayTmp.compareTo(dataFi) <= 0 && dataFi.compareTo(act.getDateInici()) >= 0) {
                                    validades++;
                                    act.setDateFi(dataFi);
                                } else {
                                    errorsTrobats++;
                                    if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                        campsInvalids += ",\n";
                                    }
                                    campsInvalids += "Data Fi, la data ha de ser posterior a la actual ni posterior a la de inici";
                                }
                            } else {
                                validades++;
                            }

                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "Data Fi(Format Ivalid Any-Mes-Dia Hora:minuts:segons)";
                        }

                    }
                } else if (c instanceof JComboBox) {
                    JComboBox jcbb = (JComboBox) c;
                    if (jcbb.getSelectedIndex() != -1) {
                        validades++;
                        TipusActivitat tp2 = (TipusActivitat) jcbb.getSelectedItem();
                        act.setTipus(tp2);
                    } else {
                        errorsTrobats++;
                        if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                            campsInvalids += ",\n";
                        }
                        campsInvalids += "Tipus Activitat, selecciona una activitat";
                    }
                } else {
                    JTextArea text = (JTextArea) c;
                    if (text.getName().equals("desc")) {
                        if (text.getText().length() == 0 || text.getText().length() <= 500) {
                            validades++;
                            act.setDescripcio(text.getText());
                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "Descripcio, No pot superar els 500 caracters";
                        }

                    }
                }
                i++;
            }
            if (validades < 5) {
                JOptionPane.showMessageDialog(activitatPage, "Dades Modificades Invalides.\nCamps invalids: " + campsInvalids, "Error Al Validar Les Dades", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                act.setCalendari(cal);
                act.setUser(user);
                if (db.insertActivitat(act) > 0) {
                    db.aplicarCanvis();
                    String dataFi = "";
                    if (act.getDateFi() == null) {
                        dataFi = "";
                    } else {
                        dataFi = act.getDateFi().toString();
                    }
                    String publicada = "";
                    if (act.isPublicada()) {
                        publicada = "Si";
                    } else {
                        publicada = "No";
                    }
                    actList.add(act);
                    modelTaula.addRow(new Object[]{act.getNom(), act.getDateInici().toString(), dataFi, act.getTipus(), publicada});
                    activitatPage.dispose();
                } else {
                    JOptionPane.showMessageDialog(activitatPage, "Error en Inserir.", "Error Al Inserir", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ProjecteDawException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(activitatPage, "Error en Inserir.", "Error en Inserir.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class EditarActivitat implements ActionListener {

        private ArrayList<JLabel> dataLabel;
        private ArrayList<JComponent> dataTarget;
        private Calendari cal;
        private Usuari user;
        private Activitat activitat;

        public EditarActivitat(ArrayList<JLabel> dataLabel, ArrayList<JComponent> dataTarget, Calendari cal, Usuari user, Activitat activitat) {
            this.dataLabel = dataLabel;
            this.dataTarget = dataTarget;
            this.cal = cal;
            this.user = user;
            this.activitat = activitat;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String campsInvalids = "";
            Activitat act = new Activitat();
            int validades = 0;
            int errorsTrobats = 0;
            int i = 0;
            for (JComponent c : dataTarget) {
                if (c instanceof JTextField) {
                    JTextField text = (JTextField) c;
                    if (text.getName().equals("nom")) {
                        if (text.getText().length() <= 250) {
                            validades++;
                            act.setNom(text.getText());
                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "nom";
                        }

                    } else if (text.getName().equals("dataInici")) {
                        if (text.getText().matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}(\\.\\d)?$")) {
                            java.util.Date today = new java.util.Date();
                            Timestamp todayTmp = new Timestamp(today.getTime());
                            int year = Integer.parseInt(text.getText().substring(0, 4));
                            int mes = Integer.parseInt(text.getText().substring(5, 7));
                            int dia = Integer.parseInt(text.getText().substring(8, 10));
                            int hora = Integer.parseInt(text.getText().substring(11, 13));
                            int minuts = Integer.parseInt(text.getText().substring(14, 16));
                            int segons = Integer.parseInt(text.getText().substring(17, 19));
                            Timestamp dataInici = new Timestamp(year - 1900, mes - 1, dia, hora, minuts, segons, 0);
                            validades++;
                            act.setDateInici(dataInici);
                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "Data Inici(Format Ivalid Any-Mes-Dia Hora:minuts:segons)";
                        }

                    } else if (text.getName().equals("dataFi")) {
                        if (text.getText().length() == 0 || text.getText().matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}(\\.\\d)?$")) {
                            if (text.getText().length() != 0) {
                                java.util.Date today = new java.util.Date();
                                Timestamp todayTmp = new Timestamp(today.getTime());
                                int year = Integer.parseInt(text.getText().substring(0, 4));
                                int mes = Integer.parseInt(text.getText().substring(5, 7));
                                int dia = Integer.parseInt(text.getText().substring(8, 10));
                                int hora = Integer.parseInt(text.getText().substring(11, 13));
                                int minuts = Integer.parseInt(text.getText().substring(14, 16));
                                int segons = Integer.parseInt(text.getText().substring(17, 19));
                                Timestamp dataFi = new Timestamp(year - 1900, mes - 1, dia, hora, minuts, segons, 0);
                                System.out.println(dataFi.toString());
                                if (dataFi.compareTo(act.getDateInici()) >= 0) {
                                    validades++;
                                    act.setDateFi(dataFi);
                                } else {
                                    errorsTrobats++;
                                    if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                        campsInvalids += ",\n";
                                    }
                                    campsInvalids += "Data Fi, la data no pot ser posterior a la de inici";
                                }
                            } else {
                                validades++;
                            }

                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "Data Fi(Format Ivalid Any-Mes-Dia Hora:minuts:segons)";
                        }

                    }
                } else if (c instanceof JComboBox) {
                    JComboBox jcbb = (JComboBox) c;
                    if (jcbb.getSelectedIndex() != -1) {
                        validades++;
                        TipusActivitat tp2 = (TipusActivitat) jcbb.getSelectedItem();
                        act.setTipus(tp2);
                    } else {
                        errorsTrobats++;
                        if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                            campsInvalids += ",\n";
                        }
                        campsInvalids += "Tipus Activitat, selecciona una activitat";
                    }
                } else {
                    JTextArea text = (JTextArea) c;
                    if (text.getName().equals("desc")) {
                        if (text.getText().length() == 0 || text.getText().length() <= 500) {
                            validades++;
                            act.setDescripcio(text.getText());
                        } else {
                            errorsTrobats++;
                            if (i < (dataTarget.size() - 1) && errorsTrobats > 1) {
                                campsInvalids += ",\n";
                            }
                            campsInvalids += "Descripcio, No pot superar els 500 caracters";
                        }

                    }
                }
                i++;
            }
            if (validades < 5) {
                JOptionPane.showMessageDialog(activitatPage, "Dades Modificades Invalides.\nCamps invalids: " + campsInvalids, "Error Al Validar Les Dades", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                act.setCalendari(cal);
                act.setUser(user);
                act.setId(activitat.getId());
                if (db.updateActivitat(act) > 0) {
                    db.aplicarCanvis();
                    modelTaula.setValueAt(act.getNom(), selectedRowTable, 0);
                    modelTaula.setValueAt(act.getDateInici().toString(), selectedRowTable, 1);
                    modelTaula.setValueAt(act.getDateFi().toString(), selectedRowTable, 2);
                    modelTaula.setValueAt(act.getTipus(), selectedRowTable, 3);
                    String publicada;
                    if (act.isPublicada()) {
                        publicada = "Si";
                    } else {
                        publicada = "No";
                    }
                    modelTaula.setValueAt(publicada, selectedRowTable, 4);
                    activitatPage.dispose();
                } else {
                    JOptionPane.showMessageDialog(activitatPage, "Error en Actualitzar.", "Error Al Actualitzar", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ProjecteDawException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(activitatPage, "Error en Actualitzar.", "Error en Actualitzar.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
