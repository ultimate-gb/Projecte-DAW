/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.models;

/**
 *
 * @author Gerard Balsells
 */
public class Nacionalitat {
    private String codi;
    private String nom;

    public Nacionalitat(String codi, String nom) {
        this.codi = codi;
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        if(codi == null || codi.length() != 3) {
            throw new RuntimeException("El codi es obligatori i de mida obligatoria de 3 caracters");
        }
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom == null || nom.length() > 250) {
            throw new RuntimeException("El nom es obligatori i no pot superar la mesura de 250 caracters");
        }
        this.nom = nom;
    }
    
    
}
