package com.nicatec.restaurante.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.fragment.DetalleMesaFragment;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;

public class DetalleMesaActivity extends AppCompatActivity {

    public static final String EXTRA_MESA_INDEX = "EXTRA_MESA_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mesa);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        //Añado el fragmen, compruebo que no exista ya
        FragmentManager fm = getFragmentManager();
        if ( fm.findFragmentById(R.id.fragment_detalle_mesa) == null) {

            //extraigo el parametro que me han pasado, se lo paso al fragment para que saque la mesa que tiene que mostrar
            int index = getIntent().getIntExtra(EXTRA_MESA_INDEX, 0);
            fm.beginTransaction()
                    .add(R.id.fragment_detalle_mesa, DetalleMesaFragment.newInstance(index))
                    .commit();
        }
    }

}
