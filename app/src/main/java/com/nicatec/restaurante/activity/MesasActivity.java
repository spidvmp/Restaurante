package com.nicatec.restaurante.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nicatec.restaurante.R;
import com.nicatec.restaurante.model.Mesa;
import com.nicatec.restaurante.model.Plato;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MesasActivity extends AppCompatActivity {

    private static final String restauranteURL = "http://baccusapp.herokuapp.com/wines";
    public Mesa mMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        //me genero 15 mesas vacias

        Plato p = new Plato("Ptata",45,"fff");
        Plato p2 = new Plato("kkkk", 34,"ldlplp");
        Mesa m = new Mesa(1);

    }


    private static void downloadInfo() throws IOException, JSONException {
        URLConnection conn = new URL(restauranteURL).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null ){
            response.append(line);
        }
        reader.close();

        //lo que recibo es un array de platos
        JSONArray platos = new JSONArray(response.toString());
        //me recorro el array
        for (int index = 0; index <= platos.length(); index++){
            //creo las variables que hacen falta
            String nombre = null;
            String foto = null;
            float precio = 0.0f;
            String comentario = null;

            //creo el objeto plato
            JSONObject jsonplato = platos.getJSONObject(index);

            nombre = jsonplato.getString("name");
            foto = jsonplato.getString("photo");
            precio = Float.parseFloat(jsonplato.getString("pvp"));
            comentario = jsonplato.getString("comment");

            //creo el plato
            Plato p = new Plato(nombre,precio,foto,comentario);

            JSONArray jsonAlergias = jsonplato.getJSONArray("allergies");
            for (int index = 0; index < jsonAlergias.length(); index++) {
                
            }

        }
    }
}
