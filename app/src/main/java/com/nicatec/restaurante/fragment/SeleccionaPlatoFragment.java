package com.nicatec.restaurante.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionaPlatoFragment extends Fragment {


    //guardo la referencia del Listener
    private SelectPlatoListener mPlatoListener;

    public SeleccionaPlatoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTitle("Selecciona Plato");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_selecciona_plato, container, false);

        final Carta carta = Carta.getsInstance();

        //tengo una lista con todos los platos que existen en la carta
        ListView list = (ListView) root.findViewById(android.R.id.list);
        //creamos el adaptador para darselo a la lista
        ArrayAdapter<Plato> adapter = new ArrayAdapter<Plato>(getActivity(), android.R.layout.simple_list_item_1, carta.getPlatos());
        list.setAdapter(adapter);

    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if ( mPlatoListener != null ){
                mPlatoListener.onPlatoSelected(position);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPlatoListener = (SelectPlatoListener) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //se llama cuando el fragment se engancha a la actividad
        mPlatoListener = (SelectPlatoListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //esto es cuando el fragment deja de estar en la actividad. Ademas libera memoria
        mPlatoListener = null;
    }
    public interface SelectPlatoListener {
        //paso el indice del plato seleccionado
        void onPlatoSelected(int position);
    }
}
