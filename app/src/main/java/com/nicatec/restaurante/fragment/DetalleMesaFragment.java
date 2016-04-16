package com.nicatec.restaurante.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Mesas;
import com.nicatec.restaurante.model.Plato;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMesaFragment extends Fragment {
    private static final String ARG_MESA_INDEX = "ARG_MESA_INDEX";
    private static final String ARG_PLATO_DE_LA_MESA_INDEX = "ARG_PLATO_DE_LA_MESA_INDEX";
    private static Mesa mMesa;

    private DetalleMesaListener mDetalleMesaListener;
    private ListView list;
    private MenuItem mMenuItem;


    public static DetalleMesaFragment newInstance(int position, int posicionPlato) {
        //me pasan la posicion dela mesa y la posicion del plato. La posicion del plato podria ser -1, eso significa que el plato que se saca
        //es del sibgleton de los platos y sera añadir un plato a la mesa, si viene con dato, entonces estan editando una mesa y un plato
        //asi que hayq eu sacarlo de la mesa(posicion).plato(posicionPlato) y no se añadira nada, solo se modificara el comentario del camarero
        //esto devuelve un DEtallemesafragment y ha recibido un paramtro
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_MESA_INDEX, position);
        if ( posicionPlato >= 0)
            arguments.putInt(ARG_PLATO_DE_LA_MESA_INDEX, posicionPlato);

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
        if ( getArguments() != null ) {
            mMesa = Mesas.getInstance().getMesa(getArguments().getInt(ARG_MESA_INDEX));
            updateTitle(mMesa.toString());
        }

        //indicamos que toolbar tiene opciones
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detalle_mesa, container, false);

        //saco las referencias de los elementos de la vista
        //TextView nombre = (TextView) root.findViewById(R.id.nombre_mesa);
        //nombre.setText(mMesa.toString());
        //tabla de platos pedidos
        this.list = (ListView) root.findViewById(R.id.list);

        //creamos un adaptador para darselo a la lista y que sepa que datos mostrar
        //le tengo que meter el array que esta dentro de la mesa pedida
        ArrayAdapter<Plato> adapter = new ArrayAdapter<Plato>(getActivity(), android.R.layout.simple_list_item_1,mMesa.getPlatos());
        //le asignamos el adaptador a la vista;
        list.setAdapter(adapter);

        //le asignamos un listener a la tabla, si pulsan sobre unplato de l amesa se va a PlatoDEtalleActivity y muestra el plato, el camaeo podra añadir cosas
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ( mDetalleMesaListener != null )
                    mDetalleMesaListener.editPlatoDeLaMesa(mMesa.getIndex(), position);
            }
        });


        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        actualizaLaMesa();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detallemesa, menu);

        mMenuItem = menu.findItem(R.id.dolorosa);
        mMenuItem.setEnabled(!mMesa.estaVacia());
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue =  super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.dolorosa:
                //tengo quemostrar un dialog y luego limpiar la mesa
                mostrarCuenta();
                actualizaLaMesa();
                return true;
            case R.id.addplatocomanda:
                //tengo que pasar la mesa al detalleActivity para que lance el listado de platos, lo hago usando el listener y el interface
                //paso tambien el plato si lo estuvieramos editando, como es uno nuevo le paso -1
                if ( mDetalleMesaListener != null) {
                    mDetalleMesaListener.addPlatoOnMesa(mMesa.getIndex(),-1);
                }
                return true;


        }
        return superValue;
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

    void mostrarCuenta(){
        //La mesa esta en el parametro, muestro la cuenta y limpio la mesa
        float aPagar = mMesa.laDolorosa();
        String msg = "Total a pagar: " + Float.toString(aPagar) ;
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Cuenta");
        alert.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog a = alert.create();
        a.show();

    }

    private void actualizaLaMesa(){
        //recarga la tabla y comprueba el estado enable de laDolorosa
        //vuelvo a recargar la tabla, es posible ue haya una forma mejor
        this.list.invalidateViews();
        if ( mMenuItem != null)
            mMenuItem.setEnabled(!mMesa.estaVacia());
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mDetalleMesaListener = (DetalleMesaListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDetalleMesaListener = (DetalleMesaListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mDetalleMesaListener = null;
    }
    public interface DetalleMesaListener {
        //si pulsan sobre el boton de add, he de sacar la lista de los platos para añadir en una mesa, paso el indice de la mesa
        void addPlatoOnMesa(int  mesaIndex, int platoIndex);
        //si han pulsado sobre unplato, edito el plato de la mesa
        void editPlatoDeLaMesa(int indexMesa, int indexPlato);
    }
}
