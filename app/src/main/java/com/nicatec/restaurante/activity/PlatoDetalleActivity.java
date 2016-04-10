package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.PlatoDetalleFragment;

/*
Se muestra el detalle del plato. Se llega o pulsando desde el listado de platos que se han pedido en una mesa o dede la seleccion de un nuevo plato
Se pone la opcion de añadir el plato al pedido de la mesa siempre que se haya llegado desde la seleccion del plato
DE cualquier otra forma, se muestra la opcion de poner los comentarios del camarero
 */

public class PlatoDetalleActivity extends AppCompatActivity implements PlatoDetalleFragment.PlatoDetalleListener{

    public static final String EXTRA_PLATO_INDEX = "EXTRA_PLATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Añado el fragmen, compruebo que no exista ya
        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_plato_detalle) == null) {

            //extraigo el parametro que me han pasado, se lo paso al fragment para que saque el plato que tiene que mostrar
            int index = getIntent().getIntExtra(EXTRA_PLATO_INDEX, 0);
            fm.beginTransaction()
                    .add(R.id.fragment_plato_detalle, PlatoDetalleFragment.newInstance(index))
                    .commit();
        }

    }

}
