package com.nicatec.restaurante.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicatec.restaurante.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMesaFragment extends Fragment {


    public DetalleMesaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_mesa, container, false);
    }

}
