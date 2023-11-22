package com.example.easycamp.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.easycamp.domain.CampamentoDto;
import com.example.easycamp.domain.UserDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    private static final String CAMPAMENTO_IMAGEN = "imagen";
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
    //tabla favoritos
    private static final String TABLE_FAVORITOS = "favoritos";
    private static final String FAVORITO_ID = "id";
    private static final String FAVORITO_USUARIO_ID = "usuario_id";
    private static final String FAVORITO_CAMPAMENTO_ID = "campamento_id";

    private static final String TABLE_INSCRITOS = "inscritos";
    private static final String INSCRITOS_ID = "id";
    private static final String INSCRITOS_USUARIO_ID = "usuario_id";
    private static final String INSCRITOS_CAMPAMENTO_ID = "campamento_id";

    //otros atributos
    private Context context;

    private static DBHelper db;
    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.d("MiApp", "Se crea la base de datos");
        this.db = this;
    }

    public static DBHelper getInstance(Context context){
        if(db == null){
            db = new DBHelper(context);
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de campamentos
        Log.d("MiApp", "Se crean la tablas");
        String createTableCampamentos = "CREATE TABLE " + TABLE_CAMPAMENTOS + " (" +
                CAMPAMENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAMPAMENTO_NOMBRE + " TEXT, " +
                CAMPAMENTO_DESCRIPCION + " TEXT, " +
                CAMPAMENTO_FECHA_INICIO + " TEXT, " +
                CAMPAMENTO_FECHA_FINAL + " TEXT, " +
                CAMPAMENTO_NUMERO_MAX_PARTICIPANTES + " INTEGER, " +
                CAMPAMENTO_NUMERO_APUNTADOS + " INTEGER, " +
                CAMPAMENTO_UBICACION + " TEXT, " +
                CAMPAMENTO_IMAGEN + " TEXT, " +
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
        String createTableFavoritos = "CREATE TABLE " + TABLE_FAVORITOS + " (" +
                FAVORITO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FAVORITO_USUARIO_ID + " INTEGER, " +
                FAVORITO_CAMPAMENTO_ID + " INTEGER, " +
                "FOREIGN KEY(" + FAVORITO_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + USUARIO_ID + "), " +
                "FOREIGN KEY(" + FAVORITO_CAMPAMENTO_ID + ") REFERENCES " + TABLE_CAMPAMENTOS + "(" + CAMPAMENTO_ID + "))";
        db.execSQL(createTableFavoritos);

        String createTableInscritos = "CREATE TABLE " + TABLE_INSCRITOS + " (" +
                INSCRITOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INSCRITOS_USUARIO_ID + " INTEGER, " +
                INSCRITOS_CAMPAMENTO_ID + " INTEGER, " +
                "FOREIGN KEY(" + INSCRITOS_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + USUARIO_ID + "), " +
                "FOREIGN KEY(" + INSCRITOS_CAMPAMENTO_ID + ") REFERENCES " + TABLE_CAMPAMENTOS + "(" + CAMPAMENTO_ID + "))";
        db.execSQL(createTableInscritos);

        // se cargan 3 de aventuras , 3 de naturaleza , 2 de deportes , 1 de arte y dos de ciencias
        insertarDatosDesdeJSON(context, db, TABLE_CAMPAMENTOS, "campamentos", "datos_iniciales.json");

        //se crean 3 usuarios de clientes y 3 de trabajadores
        insertarDatosUsuariosDesdeJSON(context, db, TABLE_USUARIOS, "usuarios", "datos_iniciales.json");



    }
    private void insertarDatosDesdeJSON(Context context, SQLiteDatabase db, String tableName, String jsonArrayName, String fileName) {
        Log.d("MiApp", "Se intenta poner los campamentos");
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
                values.put(CAMPAMENTO_NOMBRE, item.getString("nombre"));
                values.put(CAMPAMENTO_DESCRIPCION, item.getString("descripcion"));
                values.put(CAMPAMENTO_FECHA_INICIO, item.getString("fecha_inicio"));
                values.put(CAMPAMENTO_FECHA_FINAL, item.getString("fecha_final"));
                values.put(CAMPAMENTO_NUMERO_MAX_PARTICIPANTES, item.getInt("numero_max_participantes"));
                values.put(CAMPAMENTO_NUMERO_APUNTADOS, item.getInt("numero_apuntados"));
                values.put(CAMPAMENTO_UBICACION, item.getString("ubicacion"));
                values.put(CAMPAMENTO_IMAGEN, item.getString("imagen"));
                values.put(CAMPAMENTO_EDAD_MINIMA, item.getInt("edad_minima"));
                values.put(CAMPAMENTO_EDAD_MAXIMA, item.getInt("edad_maxima"));
                values.put(CAMPAMENTO_NUM_MONITORES, item.getInt("num_monitores"));
                values.put(CAMPAMENTO_PRECIO, item.getDouble("precio"));
                values.put(CAMPAMENTO_CATEGORIA, item.getString("categoria"));
                Log.d("MiApp", "Nuevo Campamento "+item.getString("nombre")+" "+item.getString("descripcion"));

                // Insertar los valores en la base de datos
                db.insert(tableName, null, values);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertarDatosUsuariosDesdeJSON(Context context, SQLiteDatabase db, String tableName, String jsonArrayName, String fileName) {
        Log.d("MiApp", "Se intenta poner los usuarios");
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
                values.put(USUARIO_NOMBRE_USUARIO, item.getString("nombre_usuario"));
                values.put(USUARIO_CONTRASENA, item.getString("contrasena"));
                values.put(USUARIO_TIPO, item.getString("tipo_usuario"));
                values.put(USUARIO_NOMBRE, item.getString("nombre"));
                values.put(USUARIO_APELLIDOS, item.getString("apellidos"));
                values.put(USUARIO_EDAD, item.getInt("edad"));
                Log.d("MiApp", "Nuevo Usuario  "+item.getString("nombre_usuario")+" "+item.getString("contrasena"));
                // Insertar los valores en la base de datos
                db.insert(tableName, null, values);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void agregarFavorito(long usuarioId, long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVORITO_USUARIO_ID, usuarioId);
        values.put(FAVORITO_CAMPAMENTO_ID, campamentoId);
        db.insert(TABLE_FAVORITOS, null, values);
        db.close();
    }
    public void quitarDeFavoritos(long usuarioId, long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = FAVORITO_USUARIO_ID + " = ? AND " + FAVORITO_CAMPAMENTO_ID + " = ?";
        String[] whereArgs = {String.valueOf(usuarioId), String.valueOf(campamentoId)};

        db.delete(TABLE_FAVORITOS, whereClause, whereArgs);

        db.close();
    }

    public List<CampamentoDto> obtenerFavoritosDeUsuario(long usuarioId) {
        List<CampamentoDto> campamentosFavoritos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_FAVORITOS +
                " INNER JOIN " + TABLE_CAMPAMENTOS +
                " ON " + TABLE_FAVORITOS + "." + FAVORITO_CAMPAMENTO_ID + " = " + TABLE_CAMPAMENTOS + "." + CAMPAMENTO_ID +
                " WHERE " + TABLE_FAVORITOS + "." + FAVORITO_USUARIO_ID + " = " + usuarioId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") CampamentoDto campamento = new CampamentoDto(
                        cursor.getLong(cursor.getColumnIndex(CAMPAMENTO_ID)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_DESCRIPCION)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_FECHA_INICIO)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_FECHA_FINAL)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_NUMERO_MAX_PARTICIPANTES)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_NUMERO_APUNTADOS)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_UBICACION)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_EDAD_MINIMA)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_EDAD_MAXIMA)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_NUM_MONITORES)),
                        cursor.getDouble(cursor.getColumnIndex(CAMPAMENTO_PRECIO)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_CATEGORIA)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_IMAGEN)),
                        true
                );
                campamentosFavoritos.add(campamento);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return campamentosFavoritos;
    }

    public List<CampamentoDto> obtenerInscritosDeUsuario(long usuarioId) {
        List<CampamentoDto> campamentosFavoritos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_INSCRITOS +
                " INNER JOIN " + TABLE_CAMPAMENTOS +
                " ON " + TABLE_INSCRITOS + "." + INSCRITOS_CAMPAMENTO_ID + " = " + TABLE_CAMPAMENTOS + "." + CAMPAMENTO_ID +
                " WHERE " + TABLE_INSCRITOS + "." + INSCRITOS_USUARIO_ID + " = " + usuarioId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") CampamentoDto campamento = new CampamentoDto(
                        cursor.getLong(cursor.getColumnIndex(CAMPAMENTO_ID)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_DESCRIPCION)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_FECHA_INICIO)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_FECHA_FINAL)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_NUMERO_MAX_PARTICIPANTES)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_NUMERO_APUNTADOS)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_UBICACION)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_EDAD_MINIMA)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_EDAD_MAXIMA)),
                        cursor.getInt(cursor.getColumnIndex(CAMPAMENTO_NUM_MONITORES)),
                        cursor.getDouble(cursor.getColumnIndex(CAMPAMENTO_PRECIO)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_CATEGORIA)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_IMAGEN)),
                        true
                );
                campamentosFavoritos.add(campamento);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return campamentosFavoritos;
    }
    @SuppressLint("Range")
    public UserDTO obtenerUsuarioPorCredenciales(String nombreUsuario, String contrasena) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define las columnas que deseas recuperar
        String[] columnas = {
                USUARIO_ID,
                USUARIO_NOMBRE_USUARIO,
                USUARIO_TIPO,
                USUARIO_NOMBRE,
                USUARIO_APELLIDOS,
                USUARIO_EDAD
        };

        // Define la cláusula WHERE para la consulta
        String whereClause = USUARIO_NOMBRE_USUARIO + " = ? AND " + USUARIO_CONTRASENA + " = ?";
        String[] whereArgs = {nombreUsuario, contrasena};
        Log.d("MiApp", "Intento inicio de sesion "+nombreUsuario+" "+contrasena);
        // Realiza la consulta en la base de datos
        Cursor cursor = db.query(TABLE_USUARIOS, columnas, whereClause, whereArgs, null, null, null);

        UserDTO usuarioDto = null;

        if (cursor.moveToFirst()) {
            // Si se encuentra el usuario, crea un objeto UsuarioDto
            usuarioDto = new UserDTO(
                    cursor.getLong(cursor.getColumnIndex(USUARIO_ID)),
                    cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE_USUARIO)),
                    cursor.getString(cursor.getColumnIndex(USUARIO_TIPO)),
                    cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(USUARIO_APELLIDOS)),
                    cursor.getInt(cursor.getColumnIndex(USUARIO_EDAD))
            );
        }

        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return usuarioDto;
    }



    public List<CampamentoDto> obtenerCampamentosConFavoritos(long usuarioId) {
        List<CampamentoDto> campamentosConFavoritos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CAMPAMENTOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorCampamentos = db.rawQuery(selectQuery, null);

        if (cursorCampamentos.moveToFirst()) {
            do {
                @SuppressLint("Range") long campamentoId = cursorCampamentos.getLong(cursorCampamentos.getColumnIndex(CAMPAMENTO_ID));
                boolean esFavorito = esCampamentoFavorito(usuarioId, campamentoId, db);

                @SuppressLint("Range") CampamentoDto campamento = new CampamentoDto(
                        cursorCampamentos.getLong(cursorCampamentos.getColumnIndex(CAMPAMENTO_ID)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_NOMBRE)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_DESCRIPCION)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_FECHA_INICIO)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_FECHA_FINAL)),
                        cursorCampamentos.getInt(cursorCampamentos.getColumnIndex(CAMPAMENTO_NUMERO_MAX_PARTICIPANTES)),
                        cursorCampamentos.getInt(cursorCampamentos.getColumnIndex(CAMPAMENTO_NUMERO_APUNTADOS)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_UBICACION)),
                        cursorCampamentos.getInt(cursorCampamentos.getColumnIndex(CAMPAMENTO_EDAD_MINIMA)),
                        cursorCampamentos.getInt(cursorCampamentos.getColumnIndex(CAMPAMENTO_EDAD_MAXIMA)),
                        cursorCampamentos.getInt(cursorCampamentos.getColumnIndex(CAMPAMENTO_NUM_MONITORES)),
                        cursorCampamentos.getDouble(cursorCampamentos.getColumnIndex(CAMPAMENTO_PRECIO)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_CATEGORIA)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_IMAGEN)),
                        esFavorito
                );
                campamentosConFavoritos.add(campamento);
            } while (cursorCampamentos.moveToNext());
        }

        cursorCampamentos.close();
        db.close();

        return campamentosConFavoritos;
    }

    private boolean esCampamentoFavorito(long usuarioId, long campamentoId, SQLiteDatabase db) {
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITOS +
                " WHERE " + FAVORITO_USUARIO_ID + " = " + usuarioId +
                " AND " + FAVORITO_CAMPAMENTO_ID + " = " + campamentoId;

        Cursor cursorFavoritos = db.rawQuery(selectQuery, null);

        boolean esFavorito = cursorFavoritos.getCount() > 0;

        cursorFavoritos.close();

        return esFavorito;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de la base de datos si es necesario

    }
}
