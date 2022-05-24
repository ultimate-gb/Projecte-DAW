/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.models;

/**
 *
 * @author Usuari
 */
public class Tipus_Activitat {
    private int codi;
    private String nom;
    private Usuari user;

    public Usuari getUser() {
        return user;
    }

    public void setUser(Usuari user) {
        this.user = user;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
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
    
    
}
