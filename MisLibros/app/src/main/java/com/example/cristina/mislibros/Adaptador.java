package com.example.cristina.mislibros;

/**
 * Created by 2DAW on 24/02/2016.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class Adaptador extends CursorAdapter {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Adaptador(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Cogemos los elementos de la vista
        TextView tv_titulo = (TextView) view.findViewById(R.id.tituloLibro);
        TextView tv_autor = (TextView) view.findViewById(R.id.idAutor);
        RatingBar rating_nota = (RatingBar) view.findViewById(R.id.ratingBar);
        ImageView imagen = (ImageView) view.findViewById(R.id.imageView);

        //tv_titulo.setFocusable(false);
        //tv_titulo.setFocusableInTouchMode(true);

        //Extraemos los datos del cursor
        String titulo = cursor.getString(cursor.getColumnIndexOrThrow("idTitulo"));
        String autor = cursor.getString(cursor.getColumnIndexOrThrow("idAutor"));
        Float nota = cursor.getFloat(cursor.getColumnIndexOrThrow("ratingbar"));

        //Log.d("AdaptadorLista","Nota: "+nota.toString());

        //Guardamos en los elementos los datos guardados en el cursor
        tv_titulo.setText(titulo);
        tv_autor.setText(autor);
        rating_nota.setRating(nota);

        switch((int)(Math.random()*3)) {
            case 0:
                imagen.setImageResource(R.drawable.libro1);
                break;
            case 1:
                imagen.setImageResource(R.drawable.libro2);
                break;
            case 2:
                imagen.setImageResource(R.drawable.libro3);
                break;
            default:
                imagen.setImageResource(R.drawable.libro1);
                break;

        }
    }
}