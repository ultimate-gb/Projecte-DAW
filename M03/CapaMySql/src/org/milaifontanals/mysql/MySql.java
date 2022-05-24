/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.mysql;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.milaifontanals.iface.IBaseDeDades;
import org.milaifontanals.iface.ProjecteDawException;
import org.milaifontanals.models.Activitat;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.Usuari;

/**
 *
 * @author Usuari
 */
public class MySql implements IBaseDeDades {

    private Connection con = null;

    public void MySql() throws ProjecteDawException {
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
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, usu, pwd);

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
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuari[] cercaUserByEmail(String email) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuari[] cercaByNomCognom(String nom, String cognom) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Calendari[] cercaCalendariPropietari(Usuari user) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Calendari[] cercaCalendariAjudant(Usuari user) throws ProjecteDawException {
        throw new ProjecteDawException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
