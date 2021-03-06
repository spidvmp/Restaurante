package com.nicatec.restaurante.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by vtx on 8/4/16.
 * Modelo de la lista de Platos que tiene el restaurante. El listado de platos que tiene se baja de un JSON
 * Es un singleton ya que esta lista va a ser unica
 */
public class Carta {

    //genero un singleton con todos los platos que tengo
    private static Carta sInstance = null;
    private LinkedList<Plato> mCarta = null;
    private static final String restauranteURL = "http://www.mocky.io/v2/570f82b5250000c51d29c77e";


    public static Carta getsInstance() {
        if ( sInstance == null ) {
            try {
                sInstance = downloadInfo();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return sInstance;
    }

    //constructor
    public Carta() throws MalformedURLException {
        mCarta = new LinkedList<Plato>();


    }

    public Plato getPlato(int index){
        //me devuelve el plato que este enla posicion x
        return mCarta.get(index);
    }

    public LinkedList<Plato> getPlatos() { return mCarta; }

    public int getCartaCount(){
        return  mCarta.size();
    }

    public void addPlato(Plato p) { mCarta.add(p); }

    private static Carta downloadInfo() throws MalformedURLException {
        Carta carta = new Carta();

        URL url = null;
        InputStream input = null;
        try {
            url = new URL(restauranteURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            //esto me devuelve cuanto se ha bajado
            int responseLength = conn.getContentLength();

            //bajamos el contenido a trocitos
            byte data[] = new byte[1024];
            long currentBytes = 0;
            int downloadedBytes;
            input = conn.getInputStream();
            StringBuilder sb = new StringBuilder();
            while ( (downloadedBytes = input.read(data)) != -1) {
                sb.append(new String(data,0, downloadedBytes));
            }
            Log.v("DOWNLOAD", String.format("downloaded sb=", sb.toString()));
            //lo que recibo es un array de platos
            JSONArray platosJSONArray = new JSONArray(sb.toString());
            //JSONArray platosJSONArray = new JSONArray(a);
            //me recorro el array
            for (int index = 0; index < platosJSONArray.length(); index++){
                //creo las variables que hacen falta
                String nombre = null;
                String foto = null;
                float precio = 0.0f;
                String comentario = null;

                //creo el objeto JSON de plato
                JSONObject jsonplato = platosJSONArray.getJSONObject(index);
                //relleno los datos
                nombre = jsonplato.getString("name");
                foto = jsonplato.getString("photo");
                precio = (float) jsonplato.getDouble("pvp");
                comentario = jsonplato.getString("comment");

                //creo el plato
                Plato p = new Plato(nombre,precio,foto,comentario);
                //para las alergias, he de sacar un objeto que es un array
                JSONArray jsonAlergias = jsonplato.getJSONArray("allergies");
                for (int indexA = 0; indexA < jsonAlergias.length(); indexA++) {
                    //añado la alergia al plato
                    p.addAlergia(jsonAlergias.getJSONObject(indexA).getString("a"));
                }
                //ya tengo el plato preparado, se lo añado a la carta
                carta.addPlato(p);
            }

            Log.v("CARTA","Termine de bajarme el JSON");
        } catch (Exception ex) {
            Log.v("DOWNLOAD CARTA", "Error con la conexion");
            ex.printStackTrace();
        }

        return carta;
    }
}
