package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.MesasListFragment;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Mesa;

public class MesasActivity extends AppCompatActivity implements MesasListFragment.MesasListListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);


        AsyncTask<Void, Void, Carta> cartaDownloader = new AsyncTask<Void, Void, Carta>() {
            @Override
            protected Carta doInBackground(Void... voids) {
                return Carta.getsInstance();
            }

            @Override
            protected void onPostExecute(Carta carta)   {
                super.onPostExecute(carta);

            }
        };
        cartaDownloader.execute();



        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_mesas_list) == null){
            fm.beginTransaction().add(R.id.fragment_mesas_list, new MesasListFragment()).commit();
        }


    }



    @Override
    public void onMesaSelected(Mesa mesa, int position) {
        //aqui me entero de que una ciudad ha sido seleccionada en el MesasLisFragment
        Log.v("MESASACTIVITY", "Han seleccionado " + mesa + " posicion " + position);

        //comprobamos si tenemos un fragment del detalle de la mesa en esta actividad,, si es asi es una tablet
        //en caso contrario, tenemos que crear una actividad que mostrara el fragmen del detalle de la mesa, estamos en un telefono

        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_detalle_mesa) != null) {
            /*
        DetalleMesaFragment detalleMesaFragment = (DetalleMesaFragment) fm.findFragmentById(R.id.fragment_detalle_mesa);
        //si es null es que no estamos en tablet
        if ( detalleMesaFragment != null ){
        */
            //le pasamos el indice de la mesa para que saque los datos del singleton
            //detalleMesaFragment.setMesa(position);
        } else {
            //tenemos que crear la actividad
            Intent intent = new Intent(this, DetalleMesaActivity.class);
            intent.putExtra(DetalleMesaActivity.EXTRA_MESA_INDEX, position);
            startActivity(intent);
        }

    }
}
