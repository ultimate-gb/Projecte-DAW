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
    public void updateUser(Usuari user) throws ProjecteDawException;
    public Usuari getUser(String email) throws ProjecteDawException;
    public Usuari[] cercaUserByEmail(String email) throws ProjecteDawException;
    public Usuari[] cercaByNomCognom(String nom, String cognom) throws ProjecteDawException;
    public Calendari[] cercaCalendariPropietari(Usuari user) throws ProjecteDawException;
    public Calendari[] cercaCalendariAjudant(Usuari user) throws ProjecteDawException;
    public void insertActivitat(Activitat act) throws ProjecteDawException;
    public void updateActivitat (Activitat act) throws ProjecteDawException;
    public void deleteActivitat(Activitat act) throws ProjecteDawException;
    
    public void aplicarCanvis() throws ProjecteDawException;
    public void desfer() throws ProjecteDawException;
    public void tancarConnexio() throws ProjecteDawException;
}
