package com.example.domingo.androidlibros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.sql.SQLException;

public class VistaLibro extends AppCompatActivity
{

    private Cursor cursor;
    private MisLibrosDDBB database = new MisLibrosDDBB(this);
    long identificadorLibro;
    boolean anhadir, editar, eliminar;

    EditText et_editorial;
    EditText et_isbn;
    CheckBox cb_ebook;
    CheckBox cb_leido;
    EditText et_paginas;
    EditText et_anho;
    EditText et_titulo;
    EditText et_autor;
    RatingBar rat_nota;
    EditText et_resumen;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datoslibro);

        //Coge los elementos de la vista
        et_titulo = (EditText) findViewById(R.id.campoTitulo);
        et_autor = (EditText) findViewById(R.id.campoAutor);
        et_editorial = (EditText) findViewById(R.id.campoEditorial);
        et_isbn = (EditText) findViewById(R.id.campoIsbn);
        et_paginas = (EditText) findViewById(R.id.campoPaginas);
        et_anho = (EditText) findViewById(R.id.et_anho);
        cb_ebook = (CheckBox) findViewById(R.id.checkEbook);
        cb_leido = (CheckBox) findViewById(R.id.checkLeido);
        rat_nota = (RatingBar) findViewById(R.id.notaLibro);
        et_resumen = (EditText) findViewById(R.id.campoResumen);

        identificadorLibro = getIntent().getLongExtra("id", 0);
        if (identificadorLibro != 0)
        {
            anhadir = false;
            editar = true;
            eliminar = true;

            try
            {
                database.openR();//Abre BD
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            /*una vez tenemos el identificador del libro le pedimos los datos de dicho libro a la clase MisLibrosDDBB y los guardamos en el cursor
            * luego nos ayudamos de la funcion setText para mostrar los datos en la vista recogiendo los datos del cursor*/
            cursor = database.getUnLibro(identificadorLibro);
            et_titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            et_autor.setText(cursor.getString(cursor.getColumnIndexOrThrow("autor")));
            et_editorial.setText(cursor.getString(cursor.getColumnIndexOrThrow("editorial")));
            et_isbn.setText(cursor.getString(cursor.getColumnIndexOrThrow("isbn")));
            et_paginas.setText(cursor.getString(cursor.getColumnIndexOrThrow("paginas")));
            et_anho.setText(cursor.getString(cursor.getColumnIndexOrThrow("anio")));
            cb_ebook.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("ebook")) != 0);
            cb_leido.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("leido")) != 0);
            rat_nota.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow("nota")));
            et_resumen.setText(cursor.getString(cursor.getColumnIndexOrThrow("resumen")));

            database.close();
        }
        else
        {
            anhadir = true;
            editar = false;
            eliminar = false;
        }

    }

    /**
     * Carga el menu de la barra de arriba
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Establece las funciones de las opciones del menú
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.edit:
                updateLibro();
                break;
            case R.id.guardar:
            {
                Insertar();
                break;
            }
            case R.id.eliminar:
            {
                pregunta("Eliminar libro", "¿Está seguro?").show();
                break;
            }
        }
        return true;
    }

    /**
     * Establece si se debe mostrar o no una opción del menú
     */
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.findItem(R.id.guardar).setVisible(anhadir);
        menu.findItem(R.id.eliminar).setVisible(eliminar);
        menu.findItem(R.id.edit).setVisible(editar);
        return true;
    }

    /**
     * Eliminamos el libro de la identificadorLibro que hayamos guardado anteriormente
     */
    public void Eliminar() {
        try
        {
            try
            {
                database.openW();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }

            database.deleteLibro(identificadorLibro);

            database.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Se ha producido un error eliminando", Toast.LENGTH_SHORT).show();
        }
    }

    private AlertDialog pregunta(String titulo, String mensaje)
    {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);

        //Botón CANCELAR
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        };

        //Botón ACEPTAR
        DialogInterface.OnClickListener listenerOK = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Eliminar();
                Toast.makeText(getApplicationContext(), "Libro eliminado", Toast.LENGTH_SHORT).show();
                finish();//Finaliza actividad para volver a la vista principal
            }
        };

        //Establece el nombre del botón y la función que tiene que ejecutar
        alerta.setPositiveButton("Aceptar", listenerOK);
        alerta.setNegativeButton("Cancelar", listenerCancelar);

        return alerta.create();
    }


    public void updateLibro()
    {
        try {
            if (et_titulo.getText().toString().matches("") || et_autor.getText().toString().matches(""))
            {
                Toast.makeText(this, "Los campos título y un autor son obligatorios", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {//abrimos
                    database.openW();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
                database.updateLibro(identificadorLibro,
                        et_titulo.getText().toString(),
                        et_autor.getText().toString(),
                        et_editorial.getText().toString(),
                        et_isbn.getText().toString(),
                        et_anho.getText().toString(),
                        et_paginas.getText().toString(),
                        (cb_ebook.isChecked() ? 1 : 0),
                        (cb_leido.isChecked() ? 1 : 0),
                        rat_nota.getRating(),
                        et_resumen.getText().toString()
                );

                Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show();
                database.close();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Se ha producido un error editando", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Añade un libro a la base de datos, se tiene que introducir el título y el autor para poder guardarlo
     */
    public void Insertar() {
        try {
            if (et_titulo.getText().toString().matches("") || et_autor.getText().toString().matches("")) {
                Toast.makeText(this, "Los campos título y un autor son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    database.openW();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                database.insertLibro(
                        et_titulo.getText().toString(),
                        et_autor.getText().toString(),
                        et_editorial.getText().toString(),
                        et_isbn.getText().toString(),
                        et_anho.getText().toString(),
                        et_paginas.getText().toString(),
                        (cb_ebook.isChecked() ? 1 : 0),
                        (cb_leido.isChecked() ? 1 : 0),
                        rat_nota.getRating(),
                        et_resumen.getText().toString());
                database.close();
                Toast.makeText(this, "Libro añadido correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_SHORT).show();
        }
    }
}
