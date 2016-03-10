package com.example.domingo.androidlibros;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    ListView listViewListaDeLibros;
    private Cursor cursor;
    MisLibrosDDBB BaseDatosLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Enlazamos el boton añadir con un inten y le pasamos el campo id para identificar el libro
        FloatingActionButton btn_MAS = (FloatingActionButton) findViewById(R.id.unomas);
        btn_MAS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getApplicationContext(), VistaLibro.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });

        BaseDatosLibros = new MisLibrosDDBB(this);
        crearPrimerosLibros();
        actualizaVista();
        listViewListaDeLibros.setOnItemClickListener(this);

    }

    /**
     * Añadimos el campo id con la ayuda del intent
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, VistaLibro.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * Carga todos los libros guardados en la lista
     */
    public void actualizaVista()
    {
        listViewListaDeLibros = (ListView) findViewById(R.id.lv_listaLibros);

        try
        {
            BaseDatosLibros.openR();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        //metemos en el cursor los libros de la base de datos
        cursor = BaseDatosLibros.getLibros();
        if (cursor.moveToFirst())
        {
            AdaptadorLista adaptador = new AdaptadorLista(this, cursor, 0);
            //una vez tenemos los libros instanciamos el adaptador y lo añadimos al listview
            listViewListaDeLibros.setAdapter(adaptador);
        }
        else
        {
            listViewListaDeLibros.removeAllViewsInLayout();
        }

        BaseDatosLibros.close();
    }

    /**
     * Para probar nuestra aplicación añadimos algunos libros
     */
    public void crearPrimerosLibros() {

        try
        {
            BaseDatosLibros.openW();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            if (BaseDatosLibros.getCount() == 0)
            {
                BaseDatosLibros.insertLibro("Contra la ceguera", "Julio Anguita", "La Esfera", "9788499709321", "2013", "272", 1, 0, 3f, " Cuarenta años luchando por la utopía (Biografías y Memorias) Tapa blanda – 1 oct 2013 de Julio Anguita González Historiador y (Autor), Julio Flor Gamo (Autor).");

                BaseDatosLibros.insertLibro("Los amos del mundo", "Vicent Navarro", "Espasa Libros", "9788467008470", "2012", "736", 1, 0, 2f, "El resultado de una economía en manos de la oligarquía financiera es el alto endeudamiento, un empleo bajo mínimos y un debilitamiento del Estado del bienestar y de la calidad de vida de las personas, con el aumento de la pobreza y la desigualdad, y un mundo en donde disminuye la representatividad de las instituciones democráticas y la voz de la ciudadanía pierde fuerza.");

                BaseDatosLibros.insertLibro("Hombres Buenos", "Arturo Perez Reberte", "Alfaguara", "9788408141471", "2015", "496", 1, 0, 3f, "Novela del escritor español Arturo Pérez-Reverte publicada en el año 2015 por editorial Alfaguara. Narra las peripecias de dos españoles enviados a Francia por la Real Academia Española de la Lengua.");

                BaseDatosLibros.insertLibro("Momentos España", "Fernando Garcia de Cortazar", "Espasa Libros", "9788499922331", "2014", "336", 1, 0, 4f, "En el interior de un viejo libro aparece un sobre lacrado con una misteriosa inscripción a quien lo encuentre. En su interior un emocionante viaje a la historia de España: desde las colonias fenicias a la romanización, la invasión musulmana y la reconquista, el descubrimiento de América y el Imperio, su declive y la forma.");

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        BaseDatosLibros.close();
    }

    protected void onResume()
    {
        super.onResume();
        actualizaVista();
    }
}