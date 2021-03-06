package com.nicatec.restaurante.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nicatec.restaurante.fragment.SeleccionaPlatoFragment;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by vtx on 5/4/16.
 * Esta clase define un plato, nombre, precio, etc
 *
 */
public class Plato {
    private String mNombre = null;
    private float mPrecio = 0.0f;
    private String mFotoURL = null;
    private Bitmap mPic = null;
    private String mCamarero = null; //especificaciones que incluye el camarero
    private String mComentario = null;
    private LinkedList<String> mAlergias = null;

    public Plato(String nombre, float precio, String foto, String comentario) {
        this.mNombre = nombre;
        this.mPrecio = precio;
        this.mFotoURL = foto;
        this.mComentario = comentario;

        //Las variantes se modificaran en el pedio, asi que se inicializa en blanco
        this.mAlergias = new LinkedList<String>();

        //no queria poner esto aqui, sino que se baje solo cuando haga falta, pero no se como refrescar la imagen una vez se haya bajado
        getBitmapFromURL(getFotoURL());
    }

    public void addAlergia(String alergia){
        //me pasan una alergia y la añado al array de alergias
        this.mAlergias.add(alergia);
    }

    public String getNombre() {
        return mNombre;
    }

    public float getPrecio() {
        return mPrecio;
    }
    public String getPrecioString() { return String.format("%.2f€",mPrecio);}

    public String getFotoURL() {
        return mFotoURL;
    }
    public void setFoto(String foto) { mFotoURL = foto; }
    public String toString() { return mNombre; }

    public Bitmap getPic() {

        if ( mPic == null )
            getBitmapFromURL(getFotoURL());

        return mPic;
    }
    private void getBitmapFromURL(String fotoURL){
        //la foto se la baja, pero no se como hacer para que se muestre
        AsyncDownloader dwn = new AsyncDownloader();
        dwn.execute(this);
    }

    public void setPic(Bitmap mPic) {
        this.mPic = mPic;
    }

    public LinkedList<String> getAlergias() {
        return mAlergias;
    }
    public int alergiasCount() { return mAlergias.size(); }
    public String getAlergiaAtIndex(int index) { return mAlergias.get(index); }

    public String getComentario() {
        return mComentario;
    }

    public void setComentario(String comentario) {
        this.mComentario = comentario;
    }
    public String getCamarero() {
        return mCamarero;
    }

    public void setCamarero(String camarero) {
        this.mCamarero = camarero;
    }


}

class AsyncDownloader extends AsyncTask <Plato, Void, Bitmap>{
    private  Plato mPlato;

    @Override
    protected Bitmap doInBackground(Plato... params) {
        Plato plato = params[0];
        mPlato = plato;

        InputStream in = null;

        try {
            String url = "http://www.nicatec.com/android/" + plato.getFotoURL();
            in = new URL(url).openStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "Error downloading image", ex);
            return null;
        } finally {
            //cerramos la conexion
            try {
                if (in != null) {
                    in.close();
                }
            } catch ( Exception ex) {
                Log.e("Download", "Error ", ex);
            }
        }


    }


    @Override
    protected void onPostExecute(Bitmap o) {
        super.onPostExecute(o);
        if ( o != null) {
            mPlato.setPic(o);

        } else {
            Log.v("DOWNLOADASYNC", "error en el onPostExecute");
        }

    }

}
