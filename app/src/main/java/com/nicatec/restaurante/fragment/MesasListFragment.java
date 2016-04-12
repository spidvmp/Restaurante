package com.nicatec.restaurante.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesasListFragment extends Fragment {


    //guardo una referencia a mi listener
    private MesasListListener mMesasListListener;



    public MesasListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // /modelo para dar valores a la lista
        final Mesas mesas = Mesas.getInstance();

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_mesas, container, false);

        //accedo a las mesas, ya tengo acceso al array
        ListView list = (ListView) root.findViewById(android.R.id.list);

        //creamos un adaptador para darselo a la lista y que sepa que datos mostrar

        ArrayAdapter<Mesa> adapter = new ArrayAdapter<Mesa>(getActivity(), android.R.layout.simple_list_item_1,mesas.getMesas());

        //le asignamos el adaptador a la vista;
        list.setAdapter(adapter);

        //para enterarnos de que pulsan sobre una celda, hay que ...
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


        return root;
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
    
    //estos metodos se implementan para asegurarnos que cuando se toque una celda, el fragmen y la actividad esten conectados,
    // si no lo estan daria un error y cascaria. Hay que implementar los 2 , el deprecated es para los dispositivos antiguos
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMesasListListener = (MesasListListener) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //se llama cuando el fragment se engancha a la actividad
        mMesasListListener = (MesasListListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //esto es cuando el fragment deja de estar en la actividad. Ademas libera memoria
        mMesasListListener = null;
    }

    //creo una interfaz publica para comunicarme con mi actividad, es similar al protocolo de swift
    public interface MesasListListener {
        void onMesaSelected(Mesa mesa, int position);
    }
}
