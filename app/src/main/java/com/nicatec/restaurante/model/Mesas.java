package com.nicatec.restaurante.model;

import java.util.LinkedList;

/**
 * Created by vtx on 8/4/16.
 */
public class Mesas {

    //genero un singleton que es un array de las mesas
    private static Mesas sInstance = null;
    private LinkedList<Mesa> mMesas = null;

    public static Mesas getInstance() {
        if ( sInstance == null ) {
            sInstance = new Mesas();
        }

        return sInstance;
    }

    //constructor
    public Mesas () {
        mMesas = new LinkedList<Mesa>();
        //genero por ejemplo 15 posibles mesas de mi restaurante
        for (int i=0; i<=14 ; i++){
            mMesas.add(new Mesa(i+1));
        }

    }
    public Mesa getMesa(int index) {
        return mMesas.get(index);
    }

    public int getMesasConut(){
        return mMesas.size();
    }

    public LinkedList<Mesa> getMesas(){
        return mMesas;
    }
}
