package com.nicatec.restaurante.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detalle_mesa, container, false);

        //saco las referencias de los elementos de la vista
        mNombreMesa = (TextView) root.findViewById(R.id.nombre_mesa);

        updateView();
        return root;
    }


    private void updateView(){

        mNombreMesa.setText(mMesa.getNombre());
    }

}
