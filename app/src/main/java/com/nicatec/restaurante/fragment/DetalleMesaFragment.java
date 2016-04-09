package com.nicatec.restaurante.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMesaFragment extends Fragment {
    private static final String ARG_MESA_INDEX = "ARG_MESA_INDEX";
    private static Mesa mMesa;

    //saco los elementos de la vista
    private TextView mNombreMesa;

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
        if ( getArguments() != null )
            mMesa = Mesas.getInstance().getMesa(getArguments().getInt(ARG_MESA_INDEX));
        Log.v("DetalleMesaFrgamnent", "Mesa " + mMesa.getmNumero());

        mMesa.addPlato(Carta.getsInstance().getPlato(1));
        mMesa.addPlato(Carta.getsInstance().getPlato(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detalle_mesa, container, false);

        //saco las referencias de los elementos de la vista
        mNombreMesa = (TextView) root.findViewById(R.id.nombre_mesa);
        //tabla de platos pedidos
        ListView list = (ListView) root.findViewById(R.id.list);

        //creamos un adaptador para darselo a la lista y que sepa que datos mostrar
        //le tengo que meter el array que esta dentro de la mesa pedida
        ArrayAdapter<Plato> adapter = new ArrayAdapter<Plato>(getActivity(), android.R.layout.simple_list_item_1,mMesa.getPlatos());

        //le asignamos el adaptador a la vista;
        list.setAdapter(adapter);

        /*
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
    */
        updateView();
        return root;
    }


    private void updateView(){

        mNombreMesa.setText(mMesa.toString());
    }

}
