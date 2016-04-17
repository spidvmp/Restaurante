package com.nicatec.restaurante.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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

public class SeleccionaPlatoActivity extends AppCompatActivity implements  SeleccionaPlatoFragment.SelectPlatoListener, PlatoDetalleFragment.PlatoDetalleListener {
    public static final String EXTRA_MESA = "EXTRA_MESA";
    public static final String EXTRA_PLATO_SELECCIONADO = "EXTRA_PLATO_SELECCIONADO";
    public static final String EXTRA_COMENTARIO_CAMARERO = "EXTRA_COMENTARIO_CAMARERO";
    static final int PLATO_SELECCIONADO = 1;

    //me pasan el indice de la mesa desde donde han seleccionado nuevo plato,
    private int mMesaIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_plato);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //me quedo con la mesa que usare mas adelante
        mMesaIndex = getIntent().getIntExtra(EXTRA_MESA,0);

        FragmentManager fm = getFragmentManager();
        //selecciona plato siempre tiene que aparecer, asi que no comprobamos si existe, so si ya esta creado
        if ( fm.findFragmentById(R.id.fragment_selecciona_plato) == null){
            fm.beginTransaction()
                    .add(R.id.fragment_selecciona_plato, new SeleccionaPlatoFragment())
                    .commit();

        }
        //comprobamos si tenemos hueco para el  platoDetalle
        if ( findViewById(R.id.fragment_plato_detalle) != null ){
            //tenemos el fragment de l platodetalle, es una tablet, comprobamos si ya esta cargado
            if ( fm.findFragmentById(R.id.fragment_plato_detalle) == null) {
                //no lo esta, lo cargo y hay que tomarlo como plato nuevo, asi que no le paso la mesa
                fm.beginTransaction()
                        .add(R.id.fragment_plato_detalle, PlatoDetalleFragment.newInstance(0,-1))
                        .commit();
            }


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
                //al plato seleccionado le añado el comentario del camarero
                plato.setCamarero(data.getStringExtra(EXTRA_COMENTARIO_CAMARERO));
                //he de añadir el plato a la mesa
                Mesa mesa = Mesas.getInstance().getMesa(mMesaIndex);

                mesa.addPlato(plato);


            }
        }
    }

    @Override
    public void addPlatoALaMesa(int position, String comentario) {
        Log.v("SELECCIONAPLATOACT", "añaden plato a la mesa");

        if ( position >= 0 && position <= Carta.getsInstance().getCartaCount()){
            //saco el plato que me han pasado
            Plato plato = Carta.getsInstance().getPlato(position);
            if ( comentario != null ){
                plato.setCamarero(comentario);
            }

            Mesa mesa = Mesas.getInstance().getMesa(mMesaIndex);
            mesa.addPlato(plato);
        }

        /*
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SeleccionaPlatoActivity.EXTRA_PLATO_SELECCIONADO, position);
        returnIntent.putExtra(SeleccionaPlatoActivity.EXTRA_COMENTARIO_CAMARERO, comentario);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        */

    }

    @Override
    public void termine() {
        Log.v("SELECCTIONAPLATOACT","termina");

    }
}
