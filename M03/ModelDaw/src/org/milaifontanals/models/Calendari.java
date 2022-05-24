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

    public Timestamp getData_creacio() {
        return data_creacio;
    }

    public void setData_creacio(Timestamp data_creacio) {
        this.data_creacio = data_creacio;
    }

    public Usuari getUser() {
        return user;
    }

    public void setUser(Usuari user) {
        this.user = user;
    }
}
