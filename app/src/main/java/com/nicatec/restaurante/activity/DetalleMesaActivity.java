package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.DetalleMesaFragment;

/*
Detalle de una mesa, cuando se selecciona un a mesa en mesasActivity, muestra el detalle de la mesa y los platos que han pedido, aqui se
añaden nuevos platos y se da la opcion de pedir la cuenta. Utiliza el frgament DetalleMesasFragmnet para mostrar la lista de los platos y
el resto de la informacion de la mesa
 */

public class DetalleMesaActivity extends AppCompatActivity implements DetalleMesaFragment.DetalleMesaListener {

    //indice de la mesa que se esta tratando
    public static final String EXTRA_MESA_INDEX = "EXTRA_MESA_INDEX";
    //indice del plato que se trata de la mesa, si se edita se nvia este parametro, si estan añadiendo uno esto no vaç
    public static final String EXTRA_PLATO_DE_LA_MESA = "EXTRA_PLATO_DE_LA_MESA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mesa);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Añado el fragmen, compruebo que no exista ya
        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_detalle_mesa) == null) {

            //extraigo el parametro que me han pasado, se lo paso al fragment para que saque la mesa que tiene que mostrar
            int index = getIntent().getIntExtra(EXTRA_MESA_INDEX, 0);
            int indexPlato = getIntent().getIntExtra(EXTRA_PLATO_DE_LA_MESA, -1);
            fm.beginTransaction()
                    .add(R.id.fragment_detalle_mesa, DetalleMesaFragment.newInstance(index, indexPlato))
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
    public void addPlatoOnMesa(int mesaIndex, int platoIndex) {
        //estoy en una mesa y han seleccionado que quieren añadir un plato, me pasan el indice de la mesa donde añadir
        //si platoIndex es distinto de  -1, significa que se esta modificando un plato de la mesa, no se alade ninguno
        Intent intent = new Intent(this, SeleccionaPlatoActivity.class);
        intent.putExtra(SeleccionaPlatoActivity.EXTRA_MESA, mesaIndex);
        intent.putExtra(SeleccionaPlatoActivity.EXTRA_PLATO_SELECCIONADO, platoIndex);
        startActivity(intent);
    }
    @Override
    public void editPlatoDeLaMesa(int indexMesa, int indexPlato) {
        //ESto me llega cuando pulsan sobre un plato que esta en la comanda de una mesa, edito el plato
        //van a editar el plato de la mesa
        Intent intent = new Intent(this, PlatoDetalleActivity.class);
        intent.putExtra(PlatoDetalleActivity.EXTRA_MESA_INDEX, indexMesa);
        intent.putExtra(PlatoDetalleActivity.EXTRA_PLATO_INDEX, indexPlato);
        startActivity(intent);

    }


}
