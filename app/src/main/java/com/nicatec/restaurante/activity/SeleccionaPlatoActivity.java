package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.SeleccionaPlatoFragment;

/*
Saca el listado de todos los platos que tiene el restaurante para añadirlo al pedido de una mesa.
Me pasan la mesas desde donde se ha solicitado añadir un plato, y si seleccionan un plato, me tendran que enviar que plato han seleccionado
y se lo añado a la mesa que ya tengo
 */

public class SeleccionaPlatoActivity extends AppCompatActivity implements  SeleccionaPlatoFragment.SelectPlatoListener{
    public static final String EXTRA_MESA = "EXTRA_MESA";

    //me pasan el indice de la mesa desde donde han seleccionado nuevo plato,
    private int mMesaIndex;
    //me psasn tambien el indice del plato seleccionado
    private int mPlatoIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_plato);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.carta_title);

        //me quedo con la mesa que usare mas adelante
        mMesaIndex = getIntent().getIntExtra(EXTRA_MESA,0);


        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_selecciona_plato) == null){
            fm.beginTransaction()
                    .add(R.id.fragment_selecciona_plato, new SeleccionaPlatoFragment())
                    .commit();
        }

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

    @Override
    public void onPlatoSelected(int position) {
        //han selecionado un plato. Lo manodo a otra actividad, para que muestre el detalle del plato
        //me lo guardo y luego lo usare cuando acepten el plato
        mPlatoIndex = position;

        //saco la pantall del detalle del plato, donde apaece toda la informacion
        Intent intent = new Intent(this, PlatoDetalleActivity.class);
        intent.putExtra(PlatoDetalleActivity.EXTRA_PLATO_INDEX, position);
        startActivity(intent);

    }
}
