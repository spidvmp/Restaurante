package com.nicatec.restaurante.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nicatec.restaurante.R;
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

public class SeleccionaPlatoActivity extends AppCompatActivity implements  SeleccionaPlatoFragment.SelectPlatoListener {
    public static final String EXTRA_MESA = "EXTRA_MESA";
    public static final String EXTRA_PLATO_SELECCIONADO = "EXTRA_PLATO_SELECCIONADO";
    static final int PLATO_SELECCIONADO = 1;

    //me pasan el indice de la mesa desde donde han seleccionado nuevo plato,
    private int mMesaIndex;


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

    }

    @Override
    public void onPlatoSelected(int position) {
        //han selecionado un plato, llega del fragment. Lo mando a la  actividad PlatoDEtalleActivity, para que muestre el detalle del plato

        //saco la pantall del detalle del plato, donde apaece toda la informacion
        Intent intent = new Intent(this, PlatoDetalleActivity.class);
        intent.putExtra(PlatoDetalleActivity.EXTRA_PLATO_INDEX, position);
        startActivityForResult(intent, PLATO_SELECCIONADO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //se supoen que aqui recibimos infromacion del plato selecionado
        if ( requestCode == PLATO_SELECCIONADO) {
            //nos llega desde PlatoDetalleActivity
            if ( resultCode == Activity.RESULT_OK) {
                //tenemos los datos corrector, los saco de data
                int platoIndex = data.getIntExtra(EXTRA_PLATO_SELECCIONADO,0);
                Plato plato = Carta.getsInstance().getPlato(platoIndex);
                //he de añadir el plato a la mesa
                Mesa mesa = Mesas.getInstance().getMesa(mMesaIndex);

                mesa.addPlato(plato);


            }
        }
    }

}
