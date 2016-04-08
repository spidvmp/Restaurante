package com.nicatec.restaurante.model;

import java.math.MathContext;
import java.util.LinkedList;

/**
 * Created by vtx on 8/4/16.
 */
public class Carta {

    //genero un singleton con todos los platos que tengo
    private static Carta sInstance = null;
    private LinkedList<Plato> mCarta = null;

    public static Carta getsInstance() {
        if ( sInstance == null ) {
            sInstance = new Carta();
        }

        return sInstance;
    }

    //constructor
    public Carta() {
        mCarta = new LinkedList<Plato>();

    }

    public Plato getPlato(int index){
        //me devuelve el plato que este enla posicion x
        return mCarta.get(index);
    }

    public int getCartaCount(){
        return  mCarta.size();
    }

}
