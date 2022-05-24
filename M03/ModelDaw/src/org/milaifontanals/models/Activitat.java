/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.models;

import java.sql.Timestamp;

/**
 *
 * @author Usuari
 */
public class Activitat {
    private int id;
    private String nom;
    private Timestamp date_inici;
    private Timestamp date_fi;
    private String descripcio;
    private Tipus_Activitat tipus;
    private Usuari user;
    private Calendari calendari;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom.length() > 250) {
            throw new RuntimeException("Longitud del nom del tipus de activitat no pot superar els 250 caracters");
        }
        this.nom = nom;
    }

    public Timestamp getDate_inici() {
        return date_inici;
    }

    public void setDate_inici(Timestamp date_inici) {
        if(date_inici.compareTo(new java.util.Date()) < 0) {
            throw new RuntimeException("La data Inici no pot ser anterior a la data actual");
        }
        this.date_inici = date_inici;
    }

    public Timestamp getDate_fi() {
        return date_fi;
    }

    public void setDate_fi(Timestamp date_fi) {
        if(date_fi.compareTo(new java.util.Date()) < 0) {
            throw new RuntimeException("La data Fi no pot ser anterior a la data actual");
        }
        if(date_fi.compareTo(date_inici) < 0) {
            throw new RuntimeException("La data Fi no pot ser anterior a la data d'inici.");
        }
        this.date_fi = date_fi;
    }

    public String getDescripcio() {
        if(nom.length() > 500) {
            throw new RuntimeException("Longitud de la descripcio del tipus de activitat no pot superar els 500 caracters");
        }
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Tipus_Activitat getTipus() {
        return tipus;
    }

    public void setTipus(Tipus_Activitat tipus) {
        this.tipus = tipus;
    }

    public Usuari getUser() {
        return user;
    }

    public void setUser(Usuari user) {
        this.user = user;
    }

    public Calendari getCalendari() {
        return calendari;
    }

    public void setCalendari(Calendari calendari) {
        this.calendari = calendari;
    }
}
