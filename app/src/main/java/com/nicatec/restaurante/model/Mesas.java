package com.nicatec.restaurante.model;

import java.util.LinkedList;

/**
 * Created by vtx on 8/4/16.
 * Modelo de las mesas del restaurante, es un array de MESA
 * Defino un numero x de mesas que tiene el restaurante en NUMERO_DE_MESAS
 * Lo reo como singleton ya que se va a tenr que actualizar los datos desde muchos sitios y solo tiene que haber una insancia de este modelo
 */
public class Mesas {
    private static final int NUMERO_DE_MESAS = 15;
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
        for (int i=0; i<=NUMERO_DE_MESAS ; i++){
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

    public void addPlatoEnIndexMesa(Plato plato, int indexMesa) {
        //me pasan un plato y se lo tengo que añadir a una mesa que viene referenciada por su posicion.
        mMesas.get(indexMesa).addPlato(plato);
    }
}
