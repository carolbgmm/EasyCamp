package com.example.easycamp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "easyCamp.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla de campamentos
    private static final String TABLE_CAMPAMENTOS = "campamentos";
    // Columnas de la tabla de campamentos
    private static final String CAMPAMENTO_ID = "id";
    private static final String CAMPAMENTO_NOMBRE = "nombre";
    private static final String CAMPAMENTO_DESCRIPCION = "descripcion";
    private static final String CAMPAMENTO_FECHA_INICIO = "fecha_inicio";
    private static final String CAMPAMENTO_FECHA_FINAL = "fecha_final";
    private static final String CAMPAMENTO_NUMERO_MAX_PARTICIPANTES = "numero_max_participantes";
    private static final String CAMPAMENTO_NUMERO_APUNTADOS = "numero_apuntados";
    private static final String CAMPAMENTO_UBICACION = "ubicacion";
    private static final String CAMPAMENTO_EDAD_MINIMA = "edad_minima";
    private static final String CAMPAMENTO_EDAD_MAXIMA = "edad_maxima";
    private static final String CAMPAMENTO_NUM_MONITORES = "num_monitores";
    private static final String CAMPAMENTO_PRECIO = "precio";
    private static final String CAMPAMENTO_CATEGORIA = "categoria";

    // Nombre de la tabla de usuarios
    private static final String TABLE_USUARIOS = "usuarios";
    // Columnas de la tabla de usuarios
    private static final String USUARIO_ID = "id";
    private static final String USUARIO_NOMBRE_USUARIO = "nombre_usuario";
    private static final String USUARIO_CONTRASENA = "contrasena";
    private static final String USUARIO_TIPO = "tipo_usuario";
    private static final String USUARIO_NOMBRE = "nombre";
    private static final String USUARIO_APELLIDOS = "apellidos";
    private static final String USUARIO_EDAD = "edad";


    //otros atributos
    private Context context;
    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de campamentos
        String createTableCampamentos = "CREATE TABLE " + TABLE_CAMPAMENTOS + " (" +
                CAMPAMENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAMPAMENTO_NOMBRE + " TEXT, " +
                CAMPAMENTO_DESCRIPCION + " TEXT, " +
                CAMPAMENTO_FECHA_INICIO + " TEXT, " +
                CAMPAMENTO_FECHA_FINAL + " TEXT, " +
                CAMPAMENTO_NUMERO_MAX_PARTICIPANTES + " INTEGER, " +
                CAMPAMENTO_NUMERO_APUNTADOS + " INTEGER, " +
                CAMPAMENTO_UBICACION + " TEXT, " +
                CAMPAMENTO_EDAD_MINIMA + " INTEGER, " +
                CAMPAMENTO_EDAD_MAXIMA + " INTEGER, " +
                CAMPAMENTO_NUM_MONITORES + " INTEGER, " +
                CAMPAMENTO_PRECIO + " REAL, " +
                CAMPAMENTO_CATEGORIA + " TEXT)";
        db.execSQL(createTableCampamentos);

        // Crear la tabla de usuarios
        String createTableUsuarios = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USUARIO_NOMBRE_USUARIO + " TEXT, " +
                USUARIO_CONTRASENA + " TEXT, " +
                USUARIO_TIPO + " TEXT, " +
                USUARIO_NOMBRE + " TEXT, " +
                USUARIO_APELLIDOS + " TEXT, " +
                USUARIO_EDAD + " INTEGER)";
        db.execSQL(createTableUsuarios);
        // Cargar datos de usuarios desde el archivo JSON

        // se cargan 3 de aventuras , 3 de naturaleza , 2 de deportes , 1 de arte y dos de ciencias
        insertarDatosDesdeJSON(context, db, TABLE_CAMPAMENTOS, "campamentos", "datos_iniciales.json");

        //se crean 3 usuarios de clientes y 3 de trabajadores
        insertarDatosDesdeJSON(context, db, TABLE_USUARIOS, "usuarios", "datos_iniciales.json");



    }
    private void insertarDatosDesdeJSON(Context context, SQLiteDatabase db, String tableName, String jsonArrayName, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonArrayName);

            for (int i = 0; i < jsonArray.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject item = jsonArray.getJSONObject(i);

                // Agregar cada columna y valor al ContentValues
                values.put("nombre", item.getString("nombre"));
                values.put("descripcion", item.getString("descripcion"));
                // ... (agregar las demás columnas según sea necesario)

                // Insertar los valores en la base de datos
                db.insert(tableName, null, values);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de la base de datos si es necesario
        // Aquí puedes agregar lógica para actualizar tus tablas si cambias la estructura en futuras versiones.
    }
}
