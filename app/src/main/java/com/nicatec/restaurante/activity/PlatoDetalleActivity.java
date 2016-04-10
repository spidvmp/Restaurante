package com.nicatec.restaurante.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nicatec.restaurante.R;

/*
Se muestra el detalle del plato. Se llega o pulsando desde el listado de platos que se han pedido en una mesa o dede la seleccion de un nuevo plato
Se pone la opcion de añadir el plato al pedido de la mesa siempre que se haya llegado desde la seleccion del plato
DE cualquier otra forma, se muestra la opcion de poner los comentarios del camarero
 */

public class PlatoDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       */
    }

}
