package com.nicatec.restaurante.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlatoDetalleFragment extends Fragment {

    private static final String ARG_PLATO_INDEX = "ARG_PLATO_INDEX";
    private static Plato mPlato;
    private PlatoDetalleListener mPlatoDetalleListener;

    public static PlatoDetalleFragment newInstance(int position){
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_PLATO_INDEX, position);
        PlatoDetalleFragment fragment = new PlatoDetalleFragment();
        fragment.setArguments(arguments);
        return fragment;

    }

    public PlatoDetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ){
            mPlato = Carta.getsInstance().getPlato(getArguments().getInt(ARG_PLATO_INDEX));
            updateTitle(mPlato.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_plato_detalle, container, false);

        ImageView photo = (ImageView) root.findViewById(R.id.imageView);
        TextView nombre = (TextView) root.findViewById(R.id.nombre);
        TextView precio = (TextView) root.findViewById(R.id.precio);

        Button add_plato = (Button) root.findViewById(R.id.add_button);
        add_plato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hay que añadir el plato. La actividad tiene la mesa y aqui le paso el plato
                if ( mPlatoDetalleListener != null )
                    mPlatoDetalleListener.addPlatoALaMesa(getArguments().getInt(ARG_PLATO_INDEX));
            }
        });

        nombre.setText(mPlato.getNombre());
        precio.setText(mPlato.getPrecioString());
        photo.setImageBitmap(mPlato.getPic());


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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mPlatoDetalleListener = (PlatoDetalleListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPlatoDetalleListener = (PlatoDetalleListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mPlatoDetalleListener = null;
    }

    public interface  PlatoDetalleListener {
        void addPlatoALaMesa(int position);

    }
}
