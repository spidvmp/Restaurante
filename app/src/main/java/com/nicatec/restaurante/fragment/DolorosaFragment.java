package com.nicatec.restaurante.fragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
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
public class DolorosaFragment extends DialogFragment {

    static final String ARG_MESA_DOLOROSA = "ARG_MESA_DOLOROSA";
    private Mesa mMesa;

    public DolorosaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //saco el parametro de la mesa
        //saco los argumentos, que sera el index de la mesa, ya me quedo con la mesa que es
        if ( getArguments() != null ) {
            mMesa = Mesas.getInstance().getMesa(getArguments().getInt(ARG_MESA_DOLOROSA));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_dolorosa, container, false);

        TextView text = (TextView) root.findViewById(R.id.precio);

        String txt = "precio 5";

        text.setText(txt);

        return root;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.dolorosa_dialog);
        return dialog;
    }

}
