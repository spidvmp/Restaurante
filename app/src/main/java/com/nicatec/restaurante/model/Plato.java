package com.nicatec.restaurante.model;

import java.util.LinkedList;

/**
 * Created by vtx on 5/4/16.
 * Esta clase define un plato, nombre, precio, etc
 */
public class Plato {
    private String mNombre;
    private float mPrecio;
    private String mFoto;
    private String mComentario;
    private String mVariantes;
    private LinkedList<String> mAlergias;

    public Plato(String nombre, float precio, String foto, String comentario) {
        this.mNombre = nombre;
        this.mPrecio = precio;
        this.mFoto = foto;
        this.mComentario = comentario;
        //Las variantes se modificaran en el pedio, asi que se inicializa en blanco
        this.mVariantes = "";
        this.mAlergias = new LinkedList<String>();
    }

    public void addAlergia(String alergia){
        //me pasan una alergia y la añado al array de alergias
        this.mAlergias.add(alergia);
    }

    public String getmNombre() {
        return mNombre;
    }

    public float getmPrecio() {
        return mPrecio;
    }

    public String getmFoto() {
        return mFoto;
    }

    public String getmVariantes() {
        return mVariantes;
    }

    public LinkedList<String> getmAlergias() {
        return mAlergias;
    }

    public void variantes(String variantes) {
        mVariantes = variantes;
    }
}

