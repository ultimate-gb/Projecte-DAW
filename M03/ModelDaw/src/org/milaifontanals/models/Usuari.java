/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.models;

import java.sql.Date;

/**
 *
 * @author Usuari
 */
public class Usuari {
    private int id;
    private String email;
    private String nom;
    private String cognoms;
    private Date data_naix;
    private char genere;
    private String telefon;
    private boolean bloquejat;
    private int role;
    private Nacionalitat nacionalitat;
    private String token;
    private boolean validat;

    public Usuari(int id, String email, String nom, String cognoms, Date data_naix, char genere, boolean bloquejat, int role, Nacionalitat nacionalitat, String token, boolean validat) {
        setId(id);
        setEmail(email);
        setNom(nom);
        setCognoms(cognoms);
        setData_naix(data_naix);
        setGenere(genere);
        setBloquejat(bloquejat);
        setRole(role);
        setNacionalitat(nacionalitat);
        setToken(token);
        setValidat(validat);
    }

    public Usuari() {
        setId(-1);
        setEmail("prova@gbalsells.home");
        setNom("Usuari");
        setCognoms("Usuari Usuari");
        Date data = new Date(2000-1900,04,05);
        setData_naix(data);
        setTelefon(null);
        setGenere('I');
        setBloquejat(false);
        setRole(0);
        setNacionalitat(new Nacionalitat("ESP", "Spanish"));
        setToken("f92374c5657eebed008ffd68d6567864");
        setValidat(false);
        
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isValidat() {
        return validat;
    }

    public void setValidat(boolean validat) {
        this.validat = validat;
    }

    public Nacionalitat getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(Nacionalitat nacionalitat) {
        this.nacionalitat = nacionalitat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.length() == 0 || email.length() > 320) { 
            throw new RuntimeException("El camp email no pot superar els 320 caracters i no pot ser cadena buida o null");
        }
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom == null || nom.length() == 0 || nom.length() > 40) {
            throw new RuntimeException("El camp nom no pot superar els 40 caracters i no pot ser cadena buida o null");
        }
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        if(cognoms == null || cognoms.length() == 0 || cognoms.length() > 100) {
            throw new RuntimeException("El camp cognoms no pot superar els 100 caracters i no pot ser cadena buida o null");
        }
        this.cognoms = cognoms;
    }

    public Date getData_naix() {
        return data_naix;
    }

    public void setData_naix(Date data_naix) {
        java.util.Date today = new java.util.Date();
        if(data_naix == null || data_naix.compareTo(today) >= 0 || (today.getYear() - data_naix.getYear()) < 18) {
            throw new RuntimeException("El camp data_naix no pot null ni pot ser la data actual, ha de ser una data de naixement amb la que sigui major de edat");
        }
        this.data_naix = data_naix;
    }

    public char getGenere() {
        return genere;
    }

    public void setGenere(char genere) {
        if(genere != 'D' && genere != 'H' && genere != 'I') {
            throw new RuntimeException();
        }
        this.genere = genere;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        if(telefon != null && telefon.matches("^\\+?\\d+$") && telefon.length() >= 20){
            this.telefon = telefon;
        }
        this.telefon = telefon;
        
    }

    public boolean isBloquejat() {
        return bloquejat;
    }

    public void setBloquejat(boolean bloquejat) {
        this.bloquejat = bloquejat;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return  nom + " " + cognoms;
    }
}
