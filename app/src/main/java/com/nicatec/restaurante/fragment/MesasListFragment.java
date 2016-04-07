package com.nicatec.restaurante.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;

import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesasListFragment extends Fragment {


    public MesasListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

          LinkedList<Mesa> mMesas = new LinkedList<Mesa>();

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_mesas_list, container, false);

        //accedo a las mesas, ya tengo acceso al array
        ListView list = (ListView) root.findViewById(android.R.id.list);

        //modelo para dar valores a la lista

        for (int i=0; i<=14 ; i++){
            mMesas.add(new Mesa(i+1));
        }

        //creamos un adaptador para darselo a al lista y que sepa que datos mostrar
        ArrayAdapter<Mesa> adapter = new ArrayAdapter<Mesa>(getActivity(), android.R.layout.simple_list_item_1, mMesas);

        //le asignamos el adaptador a la vista
        list.setAdapter(adapter);


        return root;
    }

}
