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
public class Calendari {
    private int id;
    private String nom;
    private Timestamp data_creacio;
    private Usuari user;

    public Calendari(int id, String nom, Timestamp data_creacio, Usuari user) {
        setId(id);
        setNom(nom);
        setDataCreacio(data_creacio);
        setUser(user);
    }

    public Calendari() {
        setId(-1);
        setNom("Prova");
        java.util.Date today = new java.util.Date();
        Timestamp data = new Timestamp(today.getTime());
        setDataCreacio(data);
        setUser(new Usuari());
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
        if(nom.length() > 250) {
            throw new RuntimeException("Longitud del nom del tipus de activitat no pot superar els 250 caracters");
        }
        this.nom = nom;
    }

    public Timestamp getDataCreacio() {
        return data_creacio;
    }

    public void setDataCreacio(Timestamp data_creacio) {
        this.data_creacio = data_creacio;
    }

    public Usuari getUser() {
        return user;
    }

    public void setUser(Usuari user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return nom + " - " + data_creacio;
    }
}
