package com.nicatec.restaurante.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMesaFragment extends Fragment {
    private static final String ARG_MESA_INDEX = "ARG_MESA_INDEX";
    private static Mesa mMesa;

    private DetalleMesaListener mDetalleMesaListener;


    public static DetalleMesaFragment newInstance(int position) {
        //esto devuelve un DEtallemesafragment y ha recibido un paramtro
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_MESA_INDEX, position);

        DetalleMesaFragment fragment = new DetalleMesaFragment();
        fragment.setArguments(arguments);

        return fragment;
    }
    public DetalleMesaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //saco los argumentos, que sera el index de la mesa, ya me quedo con la mesa que es
        if ( getArguments() != null ) {
            mMesa = Mesas.getInstance().getMesa(getArguments().getInt(ARG_MESA_INDEX));
            updateTitle(mMesa.toString());
        }

        //indicamos que toolbar tiene opciones
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detalle_mesa, container, false);

        //saco las referencias de los elementos de la vista
        //TextView nombre = (TextView) root.findViewById(R.id.nombre_mesa);
        //nombre.setText(mMesa.toString());
        //tabla de platos pedidos
        ListView list = (ListView) root.findViewById(R.id.list);

        //creamos un adaptador para darselo a la lista y que sepa que datos mostrar
        //le tengo que meter el array que esta dentro de la mesa pedida
        ArrayAdapter<Plato> adapter = new ArrayAdapter<Plato>(getActivity(), android.R.layout.simple_list_item_1,mMesa.getPlatos());

        //le asignamos el adaptador a la vista;
        list.setAdapter(adapter);

        /*
        //para enterarnos de que pulsan sobre una celda de , hay que ...
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //le digo a mi actividad que han pulsado una celda
                //compruebo que estoy enganchado a la actividad ( con el onAttach )
                if ( mMesasListListener != null ){
                    //aviso al listener
                    //obtengo la mesa pulsada
                    Mesa mesaSelected = mesas.getMesa(position);
                    mMesasListListener.onMesaSelected(mesaSelected,position);


                }
            }
        });
        */

        //miramos a ver si pulsan sobre añadir un plato a la mesa, si lo hacen tengo que sacar un listado de platos
/*
        Button add_plato = (Button) root.findViewById(R.id.add_button);
        add_plato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saco la actividad con todos los platos, selecciona uno y se deberia añadir a la lista dela mesa
                seleccionaPlatoDeLaCarta();
            }
        });
        */
        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detallemesa, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue =  super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.dolorosa:
                break;
            case R.id.addplatocomanda:
                //tengo que pasar la mesa al detalleActivity para que lance el listado de platos, lo hago usando el listener y el interface
                if ( mDetalleMesaListener != null) {
                    mDetalleMesaListener.addPlatoOnMesa(mMesa.getIndex());
                }
                return true;
                

        }
        return superValue;
    }

    void updateTitle(String newTitle){
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            // 2) Acceder, dentro de la actividad, a la ActionBar
            android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();

            // 3) Cambiar el texto a la toolbar
            actionBar.setTitle(newTitle);
        }

    }
/*
    private void seleccionaPlatoDeLaCarta(){

        //he de pasarle esta informacion a la actividad, como el delagado y la actividad lanzara el SeleccionaPlatoActivity
        //le paso la mesa de la que estoy hablando para que en caso de seleccionar un plato, pase a su vez el numero de mesa para que se incluya
        //en el listado de platos pedidos por la mesa

        //tengo que pasar la mesa al detalleActivity para que lance el listado de platos, lo hago usando el listener y el interface
        if ( mDetalleMesaListener != null) {
            mDetalleMesaListener.addPlatoOnMesa(mMesa.getIndex());
        }



    /*
        //tenemos que crear la actividad
        Intent intent = new Intent(this, SeleccionaPlatoActivity.class);
        intent.putExtra(SeleccionaPlatoActivity.EXTRA_MESA, mMesa.getIndex());
        startActivity(intent);

    }
*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mDetalleMesaListener = (DetalleMesaListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDetalleMesaListener = (DetalleMesaListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mDetalleMesaListener = null;
    }
    public interface DetalleMesaListener {
        //si pulsan sobre el boton de add, he de sacar la lista de los platos para añadir en una mesa, paso el indice de la mesa
        void addPlatoOnMesa(int  mesaIndex);
    }
}
