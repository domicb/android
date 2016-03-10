package com.example.domingo.androidlibros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class MisLibrosDDBB {

    static final String TAG = "MisLibrosDDBB";
    static final String DATABASE_NAME = "mislibros";
    static final String DATABASE_TABLE = "libros";
    static final int DATABASE_VERSION = 1;
    DatabaseHelper helper;
    SQLiteDatabase mislibros;
    final Context context;
    public static final String AUTOR = "autor";
    public static final String EDITORIAL = "editorial";
    public static final String EBOOK = "ebook";
    public static final String LEIDO = "leido";
    public static final String NOTA = "nota";
    public static final String ROW_ID = "_id";
    public static final String TITULO = "titulo";
    public static final String RESUMEN = "resumen";
    public static final String ISBN = "isbn";
    public static final String ANIO = "anio";
    public static final String PAGINAS = "paginas";

    //definimos la estructura de la base de datos y la guardamos como variable u objeto global
    private static final String DATABASE_CREATE = "create table libros (_id integer primary key autoincrement, " + TITULO + " text, " + AUTOR + " text, " + EDITORIAL + " text, " + ISBN + " text, " + ANIO + " text, " + PAGINAS + " text, " + EBOOK + " integer, " + LEIDO + " integer, " + NOTA + " float, " + RESUMEN + " text " + ");";

    private class DatabaseHelper extends SQLiteOpenHelper {
        //sobrecargamos el constructor de nuevo helper con la estructura anteiormente creada
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        //y procedemos a crear la ddbb
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }
        //con upgrade actualizamos el contenido
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            onCreate(db);
        }
    }

    public MisLibrosDDBB(Context ctx) {
        this.context = ctx;
        helper = new DatabaseHelper(context);
    }

    /**
     * Añade un registro de libro a la base de datos
     */
    public long insertLibro(String titulo, String autor, String editorial, String isbn, String anio, String paginas, Integer ebook, Integer leido, Float nota, String resumen) {
        ContentValues campos = new ContentValues();

        campos.put(TITULO, titulo);
        campos.put(AUTOR, autor);
        campos.put(EDITORIAL, editorial);
        campos.put(ISBN, isbn);
        campos.put(ANIO, anio);
        campos.put(PAGINAS, paginas);
        campos.put(EBOOK, ebook);
        campos.put(LEIDO, leido);
        campos.put(NOTA, nota);
        campos.put(RESUMEN, resumen);

        return this.mislibros.insert(DATABASE_TABLE, null, campos);
    }


    public boolean deleteLibro(long Id) {
        return this.mislibros.delete(DATABASE_TABLE, ROW_ID + "=" + Id, null) > 0;
    }

    /**
     * Actualiza el registro de un libro
     */
    public boolean updateLibro(long rowId, String titulo, String autor, String editorial, String isbn, String anio, String paginas, Integer ebook, Integer leido, Float nota, String resumen) {
        ContentValues campos = new ContentValues();

        campos.put(TITULO, titulo);
        campos.put(AUTOR, autor);
        campos.put(EDITORIAL, editorial);
        campos.put(ISBN, isbn);
        campos.put(ANIO, anio);
        campos.put(PAGINAS, paginas);
        campos.put(EBOOK, ebook);
        campos.put(LEIDO, leido);
        campos.put(NOTA, nota);
        campos.put(RESUMEN, resumen);

        return this.mislibros.update(DATABASE_TABLE, campos, ROW_ID + "=" + rowId, null) > 0;
    }

    /**
     * Abre la base de datos en modo escritura
     */
    public MisLibrosDDBB openW() throws SQLException {
        mislibros = this.helper.getWritableDatabase();
        return this;
    }

    /**
     * Abre la base de datos en modo lectura
     */
    public MisLibrosDDBB openR() throws SQLException {
        mislibros = helper.getReadableDatabase();
        return this;
    }
    /**
     * Devuelve todos los libros guardados en la base de datos
     */
    public Cursor getLibros() {

        return this.mislibros.rawQuery("SELECT * FROM libros", null);
    }

    //recoge todos los libros del libro con el id sobrecargado y retorna sus datos en forma de cursor para poder mostrarlo en el layout
    public Cursor getUnLibro(long rowId) {

        Cursor cursor = mislibros.rawQuery("SELECT * FROM libros" + " WHERE _id = " + rowId, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Devuelve el número de registros que tiene la tabla libros
     */
    public int getCount() throws SQLException {

        Cursor cursor = mislibros.rawQuery("select count(*) from libros ", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    //definimos un metodo para cerrar
    public void close() {
        this.helper.close();
    }
}