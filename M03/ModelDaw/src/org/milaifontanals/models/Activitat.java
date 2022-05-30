/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.models;

import java.sql.Date;
import java.sql.Time;
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
    private TipusActivitat tipus;
    private Usuari user;
    private Calendari calendari;
    private boolean publicada;

    public Activitat(int id, String nom, Timestamp date_inici, Timestamp date_fi, String descripcio, TipusActivitat tipus, Usuari user, Calendari calendari, boolean publicada) {
        setId(id);
        setNom(nom);
        setDateInici(date_inici);
        setDateFi(date_fi);
        setDescripcio(descripcio);
        setTipus(tipus);
        setUser(user);
        setCalendari(calendari);
        setPublicada(publicada);
    }

    public Activitat() {
        setId(-1);
        setNom("Prova");
        java.util.Date today = new java.util.Date();
        Timestamp data = new Timestamp(today.getTime());
        setDateInici(data);
        setDateFi(null);
        setDescripcio("Una prova");
        setTipus(new TipusActivitat(-1,"Prova", new Usuari()));
        setUser(new Usuari());
        setCalendari(new Calendari());
        setPublicada(false);
    }

    public boolean isPublicada() {
        return publicada;
    }

    public void setPublicada(boolean publicada) {
        this.publicada = publicada;
    }

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
        if (nom.length() > 250) {
            throw new RuntimeException("Longitud del nom del tipus de activitat no pot superar els 250 caracters");
        }
        this.nom = nom;
    }

    public Timestamp getDateInici() {
        return date_inici;
    }

    public void setDateInici(Timestamp date_inici) {
        this.date_inici = date_inici;
    }

    public Timestamp getDateFi() {
        return date_fi;
    }

    public void setDateFi(Timestamp date_fi) {
        if (date_fi != null && date_fi.compareTo(date_inici) < 0) {
            throw new RuntimeException("La data Fi no pot ser anterior a la data d'inici.");
        }
        this.date_fi = date_fi;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        if (descripcio != null && descripcio.length() > 500) {
            throw new RuntimeException("Longitud de la descripcio del tipus de activitat no pot superar els 500 caracters");
        }
        this.descripcio = descripcio;
    }

    public TipusActivitat getTipus() {
        return tipus;
    }

    public void setTipus(TipusActivitat tipus) {
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
