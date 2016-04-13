package com.nicatec.restaurante.model;

import java.util.LinkedList;

/**
 * Created by vtx on 5/4/16.
 * Esta clase define un plato, nombre, precio, etc
 *
 */
public class Plato {
    private String mNombre;
    private float mPrecio;
    private String mFoto;
    private LinkedList<String> mAlergias;

    public Plato(String nombre, float precio, String foto) {
        this.mNombre = nombre;
        this.mPrecio = precio;
        this.mFoto = foto;
        //this.mComentario = comentario;
        //Las variantes se modificaran en el pedio, asi que se inicializa en blanco
        this.mAlergias = new LinkedList<String>();
    }

    public void addAlergia(String alergia){
        //me pasan una alergia y la añado al array de alergias
        this.mAlergias.add(alergia);
    }

    public String getNombre() {
        return mNombre;
    }

    public float getPrecio() {
        return mPrecio;
    }
    public String getPrecioString() { return String.format("%.2f€",mPrecio);}

    public String getFoto() {
        return mFoto;
    }
    public String toString() { return mNombre; }

    public LinkedList<String> getmAlergias() {
        return mAlergias;
    }

}

