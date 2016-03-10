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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listViewListaDeLibros;
    private Cursor cursor;
    LibrosDB BaseDatosLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BOTÓN +
        FloatingActionButton btn_MAS = (FloatingActionButton) findViewById(R.id.btn_MAS);
        btn_MAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Botón más pulsado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VistaLibro.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });

        BaseDatosLibros = new LibrosDB(this);
        crearPrimerosLibros();
        ActualizaVista();
        listViewListaDeLibros.setOnItemClickListener(this);//Click en cada linea de la lista

    }

    /**
     * Actualiza la vista con los datos actuales de la base de datos
     */
    protected void onResume(){
        super.onResume();
        ActualizaVista();
    }

    /**
     * Click en cada fila de la lista, le pasamos el ID del libro
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, VistaLibro.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * Carga todos los libros guardados en la lista
     */
    public void ActualizaVista() {
        listViewListaDeLibros = (ListView) findViewById(R.id.lv_listaLibros);

        try {
            BaseDatosLibros.openR();//Abrimos la Bd en modo lectura
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = BaseDatosLibros.getLibros();//Guardamos en el cursor los libros

        if (cursor.moveToFirst()) {
            AdaptadorLista adaptador = new AdaptadorLista(this, cursor, 0);

            listViewListaDeLibros.setAdapter(adaptador);//Pasamos al listview los libros del cursor
        }else{
            listViewListaDeLibros.removeAllViewsInLayout();//Si no hay libros, borra sus información en el layout
        }

        BaseDatosLibros.close();
    }

    /**
     * Añade a la base de datos todos los libros iniciales
     */
    public void crearPrimerosLibros() {

        try {
            BaseDatosLibros.openW();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (BaseDatosLibros.getCount() == 0) {
                BaseDatosLibros.insertLibro("CASI SIN QUERER", "DEFREDS", "FRIDA", "9788494398902", "2015",
                        "172", 1, 0, 1f, "El amor algunas veces es tan complicado como impredecible. Pero al final lo que más valoramos son los detalles más simples, los más bonitos, los que llegan sin avisar. Y a la hora de escribir sobre sentimientos, no hay nada más limpio que hacerlo desde el corazón. Y eso hace Defreds en este libro.");

                BaseDatosLibros.insertLibro("PALMERAS EN LA NIEVE", "LUZ GABAS", "TEMAS DE HOY", "9788499985138", "2015",
                        "736", 1, 0, 2f, "Es 1953 Kilian abandona la nieve de la montaña oscense para iniciar junto a su hermano, Jacobo, el viaje de ida hacia una tierra desconocida, lejana y exótica, la isla de Fernando Poo. En las entrañas de este territorio exuberante y seductor, le espera su padre, un veterano de la finca Sampaka, el lugar donde se cultiva y tuesta uno de los mejores cacaos del mundo.En esa tierra eternamente verde, cálida y voluptuosa, los jóvenes hermanos descubren la ligereza de la vida social de la colonia en comparación con una España encorsetada y gris; comparten el duro trabajo necesario para conseguir el cacao perfecto de la finca Sampaka; aprenden las diferencias y similitudes culturales entre coloniales y autóctonos; y conocen el significado de la amistad, la pasión, el amor y el odio. Pero uno de ellos cruzará una línea prohibida e invisible y se enamorará perdidamente de una nativa. Su amor por ella, enmarcado en unas complejas circunstancias históricas");

                BaseDatosLibros.insertLibro("LA CHICA DEL TREN", "PAULA HAWKINS", "PLANETA", "9788408141471", "2015",
                        "496", 1, 0, 3f, "El bestseller que arrasa en las listas de más vendidos en EE. UU. y Reino Unido. ¿Estabas en el tren de las 8.04? ¿Viste algo sospechoso? Rachel, sí. Rachel toma siempre el tren de las 8.04 h. Cada mañana lo mismo: el mismo paisaje, las mismas casas? y la misma parada en la señal roja. Son solo unos segundos, pero le permiten observar a una pareja desayunando tranquilamente en su terraza. Siente que los conoce y se inventa unos nombres para ellos: Jess y Jason. Su vida es perfecta, no como la suya. Pero un día ve algo. Sucede muy deprisa, pero es suficiente. ¿Y si Jess y Jason no son tan felices como ella cree? ¿Y si nada es lo que parece? Tú no la conoces. Ella a ti, sí.");

                BaseDatosLibros.insertLibro("LA GRAN APUESTA", "MICHAEL LEWIS", "DEBATE", "9788499922331", "2015",
                        "336", 1, 0, 4f, "La gran apuesta, de Michael Lewis, autor de otras obras con la economía financiera de fondo como El poquer del mentiroso o Boomerang: viajes al nuevo tercer mundo europeo, es la excepcional crónica del crash inmobiliario que originó la mayor crisis de los últimos 70 años. Michael Lewis es autor de algunos de los libros más vendidos en las dos últimas décadas en Estados Unidos, como The Blind Side, recientemente adaptada al cine y protagonizada por Sandra Bullock; Moneyball, con Brad Pitt en el papel principal; La gran apuesta ha encabezado durante ocho meses las principales listas de ventas.");

                BaseDatosLibros.insertLibro("EL MARCIANO", "ANDY WEIR", "DEBATE", "9788499922331", "2015",
                        "336", 1, 0, 5f, "La gran apuesta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BaseDatosLibros.close();
    }
}