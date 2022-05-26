/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.bdr;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Activitat;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.Nacionalitat;
import org.milaifontanals.models.Tipus_Activitat;
import org.milaifontanals.models.Usuari;

/**
 *
 * @author Usuari
 */
public class MySql implements IBaseDeDades {

    private Connection con = null;

    public MySql() throws ProjecteDawException {
        Properties p = new Properties();
        try {
            p.load(new FileReader("connexio.properties"));
        } catch (IOException ex) {
            System.out.println("Problemes en carregar el fitxer de configuració");
            System.out.println("Més info: " + ex.getMessage());
            System.exit(1);
        }
        // p conté les propietats necessàries per la connexió
        String url = p.getProperty("url");
        String usu = p.getProperty("usuari");
        String pwd = p.getProperty("contrasenya");
        if (url == null || usu == null || pwd == null) {
            throw new ProjecteDawException("Manca alguna de les propietats: url, usuari, contrasenya");
        }
        // Ja tenim les 3 propietats
        // Podem intentar establir connexió
        try {
            this.con = DriverManager.getConnection(url, usu, pwd);
            this.con.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new ProjecteDawException("Problemes en intentar fer la connexio", (Throwable) ex);
        }
    }

    @Override
    public void updateUser(Usuari user) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuari getUser(String email) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select u.id, u.email, u.nom, u.cognoms, u.data_naix, u.genere, u.telefon, u.bloquejat, u.role, n.codi as 'NatCode', n.nom as 'Nacionalitat' from users u JOIN nacionalitat n ON n.codi=u.nacionalitat where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            Usuari user = null;
            if (rs.next()) {
                user = new Usuari(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("cognoms"), rs.getDate("data_naix"), rs.getString("genere").charAt(0), rs.getBoolean("bloquejat"), rs.getInt("role"), new Nacionalitat(rs.getString("NatCode"), rs.getString("Nacionalitat")));
            }
            return user;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari", (Throwable) ex);
        }
    }

    @Override
    public ArrayList<Usuari> getAllUsers() throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select u.id, u.email, u.nom, u.cognoms, u.data_naix, u.genere, u.telefon, u.bloquejat, u.role, n.codi as 'NatCode', n.nom as 'Nacionalitat' from users u JOIN nacionalitat n ON n.codi=u.nacionalitat");
            ResultSet rs = ps.executeQuery();
            ArrayList<Usuari> userList = new ArrayList();
            while (rs.next()) {
                userList.add(new Usuari(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("cognoms"), rs.getDate("data_naix"), rs.getString("genere").charAt(0), rs.getBoolean("bloquejat"), rs.getInt("role"), new Nacionalitat(rs.getString("NatCode"), rs.getString("Nacionalitat"))));
            }
            return userList;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per mail", (Throwable) ex);
        }
    }

    @Override
    public ArrayList<Usuari> cercaUserByEmail(String email) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select u.id, u.email, u.nom, u.cognoms, u.data_naix, u.genere, u.telefon, u.bloquejat, u.role, n.codi as 'NatCode', n.nom as 'Nacionalitat' from users u JOIN nacionalitat n ON n.codi=u.nacionalitat where email like CONCAT(CONCAT('%',?),'%')");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            ArrayList<Usuari> userList = new ArrayList();
            while (rs.next()) {
                userList.add(new Usuari(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("cognoms"), rs.getDate("data_naix"), rs.getString("genere").charAt(0), rs.getBoolean("bloquejat"), rs.getInt("role"), new Nacionalitat(rs.getString("NatCode"), rs.getString("Nacionalitat"))));
            }
            return userList;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per mail", (Throwable) ex);
        }
    }

    @Override
    public ArrayList<Usuari> cercaByNomCognom(String nom, String cognom) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select u.id, u.email, u.nom, u.cognoms, u.data_naix, u.genere, u.telefon, u.bloquejat, u.role, n.codi as 'NatCode', n.nom as 'Nacionalitat' from users u JOIN nacionalitat n ON n.codi=u.nacionalitat where u.nom like CONCAT(CONCAT('%',?),'%') and u.cognoms like CONCAT(CONCAT('%',?),'%')");
            ps.setString(1, nom);
            ps.setString(2, cognom);
            ResultSet rs = ps.executeQuery();
            ArrayList<Usuari> userList = new ArrayList();
            while (rs.next()) {
                userList.add(new Usuari(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("cognoms"), rs.getDate("data_naix"), rs.getString("genere").charAt(0), rs.getBoolean("bloquejat"), rs.getInt("role"), new Nacionalitat(rs.getString("NatCode"), rs.getString("Nacionalitat"))));
            }
            return userList;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per nom cognom", (Throwable) ex);
        }
    }

    @Override
    public ArrayList<Calendari> cercaCalendariPropietari(Usuari user) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select id, nom, data_creacio from calendari WHERE user = ?");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Calendari> calList = new ArrayList();
            while (rs.next()) {
                calList.add(new Calendari(rs.getInt("id"), rs.getString("nom"), rs.getTimestamp("data_creacio"), user));
            }
            return calList;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per nom cognom", (Throwable) ex);
        }
    }

    @Override
    public ArrayList<Calendari> cercaCalendariAjudant(Usuari user) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select id, nom, data_creacio from calendari c RIGHT JOIN ajuda a ON a.calendari = c.id WHERE a.user = ?");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Calendari> calList = new ArrayList();
            while (rs.next()) {
                calList.add(new Calendari(rs.getInt("id"), rs.getString("nom"), rs.getTimestamp("data_creacio"), user));
            }
            return calList;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per nom cognom", (Throwable) ex);
        }
    }

    @Override
    public void insertActivitat(Activitat act) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateActivitat(Activitat act) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteActivitat(Activitat act) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aplicarCanvis() throws ProjecteDawException {
        try {
            con.commit();
        } catch (SQLException ex) {
            throw new ProjecteDawException("Error en validar els canvis", ex);
        }
    }

    @Override
    public void desfer() throws ProjecteDawException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new ProjecteDawException("Error en desfer els canvis", ex);
        }
    }

    @Override
    public void tancarConnexio() throws ProjecteDawException {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            throw new ProjecteDawException("Error en tancar la capa", ex);
        }
    }

    @Override
    public boolean esPropietariCalendari(Usuari user, Calendari calendari) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select 1 from calendari WHERE user = ? and id = ?");
            ps.setInt(1, user.getId());
            ps.setInt(2, calendari.getId());
            ResultSet rs = ps.executeQuery();
            boolean esPropietari = false;
            if (rs.next()) {
                esPropietari = true;
            }
            return esPropietari;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per nom cognom", (Throwable) ex);
        }
    }

    @Override
    public boolean esAjudantCalendari(Usuari user, Calendari calendari) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select 1 from ajuda WHERE user = ? and calendari = ?");
            ps.setInt(1, user.getId());
            ps.setInt(2, calendari.getId());
            ResultSet rs = ps.executeQuery();
            boolean esAjudant = false;
            if (rs.next()) {
                esAjudant = true;
            }
            return esAjudant;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut cercar el usuari per nom cognom", (Throwable) ex);
        }
    }

    @Override
    public ArrayList<Activitat> getActivitatsCalendari(Calendari calendari, Usuari user) throws ProjecteDawException {
        try {
            PreparedStatement ps = con.prepareStatement("select a.id, a.nom, a.data_inici, a.data_fi, a.descripcio, t.codi as 'Codi Act', t.nom as 'Nom Act', a.publicada from activitat a JOIN tipus_activitat t ON t.codi = a.tipus Where a.user = ? and a.calendari = ?");
            ps.setInt(1, user.getId());
            ps.setInt(2, calendari.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Activitat> activityList = new ArrayList();
            while (rs.next()) {
                activityList.add(new Activitat(rs.getInt("id"), rs.getString("nom"), rs.getTimestamp("data_inici"), rs.getTimestamp("data_fi"), rs.getString("descripcio"), new Tipus_Activitat(rs.getInt("Codi Act"), rs.getString("Nom Act"), user), user, calendari, rs.getBoolean("publicada")));
            }
            return activityList;
        } catch (SQLException ex) {
            throw new ProjecteDawException("No s'ha pogut obtenir les activitats del usuari en el calendari " + calendari.getNom(), (Throwable) ex);
        }
    }

}