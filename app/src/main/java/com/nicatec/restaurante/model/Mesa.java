package com.nicatec.restaurante.model;

import java.util.LinkedList;

/**
 * Created by vtx on 5/4/16.
 * esta clase tiene el numero de mesa y un array con los platos pedidos por los clientes.
 */
public class Mesa {


    private Integer mNumero;
    private LinkedList<Plato> mPlatos;
    private Boolean mLaMesaEstaVacia;

    public Mesa(Integer numero) {
        this.mPlatos = new LinkedList<Plato>();
        this.mNumero = numero;
        this.mLaMesaEstaVacia = true;
    }


    public void addPlato(Plato plato){
        //se añade un plato a la lista pedida en la mesa
        mPlatos.add(plato);
        mLaMesaEstaVacia = false;
    }

    public Boolean estaVacia(){
        return mLaMesaEstaVacia;
    }

    public Plato getPlatoAtIndex(Integer index) {
        //devuelve un plato pedido que este en la posicion index
        if ( index > mPlatos.size() ){
            return null;
        } else {
            return mPlatos.get(index);
        }
    }

    public float laDolorosa() {
        //me recorro todos los platos que han pedido, los sumo y devuelvo el valor
        float precio = 0.0f;
        for (Integer i=0; i<= mPlatos.size(); i++)
            //voy sumando los precios
            precio += mPlatos.get(i).getmPrecio();

        //sesupone que se han ido, vacio la mesa
        limpiarMesa();

        return precio;
    }

    private void limpiarMesa(){
        //se supone que han pagado, vacio la mesa, solo tengo que vaciar el array, el numeor de mesa se queda igual
        mLaMesaEstaVacia = true;
        mPlatos.clear();
    }

    @Override
    public String toString() {
        return String.format("Mesa %d", mNumero);
    }

    public Boolean getmLaMesaEstaVacia() {
        return mLaMesaEstaVacia;
    }

    public void setmLaMesaEstaVacia(Boolean mLaMesaEstaVacia) {
        this.mLaMesaEstaVacia = mLaMesaEstaVacia;
    }

    public Integer getmNumero() {
        return mNumero;
    }

    public void setmNumero(Integer mNumero) {
        this.mNumero = mNumero;
    }

    public LinkedList<Plato> getmPlatos() {
        return mPlatos;
    }

    public void setmPlatos(LinkedList<Plato> mPlatos) {
        this.mPlatos = mPlatos;
    }
}
