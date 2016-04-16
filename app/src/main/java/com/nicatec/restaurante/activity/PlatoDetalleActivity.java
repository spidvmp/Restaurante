package com.nicatec.restaurante.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.PlatoDetalleFragment;

/*
Se muestra el detalle del plato. Se llega o pulsando desde el listado de platos que se han pedido en una mesa o dede la seleccion de un nuevo plato
Se pone la opcion de añadir el plato al pedido de la mesa siempre que se haya llegado desde la seleccion del plato
DE cualquier otra forma, se muestra la opcion de poner los comentarios del camarero
 */

public class PlatoDetalleActivity extends AppCompatActivity implements PlatoDetalleFragment.PlatoDetalleListener{

    public static final String EXTRA_PLATO_INDEX = "EXTRA_PLATO";
    public static final String EXTRA_MESA_INDEX = "EXTRA_MESA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Añado el fragmen, compruebo que no exista ya
        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_plato_detalle) == null) {

            //extraigo el parametro que me han pasado, se lo paso al fragment para que saque el plato que tiene que mostrar
            int indexPlato = getIntent().getIntExtra(EXTRA_PLATO_INDEX, 0);
            //Saco el parametro de la mesa, puede que no llegue nada, eso significa que es uno nuevo, si llega algo es que estan editando el plato de una mesa
            int indexMesa = getIntent().getIntExtra(EXTRA_MESA_INDEX, -1);
            fm.beginTransaction()
                    .add(R.id.fragment_plato_detalle, PlatoDetalleFragment.newInstance(indexPlato, indexMesa))
                    .commit();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            // Han pulsado la flecha de back de la Action Bar, finalizamos la actividad
            finish();

            return true;
        }

        return superValue;
    }


    @Override
    public void addPlatoALaMesa(int position,String comentario) {
        //estoy en una mesa y han seleccionado que quieren añadir un plato, me pasan el indice de la mesa donde añadir
        //he de pasarlo al SeleccionaPlatoActivity, para que añada el plato a la mesa
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SeleccionaPlatoActivity.EXTRA_PLATO_SELECCIONADO, position);
        returnIntent.putExtra(SeleccionaPlatoActivity.EXTRA_COMENTARIO_CAMARERO, comentario);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();


    }

    @Override
    public void termine() {
        finish();
    }

}
