package com.example.easycamp.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.easycamp.domain.CampamentoDto;
import com.example.easycamp.domain.FavoritoDTO;
import com.example.easycamp.domain.HijoDTO;
import com.example.easycamp.domain.InscripcionDTO;
import com.example.easycamp.domain.LoggedUserDTO;
import com.example.easycamp.domain.TareaDTO;
import com.example.easycamp.domain.UserDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "easyCamp.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla de campamentos
    private static final String TABLE_CAMPAMENTOS = "campamentos";
    // Columnas de la tabla de campamentos
    private static final String CAMPAMENTO_ID = "id";
    private static final String CAMPAMENTO_NOMBRE = "nombre_campamento";
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
    private static final String CAMPAMENTO_LATITUD = "latitud";
    private static final String CAMPAMENTO_LONGUITUD = "longuitud";

    private static final String CAMPAMENTO_COORDINADOR = "idCoordinador";

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

    public static final String TABLE_HIJOS = "hijos";
    public static final String HIJO_ID = "id";
    public static final String HIJO_NOMBRE = "nombre_hijo";
    public static final String HIJO_APELLIDOS = "apellidos";
    public static final String HIJO_EDAD = "edad";
    public static final String HIJO_OBSERVACIONES = "observaciones";
    public static final String HIJO_USUARIO_ID = "usuario_id";

    // Nombre de la tabla de inscritos
    private static final String TABLE_INSCRITOS = "inscritos";

    // Columnas de la tabla de inscritos
    private static final String INSCRITOS_ID = "id";
    private static final String INSCRITOS_HIJO_ID = "hijo_id";
    private static final String INSCRITOS_CAMPAMENTO_ID = "campamento_id";

    // Nombre de la tabla de tareas
    private static final String TABLE_TAREAS = "tareas";
    // Columnas de la tabla de tareas
    private static final String TAREA_ID = "id";
    private static final String USUARIO_TAREA_NOMBRE = "nombre_usuario_tarea";
    private static final String TAREA_TITULO = "titulo_tarea";
    private static final String TAREA_DESCRIPCION = "descripcion_tarea";
    private static final String TAREA_FECHA = "fecha_tarea";

    //tabla tareas hechas TICK
    private static final String TABLE_TICK = "tick";
    private static final String TICK_ID = "id";
    private static final String TICK_USUARIO_NOMBRE = "usuario_nombre_tick";
    private static final String TICK_CAMPAMENTO_NOMBRE = "campamento_nombre_tick";
    private static final String TABLE_INSCRITOS_TRABAJADOR = "inscritos_trabajador";

    // Columnas de la tabla de inscritos
    private static final String INSCRITOS_TRABAJADOR_ID = "id_inscritos_trabajador";
    private static final String INSCRITOS_TRABAJADOR_TRABAJADOR_ID = "trabajador_id";
    private static final String INSCRITOS_TRABAJADOR_CAMPAMENTO_ID = "campamento_id";

   DatabaseReference mDataBase;
    //otros atributos
    private Context context;

    private static DBHelper db;
    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        mDataBase= FirebaseDatabase.getInstance().getReference();
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
                CAMPAMENTO_CATEGORIA + " TEXT, " +
                CAMPAMENTO_LATITUD+ " REAL, " +
                CAMPAMENTO_COORDINADOR+"TEXT, "+
                CAMPAMENTO_LONGUITUD + " REAL)";
        db.execSQL(createTableCampamentos);

        // Crear la tabla de usuarios
        String createTableUsuarios = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                USUARIO_ID + " TEXT PRIMARY KEY , " +
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

        String createTableHijos = "CREATE TABLE " + TABLE_HIJOS + " (" +
                HIJO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HIJO_NOMBRE + " TEXT, " +
                HIJO_APELLIDOS + " TEXT, " +
                HIJO_EDAD + " INTEGER, " +
                HIJO_OBSERVACIONES + " TEXT, " +
                HIJO_USUARIO_ID + " INTEGER, " +
                "FOREIGN KEY(" + HIJO_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + USUARIO_ID + "))";
        db.execSQL(createTableHijos);

        String createTableInscritos = "CREATE TABLE " + TABLE_INSCRITOS + " (" +
                INSCRITOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INSCRITOS_HIJO_ID + " INTEGER, " +  // Cambiado a TEXT
                INSCRITOS_CAMPAMENTO_ID + " INTEGER, " +  // Cambiado a TEXT
                "FOREIGN KEY(" + INSCRITOS_HIJO_ID + ") REFERENCES " + TABLE_HIJOS + "(" + HIJO_ID + "), " +
                "FOREIGN KEY(" + INSCRITOS_CAMPAMENTO_ID + ") REFERENCES " + TABLE_CAMPAMENTOS + "(" + CAMPAMENTO_ID + "))";
        db.execSQL(createTableInscritos);


        // Crear la tabla de tareas
        String createTableTareas = "CREATE TABLE " + TABLE_TAREAS + " (" +
                TAREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TAREA_TITULO + " TEXT, " +
                TAREA_DESCRIPCION + " TEXT, " +
                TAREA_FECHA + " TEXT, " +
                USUARIO_TAREA_NOMBRE + " TEXT)";
        db.execSQL(createTableTareas);

        String createTableTick = "CREATE TABLE " + TABLE_TICK + " (" +
                TICK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TICK_USUARIO_NOMBRE + " INTEGER, " +
                TICK_CAMPAMENTO_NOMBRE + " INTEGER, " +
                "FOREIGN KEY(" + TICK_USUARIO_NOMBRE + ") REFERENCES " + TABLE_USUARIOS + "(" + USUARIO_NOMBRE + "), " +
                "FOREIGN KEY(" + TICK_CAMPAMENTO_NOMBRE + ") REFERENCES " + TABLE_CAMPAMENTOS + "(" + CAMPAMENTO_NOMBRE + "))";
        db.execSQL(createTableTick);

        String createTableInscritosTrabajador = "CREATE TABLE " + TABLE_INSCRITOS_TRABAJADOR + " (" +
                INSCRITOS_TRABAJADOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INSCRITOS_TRABAJADOR_TRABAJADOR_ID + " INTEGER, " +  // Cambiado a TEXT
                INSCRITOS_CAMPAMENTO_ID + " INTEGER, " +  // Cambiado a TEXT
                "FOREIGN KEY(" + INSCRITOS_TRABAJADOR_TRABAJADOR_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + USUARIO_ID + "), " +
                "FOREIGN KEY(" + INSCRITOS_CAMPAMENTO_ID + ") REFERENCES " + TABLE_CAMPAMENTOS + "(" + CAMPAMENTO_ID + "))";
        db.execSQL(createTableInscritosTrabajador );

        insertarDatosTareasDesdeJSON(context, db, TABLE_TAREAS, "tareas", "datos_iniciales.json");

        insertarDatosInscritosDesdeJSON(context, db, TABLE_INSCRITOS, "inscritos", "datos_iniciales.json");


    }

    private void insertarDatosTareasDesdeJSON(Context context, SQLiteDatabase db, String tableName, String jsonArrayName, String fileName) {
        Log.d("MiApp", "Se intenta poner las tareas");
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
                values.put(TAREA_TITULO, item.getString("titulo_tarea"));
                values.put(TAREA_DESCRIPCION, item.getString("descripcion_tarea"));
                values.put(TAREA_FECHA, item.getString("fecha_tarea"));
                values.put(USUARIO_TAREA_NOMBRE, item.getString("nombre_usuario_tarea"));
                Log.d("MiApp", "Nueva Tarea  "+item.getString("nombre_usuario_tarea")+" "+item.getString("titulo_tarea"));
                // Insertar los valores en la base de datos
                db.insert(tableName, null, values);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertarDatosInscritosDesdeJSON(Context context, SQLiteDatabase db, String tableName, String jsonArrayName, String fileName) {
        Log.d("MiApp", "Se intenta poner los inscritos");
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
                values.put(INSCRITOS_HIJO_ID, item.getString(INSCRITOS_HIJO_ID));
                values.put(INSCRITOS_CAMPAMENTO_ID, item.getString(INSCRITOS_CAMPAMENTO_ID));
                Log.d("MiApp", "Nuevo inscrito  "+item.getString(INSCRITOS_HIJO_ID)+" "+item.getString(INSCRITOS_CAMPAMENTO_ID));
                // Insertar los valores en la base de datos
                db.insert(tableName, null, values);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public long crearHijo(HijoDTO hijo,String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HIJO_NOMBRE, hijo.getNombre());
        values.put(HIJO_APELLIDOS, hijo.getApellidos());
        values.put(HIJO_EDAD, hijo.getEdad());
        values.put(HIJO_OBSERVACIONES, hijo.getObservaciones());
        values.put(HIJO_USUARIO_ID, id);

        long resultado = db.insert(TABLE_HIJOS, null, values);
        db.close();
        return resultado;
    }

    public long inscribirHijo(long hijoId,long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INSCRITOS_HIJO_ID, hijoId);
        values.put(INSCRITOS_CAMPAMENTO_ID, campamentoId);

        long resultado = db.insert(TABLE_INSCRITOS, null, values);
        db.close();
        return resultado;
    }

    public long inscribirUsuario(String userId,long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INSCRITOS_TRABAJADOR_TRABAJADOR_ID, userId);
        values.put(INSCRITOS_TRABAJADOR_CAMPAMENTO_ID, campamentoId);

        long resultado = db.insert(TABLE_INSCRITOS_TRABAJADOR, null, values);
        db.close();
        return resultado;
    }

    public void desInscribirUsuario(String userId, long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INSCRITOS_TRABAJADOR, INSCRITOS_TRABAJADOR_TRABAJADOR_ID + "=? AND " + INSCRITOS_TRABAJADOR_CAMPAMENTO_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(campamentoId)});
        db.close();

        // Eliminar el favorito de Firebase
        DatabaseReference favoritosReference = mDataBase.child(TABLE_INSCRITOS_TRABAJADOR);
        favoritosReference.child(userId + "_" + campamentoId).removeValue();
    }

    public void desInscribirHijos(long hijoId, long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INSCRITOS, INSCRITOS_HIJO_ID + "=? AND " + INSCRITOS_CAMPAMENTO_ID + "=?",
                new String[]{String.valueOf(hijoId), String.valueOf(campamentoId)});
        db.close();

        // Eliminar el favorito de Firebase
        DatabaseReference favoritosReference = mDataBase.child(TABLE_INSCRITOS);
        favoritosReference.child(hijoId + "_" + campamentoId).removeValue();
    }

    // Método para obtener la lista de hijos dado el ID de un usuario
    @SuppressLint("Range")
    public List<HijoDTO> obtenerHijosPorUsuario(String idUsuario) {
        List<HijoDTO> listaHijos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HIJOS +
                " WHERE " + HIJO_USUARIO_ID + " = '" + idUsuario + "'";


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HijoDTO hijo = new HijoDTO(
                        cursor.getLong(cursor.getColumnIndex(HIJO_ID)),
                        cursor.getString(cursor.getColumnIndex(HIJO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(HIJO_APELLIDOS)),
                        cursor.getInt(cursor.getColumnIndex(HIJO_EDAD)),
                        cursor.getString(cursor.getColumnIndex(HIJO_OBSERVACIONES))
                );

                listaHijos.add(hijo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaHijos;
    }



    public void agregarFavorito(String usuarioId, long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVORITO_USUARIO_ID, usuarioId);
        values.put(FAVORITO_CAMPAMENTO_ID, campamentoId);
        db.insert(TABLE_FAVORITOS, null, values);
        db.close();

        // Agregar el favorito en Firebase
        DatabaseReference favoritosReference = mDataBase.child("favoritos");
        FavoritoDTO favoritoFirebase = new FavoritoDTO(usuarioId, campamentoId);
        favoritosReference.child(usuarioId + "_" + campamentoId).setValue(favoritoFirebase);
    }

    public void quitarDeFavoritos(String usuarioId, long campamentoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITOS, FAVORITO_USUARIO_ID + "=? AND " + FAVORITO_CAMPAMENTO_ID + "=?",
                new String[]{usuarioId, String.valueOf(campamentoId)});
        db.close();

        // Eliminar el favorito de Firebase
        DatabaseReference favoritosReference = mDataBase.child("favoritos");
        favoritosReference.child(usuarioId + "_" + campamentoId).removeValue();
    }

    public List<InscripcionDTO> getInscripcionesCampamento(long campamentoId) {
        List<InscripcionDTO> inscripciones = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_INSCRITOS +
                " WHERE " + TABLE_INSCRITOS + "." + INSCRITOS_CAMPAMENTO_ID + " = " + campamentoId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") InscripcionDTO inscripcion = new InscripcionDTO(
                        cursor.getLong(cursor.getColumnIndex(INSCRITOS_ID)),
                        cursor.getLong(cursor.getColumnIndex(INSCRITOS_HIJO_ID)),
                        cursor.getLong(cursor.getColumnIndex(INSCRITOS_CAMPAMENTO_ID))
                );
                inscripciones.add(inscripcion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return inscripciones;
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
                        true,
                        cursor.getFloat(cursor.getColumnIndex(CAMPAMENTO_LATITUD)),
                        cursor.getFloat(cursor.getColumnIndex(CAMPAMENTO_LONGUITUD)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_COORDINADOR))


                        );
                campamentosFavoritos.add(campamento);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return campamentosFavoritos;
    }

    public List<TareaDTO> obtenerTareasDeUsuario(String usuarioNombre) {
        List<TareaDTO> tareas = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TAREAS +
                " WHERE " + TABLE_TAREAS + "." + USUARIO_TAREA_NOMBRE + " = '" + usuarioNombre + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") TareaDTO tarea = new TareaDTO(
                        cursor.getLong(cursor.getColumnIndex(TAREA_ID)),
                        cursor.getString(cursor.getColumnIndex(TAREA_TITULO)),
                        cursor.getString(cursor.getColumnIndex(TAREA_DESCRIPCION)),
                        cursor.getString(cursor.getColumnIndex(TAREA_FECHA)),
                        cursor.getString(cursor.getColumnIndex(USUARIO_TAREA_NOMBRE)),
                        true
                );
                tareas.add(tarea);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tareas;
    }

    public List<CampamentoDto> obtenerInscritosDeUsuario(String usuarioID) {
        List<CampamentoDto> campamentosInscritos = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT ("+ TABLE_CAMPAMENTOS +"." + CAMPAMENTO_ID + ")," + CAMPAMENTO_NOMBRE + ","
                + CAMPAMENTO_DESCRIPCION + "," + CAMPAMENTO_FECHA_INICIO + "," + CAMPAMENTO_FECHA_FINAL + ","
                + CAMPAMENTO_NUMERO_MAX_PARTICIPANTES + "," + CAMPAMENTO_NUMERO_APUNTADOS + "," + CAMPAMENTO_UBICACION + ","
                + CAMPAMENTO_COORDINADOR+"," + CAMPAMENTO_EDAD_MINIMA + "," + CAMPAMENTO_EDAD_MAXIMA + ","
                + CAMPAMENTO_NUM_MONITORES + "," + CAMPAMENTO_PRECIO + "," + CAMPAMENTO_CATEGORIA + ","
                + CAMPAMENTO_IMAGEN + "," + CAMPAMENTO_LATITUD + "," + CAMPAMENTO_LONGUITUD + "," + CAMPAMENTO_COORDINADOR
                +"  FROM " + TABLE_CAMPAMENTOS +
                " INNER JOIN " + TABLE_INSCRITOS +
                " ON " + TABLE_INSCRITOS + "." +INSCRITOS_CAMPAMENTO_ID + " = " + TABLE_CAMPAMENTOS + "." + CAMPAMENTO_ID +
                " INNER JOIN " + TABLE_HIJOS +
                " ON " + TABLE_INSCRITOS + "." +INSCRITOS_HIJO_ID + " = " + TABLE_HIJOS + "." + HIJO_ID +
                " WHERE " + TABLE_HIJOS + "." + HIJO_USUARIO_ID + " = '" + usuarioID + "';";



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
                        true,
                        cursor.getFloat(cursor.getColumnIndex(CAMPAMENTO_LATITUD)),
                        cursor.getFloat(cursor.getColumnIndex(CAMPAMENTO_LONGUITUD)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_COORDINADOR))
                );
                campamentosInscritos.add(campamento);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return campamentosInscritos;
    }

    public List<HijoDTO> obtenerInscritosDeCampamento(long campamentoID) {
        List<HijoDTO> hijosInscritos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_HIJOS +
                " INNER JOIN " + TABLE_INSCRITOS +
                " ON " + TABLE_INSCRITOS + "." + INSCRITOS_HIJO_ID + " = " + TABLE_HIJOS + "." + HIJO_ID +
                " WHERE " + TABLE_INSCRITOS + "." + INSCRITOS_CAMPAMENTO_ID + " = '" + campamentoID + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") HijoDTO hijo = new HijoDTO(
                        cursor.getLong(cursor.getColumnIndex(HIJO_ID)),
                        cursor.getString(cursor.getColumnIndex(HIJO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(HIJO_APELLIDOS)),
                        cursor.getInt(cursor.getColumnIndex(HIJO_EDAD)),
                        cursor.getString(cursor.getColumnIndex(HIJO_OBSERVACIONES))
                );
                hijosInscritos.add(hijo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return hijosInscritos;
    }

    public List<CampamentoDto> obtenerInscritosDeTrabajador(String usuarioID) {
        List<CampamentoDto> campamentosInscritos = new ArrayList<>();

        String selectQuery = "SELECT *  FROM " + TABLE_CAMPAMENTOS +
                " INNER JOIN " + TABLE_INSCRITOS_TRABAJADOR +
                " ON " + TABLE_INSCRITOS_TRABAJADOR + "." +INSCRITOS_TRABAJADOR_CAMPAMENTO_ID + " = " + TABLE_CAMPAMENTOS + "." + CAMPAMENTO_ID +
                " WHERE " + TABLE_INSCRITOS_TRABAJADOR + "." + INSCRITOS_TRABAJADOR_TRABAJADOR_ID + " = '" + usuarioID + "';";

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
                        true,
                        cursor.getFloat(cursor.getColumnIndex(CAMPAMENTO_LATITUD)),
                        cursor.getFloat(cursor.getColumnIndex(CAMPAMENTO_LONGUITUD)),
                        cursor.getString(cursor.getColumnIndex(CAMPAMENTO_COORDINADOR))
                );
                campamentosInscritos.add(campamento);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return campamentosInscritos;
    }

    @SuppressLint("Range")
    public UserDTO obtenerUsuarioPorCredenciales(String nombreUsuario, String contrasena) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserDTO usuario = null;
        Log.d("MiApp", "intentando obtener un usuario con  " +  nombreUsuario + " " + contrasena);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE " + USUARIO_NOMBRE_USUARIO + " = ? AND " + USUARIO_CONTRASENA + " = ?", new String[]{nombreUsuario, contrasena});

        if (cursor.moveToFirst()) {
            usuario = new UserDTO();
            usuario.setId(cursor.getString(cursor.getColumnIndex(USUARIO_ID)));
            usuario.setNombreUsuario(cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE_USUARIO)));
            usuario.setContrasena(cursor.getString(cursor.getColumnIndex(USUARIO_CONTRASENA)));
            usuario.setTipoUsuario(cursor.getString(cursor.getColumnIndex(USUARIO_TIPO)));
            usuario.setNombre(cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE)));
            usuario.setApellidos(cursor.getString(cursor.getColumnIndex(USUARIO_APELLIDOS)));
            usuario.setEdad(cursor.getInt(cursor.getColumnIndex(USUARIO_EDAD)));
        }

        cursor.close();
        db.close();
        if(usuario!=null){
            Log.d("MiApp", "Se encontro  " +  usuario.toString());

        }else{
            Log.d("MiApp", "No se encontro a nadie");
        }
        return usuario;
    }




    public List<CampamentoDto> obtenerCampamentosConFavoritos(String usuarioId) {
        List<CampamentoDto> campamentosConFavoritos = new ArrayList<>();
        sincronizarCampamentos();
        sincronizarFavoritos();
        String selectQuery = "SELECT * FROM " + TABLE_CAMPAMENTOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorCampamentos = db.rawQuery(selectQuery, null);

        if (cursorCampamentos.moveToFirst()) {
            do {
                @SuppressLint("Range") long campamentoId = cursorCampamentos.getLong(cursorCampamentos.getColumnIndex(CAMPAMENTO_ID));
                boolean esFavorito = esCampamentoFavorito(usuarioId, campamentoId);

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
                        esFavorito,
                        cursorCampamentos.getFloat(cursorCampamentos.getColumnIndex(CAMPAMENTO_LATITUD)),
                        cursorCampamentos.getFloat(cursorCampamentos.getColumnIndex(CAMPAMENTO_LONGUITUD)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_COORDINADOR))
                );
                campamentosConFavoritos.add(campamento);
            } while (cursorCampamentos.moveToNext());
        }
        cursorCampamentos.close();
        db.close();

        return campamentosConFavoritos;
    }

    public List<CampamentoDto> obtenerCampamentosCon(String usuarioId, String query) {
        String selectQuery = "SELECT * FROM " + TABLE_CAMPAMENTOS + " WHERE " + CAMPAMENTO_NOMBRE + " LIKE '%" + query + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorCampamentos = db.rawQuery(selectQuery, null);

        List<CampamentoDto> campamentosConFavoritos = getListaCampamentosConFavoritos(cursorCampamentos, usuarioId,db);

        cursorCampamentos.close();
        db.close();

        return campamentosConFavoritos;
    }
    private List<CampamentoDto> getListaCampamentosConFavoritos(Cursor cursorCampamentos, String usuarioId, SQLiteDatabase db){
        List<CampamentoDto> campamentos = new ArrayList<>();
        if (cursorCampamentos.moveToFirst()) {
            do {
                @SuppressLint("Range") long campamentoId = cursorCampamentos.getLong(cursorCampamentos.getColumnIndex(CAMPAMENTO_ID));
                boolean esFavorito = esCampamentoFavorito(usuarioId, campamentoId);

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
                        esFavorito,
                        cursorCampamentos.getFloat(cursorCampamentos.getColumnIndex(CAMPAMENTO_LATITUD)),
                        cursorCampamentos.getFloat(cursorCampamentos.getColumnIndex(CAMPAMENTO_LONGUITUD)),
                        cursorCampamentos.getString(cursorCampamentos.getColumnIndex(CAMPAMENTO_COORDINADOR))
                );
                campamentos.add(campamento);
            } while (cursorCampamentos.moveToNext());
        }
        return campamentos;
    }

    public boolean esCampamentoFavorito(String usuarioId, long campamentoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean esFavorito = false;

        try {
            // Utiliza marcadores de posición en la consulta para evitar problemas de SQL injection
            String consulta = "SELECT * FROM " + TABLE_FAVORITOS + " WHERE " +
                    FAVORITO_USUARIO_ID + " = ? AND " + FAVORITO_CAMPAMENTO_ID + " = ?";
            String[] argumentos = {usuarioId, String.valueOf(campamentoId)};

            Cursor cursor = db.rawQuery(consulta, argumentos);

            if (cursor != null && cursor.moveToFirst()) {
                // Si hay al menos una fila, el campamento es favorito
                esFavorito = true;
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return esFavorito;
    }




    // Método para actualizar un usuario
    public void actualizarUsuario(UserDTO usuario, String nombre, String apellidos, String edad) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuarios").child(String.valueOf(usuario.getId()));
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setEdad(Integer.parseInt(edad));

        Map<String, Object> actualizacionUsuario = new HashMap<>();

        actualizacionUsuario.put("nombre", nombre);
        actualizacionUsuario.put("apellidos", apellidos);
        actualizacionUsuario.put("edad", Integer.parseInt(edad));
        databaseReference.updateChildren(actualizacionUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // La actualización en Firebase fue exitosa
                    Log.d("Firebase", "Usuario actualizado en Firebase");
                    sincronizarUsuarios();

                } else {
                    // Hubo un error en la actualización en Firebase
                    Log.e("Firebase", "Error al actualizar usuario en Firebase", task.getException());
                }
            }
        });
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de la base de datos si es necesario

    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define las columnas que deseas recuperar
        String[] columnas = {USUARIO_ID};

        // Define la cláusula WHERE para la consulta
        String whereClause = USUARIO_NOMBRE_USUARIO + " = ?";
        String[] whereArgs = {nombreUsuario};

        // Realiza la consulta en la base de datos
        Cursor cursor = db.query(TABLE_USUARIOS, columnas, whereClause, whereArgs, null, null, null);

        boolean existeUsuario = cursor.getCount() > 0;

        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return existeUsuario;
    }

    public void agregarUsuario(UserDTO usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long resultado = -1;

        try {
            values.put(USUARIO_ID, usuario.getId()); // Asegúrate de proporcionar tu propio valor para USUARIO_ID
            values.put(USUARIO_NOMBRE_USUARIO, usuario.getNombreUsuario());
            values.put(USUARIO_CONTRASENA, usuario.getContrasena());
            values.put(USUARIO_TIPO, usuario.getTipoUsuario());
            values.put(USUARIO_NOMBRE, usuario.getNombre());
            values.put(USUARIO_APELLIDOS, usuario.getApellidos());
            values.put(USUARIO_EDAD, usuario.getEdad());

            db.beginTransaction();
            resultado = db.insert(TABLE_USUARIOS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace();
        } finally {
            if (db != null) {
                try {
                    db.endTransaction();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                }
            }
        }

        if (resultado != -1) {
            Log.d("MiApp", "Se creó un nuevo usuario con " + usuario.getId() + " " + usuario.getNombreUsuario() + " " + usuario.getContrasena());
        }
    }



    public void sincronizarUsuarios() {
        DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference("usuarios");

        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                limpiarTablaUsuarios();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UserDTO usuarioFirebase = snapshot.getValue(UserDTO.class);
                    // Actualizar la base de datos local con los datos de usuarioFirebase
                    actualizarBaseDeDatosLocal(usuarioFirebase);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error, si es necesario
            }
        });
    }
    public void limpiarTablaUsuarios() {
        Log.d("MiApp", "Se limpia todos los usuarios");

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USUARIOS, null, null);
        db.close();
    }
    private void actualizarBaseDeDatosLocal(UserDTO usuarioFirebase) {
        Log.d("MiApp", "Se agrega el ususario "+usuarioFirebase.getNombreUsuario() + usuarioFirebase.getId());

        agregarUsuario(usuarioFirebase);
    }



    private void sincronizarCampamentos() {
        DatabaseReference campamentoReference = mDataBase.child("campamentos");
        Log.d("MiApp", "Se sincronizan los campamentos");
        campamentoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                limpiarTablaCampamentos();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtén la ID del campamento del nombre del nodo
                    String campamentoId = snapshot.getKey();

                    CampamentoDto campamentoFirebase = snapshot.getValue(CampamentoDto.class);

                    // Establece la ID en el objeto CampamentoDto
                    if (campamentoFirebase != null) {
                        campamentoFirebase.setId(Long.parseLong(campamentoId));
                    }

                    // Actualizar la base de datos local con los datos de campamentoFirebase
                    actualizarBaseDeDatosLocalCampamento(campamentoFirebase);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de datos, si es necesario
            }
        });
    }


    private void limpiarTablaCampamentos() {
        Log.d("MiApp", "Se limpia todos los campamentos");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAMPAMENTOS, null, null);
        db.close();
    }

    private void actualizarBaseDeDatosLocalCampamento(CampamentoDto campamento) {
        // Implementar lógica para actualizar la base de datos local con los datos del campamento, si es necesario
        // Puedes guardar los datos localmente, mostrarlos en la interfaz de usuario, etc.
        Log.d("MiApp", "se agrega el campamento "+campamento.toString());
        agregarCampamento(campamento);
    }

    public void agregarCampamento(CampamentoDto campamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Incluye la columna de la ID del campamento
        values.put(CAMPAMENTO_ID, campamento.getId());
        values.put(CAMPAMENTO_NOMBRE, campamento.getNombre());
        values.put(CAMPAMENTO_DESCRIPCION, campamento.getDescripcion());
        values.put(CAMPAMENTO_FECHA_INICIO, campamento.getFecha_inicio());
        values.put(CAMPAMENTO_FECHA_FINAL, campamento.getFecha_final());
        values.put(CAMPAMENTO_NUMERO_MAX_PARTICIPANTES, campamento.getNumero_max_participantes());
        values.put(CAMPAMENTO_NUMERO_APUNTADOS, campamento.getNumero_apuntados());
        values.put(CAMPAMENTO_UBICACION, campamento.getUbicacion());
        values.put(CAMPAMENTO_IMAGEN, campamento.getImagen());
        values.put(CAMPAMENTO_EDAD_MINIMA, campamento.getEdad_minima());
        values.put(CAMPAMENTO_EDAD_MAXIMA, campamento.getEdad_maxima());
        values.put(CAMPAMENTO_NUM_MONITORES, campamento.getNum_monitores());
        values.put(CAMPAMENTO_PRECIO, campamento.getPrecio());
        values.put(CAMPAMENTO_CATEGORIA, campamento.getCategoria());
        values.put(CAMPAMENTO_LATITUD, campamento.getLatitud());

        values.put(CAMPAMENTO_COORDINADOR, campamento.getIdCoordinador());

        db.insert(TABLE_CAMPAMENTOS, null, values);
        db.close();
    }
    private void sincronizarFavoritos() {
        DatabaseReference favoritosReference = mDataBase.child("favoritos");
        Log.d("MiApp", "Se sincronizan los favoritos");
        favoritosReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                limpiarTablaFavoritos();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtén la ID del favorito del nombre del nodo
                    String favoritoId = snapshot.getKey();

                    FavoritoDTO favoritoFirebase = snapshot.getValue(FavoritoDTO.class);



                    // Actualizar la base de datos local con los datos de favoritoFirebase
                    actualizarBaseDeDatosLocalFavorito(favoritoFirebase);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de datos, si es necesario
            }
        });
    }

    private void limpiarTablaFavoritos() {
        Log.d("MiApp", "Se limpian todos los favoritos");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITOS, null, null);
        db.close();
    }

    private void actualizarBaseDeDatosLocalFavorito(FavoritoDTO favorito) {
        // Implementar lógica para actualizar la base de datos local con los datos del favorito, si es necesario
        // Puedes guardar los datos localmente, mostrarlos en la interfaz de usuario, etc.
        Log.d("MiApp", "Se agrega el favorito para el usuario " + favorito.getUsuarioId() + " y campamento " + favorito.getCampamentoId());
        agregarFavorito(favorito);
    }

    private void agregarFavorito(FavoritoDTO favorito) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Incluye la columna de la ID del favorito

        values.put(FAVORITO_USUARIO_ID, favorito.getUsuarioId());
        values.put(FAVORITO_CAMPAMENTO_ID, favorito.getCampamentoId());

        db.insert(TABLE_FAVORITOS, null, values);
        db.close();
    }


    @NotNull
    public List<CampamentoDto> obtenerCampamentosCoordinador() {
        String id = LoggedUserDTO.getInstance(null).getUser().getId();
        List<CampamentoDto> lista = this.obtenerCampamentosConFavoritos(id);
        List<CampamentoDto> result= new ArrayList<>();
        for (CampamentoDto camp : lista ){
            if(camp.getIdCoordinador().equals(id)){
                result.add(camp);
            }
        }
        return result;
    }
}
