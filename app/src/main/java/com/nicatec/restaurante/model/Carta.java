package com.nicatec.restaurante.model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.MathContext;
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
    private static final String restauranteURL = "http://www.mocky.io/v2/5707f5ff1100002523e9465a";


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

    public int getCartaCount(){
        return  mCarta.size();
    }

    private static Carta downloadInfo() throws MalformedURLException {
        Carta carta = new Carta();

        URL url = null;
        InputStream input = null;
        try {
            url = new URL(restauranteURL);
            //url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&appid=4cef94e2559e8f62a5f567ab654b0a70&units=metric&lang=sp", "Madrid"));
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
            while ( (downloadedBytes = input.read()) != -1) {
                sb.append(new String(data,0, downloadedBytes,"UTF-8"));
            }
            Log.v("DOWNLOAD", String.format("downloaded sb=", sb.toString()));
            //lo que recibo es un array de platos
            String a = "[\n" +
                    "{\n" +
                    "\"name\":\"Al Mondigas\",\n" +
                    "\"pvp\":\"12.95\",\n" +
                    "\"allergies\":[{\"a\":\"huevo\"}],\n" +
                    "\"photo\":\"albondigas.jpg\",\n" +
                    "\"comment\":\"Un plato redondito redondito para comer o jugar a las canicas\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"name\":\"Flan\",\n" +
                    "\"pvp\":\"5.95\",\n" +
                    "\"allergies\":[{\"a\":\"huevo\"}],\n" +
                    "\"photo\":\"flan.jpg\",\n" +
                    "\"comment\":\"Un postre delicioso\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"name\":\"Mac Arrones\",\n" +
                    "\"pvp\":\"7.50\",\n" +
                    "\"allergies\":[{\"a\":\"chorizo\"},{\"a\":\"gluten\"}],\n" +
                    "\"photo\":\"macarrones.jpg\",\n" +
                    "\"comment\":\"Comete un buen plato de estos, con o sin politicos\"\n" +
                    "}\n" +
                    "]";
            //JSONArray platosJSONArray = new JSONArray(sb.toString());
            JSONArray platosJSONArray = new JSONArray(a);
            //me recorro el array
            for (int index = 0; index <= platosJSONArray.length(); index++){
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
                Plato p = new Plato(nombre,precio,foto);
                //para las alergias, he de sacar un objeto que es un array
                JSONArray jsonAlergias = jsonplato.getJSONArray("allergies");
                for (int indexA = 0; indexA < jsonAlergias.length(); indexA++) {
                    //añado la alergia al plato
                    p.addAlergia(jsonAlergias.getJSONObject(indexA).getString("a"));
                }
                //ya tengo el plato preparado, se lo añado a la carta
                carta.mCarta.add(p);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return carta;
    }
}
