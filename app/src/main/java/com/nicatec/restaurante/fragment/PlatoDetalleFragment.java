package com.nicatec.restaurante.fragment;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Carta;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlatoDetalleFragment extends Fragment {

    private static final String ARG_PLATO_INDEX = "ARG_PLATO_INDEX";
    private static final String ARG_MESA_INDEX = "ARG_MESA_INDEX";
    private static Plato mPlato;
    //esta opcion de la mesa es para cuando se edita un plato de la mesa
    private static Mesa mMesa = null;
    private PlatoDetalleListener mPlatoDetalleListener;

    public static PlatoDetalleFragment newInstance(int positionPlato, int positionMesa){
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_PLATO_INDEX, positionPlato);
        arguments.putInt(ARG_MESA_INDEX, positionMesa);
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
            if ( getArguments().getInt(ARG_MESA_INDEX) == -1) {
                //si parametro de mesa es -1, es que es un plato nuevo
                mPlato = Carta.getsInstance().getPlato(getArguments().getInt(ARG_PLATO_INDEX));

            } else {
                //tenemos valor en mesa, eso significa que se esta editanfo, asi que el plato no sale del singleton, sino del array que tiene la mesaç
                mMesa = Mesas.getInstance().getMesa(getArguments().getInt(ARG_MESA_INDEX));
                mPlato = mMesa.getPlatos().get(getArguments().getInt(ARG_PLATO_INDEX));
            }
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
        TextView comentario = (TextView) root.findViewById(R.id.comentario);
        final EditText camarero = ( EditText) root.findViewById(R.id.camarero);

        Button add_plato = (Button) root.findViewById(R.id.add_button);
        //si se esta editando cambio el texto del boton
        if ( mMesa != null ){
            add_plato.setText("Modificar");
        }
        add_plato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hay que añadir el plato. La actividad tiene la mesa y aqui le paso el plato y el comentario del camarero, si lo hubiera puesto
                if (mPlatoDetalleListener != null) {
                    String c = camarero.getText().toString();

                    //en el caso de que la mesa sea null, significa que añado un plato a la mesa
                    if ( mMesa == null) {
                        mPlatoDetalleListener.addPlatoALaMesa(getArguments().getInt(ARG_PLATO_INDEX), c);
                    } else {
                        //estamos editando, lo unico que se modifica es el comentario del camarero
                        //modifico el valor del comentario del camarero
                        mPlato.setCamarero(c);
                        //cambio el plato de la mesa por esta actualizacion
                        mMesa.getPlatos().set(getArguments().getInt(ARG_PLATO_INDEX), mPlato);
                        Mesas.getInstance().getMesas().set(getArguments().getInt(ARG_MESA_INDEX), mMesa);
                        //le paso a mi atividadque he terminado, que salga sin hacer nada xq ya lo hice yo
                        mPlatoDetalleListener.termine();
                    }
                }
            }
        });

        nombre.setText(mPlato.getNombre());
        precio.setText(mPlato.getPrecioString());
        photo.setImageBitmap(mPlato.getPic());
        comentario.setText(mPlato.getComentario());
        if ( mPlato.getCamarero() != null) {
            camarero.setText(mPlato.getCamarero());
        }

        //hago un linear layout para poner los icono de las alergias
        //conpruebo si tengo alergias
        if ( mPlato.alergiasCount() > 0) {
            LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.alergiasIcons);

            for (int i = 0; i < mPlato.alergiasCount(); i++) {
                //saco la alergia que es
                String a = mPlato.getAlergiaAtIndex(i);
                //creo las vistas e inserto en el layout
                ImageView imageView = new ImageView(root.getContext());
                //imagino que esto se podra generar automaticamente o hacer de una menera mejor, pero de momento con el switch
                switch (a){
                    case "gluten":
                        imageView.setImageResource(R.drawable.gluten);
                        break;
                    case "huevo":
                        imageView.setImageResource(R.drawable.huevo);
                        break;
                }

                linearLayout.addView(imageView);
            }





        }

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
        void addPlatoALaMesa(int position, String text);
        void termine();

    }
}
