/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.iface;

import org.milaifontanals.models.Activitat;
import org.milaifontanals.models.Calendari;
import org.milaifontanals.models.Usuari;

/**
 *
 * @author Usuari
 */
public interface IBaseDeDades {
    public void updateUser(Usuari user);
    public Usuari getUser(String email);
    public Usuari[] cercaUserByEmail(String email);
    public Usuari[] cercaByNomCognom(String nom, String cognom);
    public Calendari[] cercaCalendariPropietari(Usuari user);
    public Calendari[] cercaCalendariAjudant(Usuari user);
    public void insertActivitat(Activitat act);
    public void updateActivitat (Activitat act);
    public void deleteActivitat(Activitat act);
    
    public void aplicarCanvis() throws ProjecteDawException;
    public void desfer() throws ProjecteDawException;
    public void tancarConnexio() throws ProjecteDawException;
}
