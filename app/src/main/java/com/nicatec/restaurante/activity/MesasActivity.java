package com.nicatec.restaurante.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Plato;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class MesasActivity extends AppCompatActivity {

    private static final String restauranteURL = "http://www.mocky.io/v2/57062d3b1000003903a3f8cf";

    //Array de mesas
    public static LinkedList<Mesa> mMesas;
    public static LinkedList<Plato> mCarta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        //genero por ejemplo 15 posibles mesas de mi restaurante
        init_modelos();


        //downloadInfo();


        //me genero 15 mesas vacias

    }

    private void init_modelos(){
        mMesas = new LinkedList<Mesa>();
        mCarta = new LinkedList<Plato>();

        //inicializo los modelos
        for (int i=0; i<=14 ; i++){
            mMesas.add(new Mesa(i+1));
        }

        Plato p = new Plato("patata",8.9f,"p");
        Plato o = new Plato("carne",25.7f,"c");
        Plato i = new Plato("lechuga",3.4f,"l");

        mCarta.add(p);
        mCarta.add(o);
        mCarta.add(i);

    }
    private static void downloadInfo() throws MalformedURLException {

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
            while ( (downloadedBytes = input.read()) != -1) {
               sb.append(new String(data,0, downloadedBytes));
            }
            //analizamos los datos de JSON a clase






        /*
        URLConnection conn = new URL(restauranteURL).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null ){
            response.append(line);
        }
        reader.close();
        */

        //lo que recibo es un array de platos
        JSONArray platosJSONArray = new JSONArray(sb.toString());
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
                //ayado la alergia al plato
                p.addAlergia(jsonAlergias.getJSONObject(indexA).getString("a"));
            }

        }





        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
