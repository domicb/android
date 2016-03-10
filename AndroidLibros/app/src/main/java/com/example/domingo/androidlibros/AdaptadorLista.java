package com.example.domingo.androidlibros;

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

/**
 * Utilizamos la clase cursor adapter para poder manejar los datos de nuestra base de datos a traves de cursores
 */
public class AdaptadorLista extends CursorAdapter {
    //instanciamos nuestro adaptador a traves del constructor del padre
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdaptadorLista(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }


    //sobrecargamos con los datos del cursor e utilizamos el metodo inflate para mostrar la vista del libro
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.libro, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //recogemos lo que el usuario nos introduzca
        TextView tv_titulo = (TextView) view.findViewById(R.id.tv_titulo_lib);
        TextView tv_autor = (TextView) view.findViewById(R.id.tv_autor);
        RatingBar rating_nota = (RatingBar) view.findViewById(R.id.rating_nota);
        ImageView imagen = (ImageView) view.findViewById(R.id.imagen);

        //recogemos los campos con el id devuelto por el cursor
        String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
        String autor = cursor.getString(cursor.getColumnIndexOrThrow("autor"));
        Float nota = cursor.getFloat(cursor.getColumnIndexOrThrow("nota"));

        //aplicamos los cambios a la vista con setText
        tv_titulo.setText(titulo);
        tv_autor.setText(autor);
        rating_nota.setRating(nota);

        imagen.setImageResource(R.drawable.libro2);


    }
}
