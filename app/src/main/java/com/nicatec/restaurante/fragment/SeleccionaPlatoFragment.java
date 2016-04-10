package com.nicatec.restaurante.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionaPlatoFragment extends Fragment {


    public SeleccionaPlatoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_selecciona_plato, container, false);

        final Carta carta = Carta.getsInstance();

        //tengo una lista con todos los platos que existen en la carta
        ListView listView = (ListView) root.findViewById(R.id.list);
        //creamos el adaptador para darselo a la lista
        ArrayAdapter<Plato> adapter = new ArrayAdapter<Plato>(getActivity(), android.R.layout.simple_list_item_1, carta.getPlatos());
        listView.setAdapter(adapter);



        return root;
    }

}
