package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.MesasListFragment;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Plato;

import java.util.LinkedList;

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

    }
}
