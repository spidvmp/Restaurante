package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.PlatoDetalleFragment;
import com.nicatec.restaurante.fragment.SeleccionaPlatoFragment;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;
import com.nicatec.restaurante.model.Plato;

/*
Saca el listado de todos los platos que tiene el restaurante para añadirlo al pedido de una mesa.
Me pasan la mesas desde donde se ha solicitado añadir un plato, y si seleccionan un plato, me tendran que enviar que plato han seleccionado
y se lo añado a la mesa que ya tengo
 */

public class SeleccionaPlatoActivity extends AppCompatActivity implements  SeleccionaPlatoFragment.SelectPlatoListener,
        PlatoDetalleActivity.PlatoDetalleListener{
    public static final String EXTRA_MESA = "EXTRA_MESA";
    public static final String EXTRA_PLATO_SELECCIONADO = "EXTRA_PLATO_SELECCIONADO";

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
        mPlatoIndex = getIntent().getIntExtra(EXTRA_PLATO_SELECCIONADO,-1);

        //compruebo si tengo EXTRA_PLATO_SELECCIONADO
        if ( mPlatoIndex != -1 ){
            //me hanpasado el plato,, eso es que vengo de seleccionarlo. grabo y me piro
            Log.v("SELECCIONAPLATOACTIVITY", "tengo plato, deberia guardar y marcharme");
            addEnLaMesaElPlato(getIntent().getIntExtra(EXTRA_PLATO_SELECCIONADO,-1));
        }

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


    @Override
    public void addEnLaMesaElPlato(int position) {
        //Me pasan el plato que han selecionado. Como tengo la mesa, he de añadirlo
        Plato plato = Carta.getsInstance().getPlato(position);
        //he de añadir el plato a la mesa
        Mesa mesa = Mesas.getInstance().getMesa(mMesaIndex);

        mesa.addPlato(plato);
        finish();
    }
}
