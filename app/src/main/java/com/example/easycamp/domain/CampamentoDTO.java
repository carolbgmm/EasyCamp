package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class CampamentoDTO implements Parcelable {
    private String id;
    private String nombre;
    private String descripcion;
    private String fecha_inicio;
    private String fecha_final;
    private int numero_max_participantes;
    private int numero_apuntados;
    private String ubicacion;
    private int edad_minima;
    private int edad_maxima;
    private int num_monitores;
    private double precio;
    private String categoria;
    private String imagen;
    private float latitud;
    private float longuitud;

    // Constructor vacío necesario para Firebase
    public CampamentoDTO() {
    }

    public CampamentoDTO(
            String id,
            String nombre,
            String descripcion,
            String fecha_inicio,
            String fecha_final,
            int numero_max_participantes,
            int numero_apuntados,
            String ubicacion,
            int edad_minima,
            int edad_maxima,
            int num_monitores,
            double precio,
            String categoria,
            String imagen,
            float latitud,
            float longuitud
    ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.numero_max_participantes = numero_max_participantes;
        this.numero_apuntados = numero_apuntados;
        this.ubicacion = ubicacion;
        this.edad_minima = edad_minima;
        this.edad_maxima = edad_maxima;
        this.num_monitores = num_monitores;
        this.precio = precio;
        this.categoria = categoria;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longuitud = longuitud;
    }

    protected CampamentoDTO(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        descripcion = in.readString();
        fecha_inicio = in.readString();
        fecha_final = in.readString();
        numero_max_participantes = in.readInt();
        numero_apuntados = in.readInt();
        ubicacion = in.readString();
        edad_minima = in.readInt();
        edad_maxima = in.readInt();
        num_monitores = in.readInt();
        precio = in.readDouble();
        categoria = in.readString();
        imagen = in.readString();
        latitud = in.readFloat();
        longuitud = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(fecha_inicio);
        dest.writeString(fecha_final);
        dest.writeInt(numero_max_participantes);
        dest.writeInt(numero_apuntados);
        dest.writeString(ubicacion);
        dest.writeInt(edad_minima);
        dest.writeInt(edad_maxima);
        dest.writeInt(num_monitores);
        dest.writeDouble(precio);
        dest.writeString(categoria);
        dest.writeString(imagen);
        dest.writeFloat(latitud);
        dest.writeFloat(longuitud);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<CampamentoDTO> CREATOR = new Parcelable.Creator<CampamentoDTO>() {
        @Override
        public CampamentoDTO createFromParcel(Parcel in) {
            return new CampamentoDTO(in);
        }

        @Override
        public CampamentoDTO[] newArray(int size) {
            return new CampamentoDTO[size];
        }
    };

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public int getNumero_max_participantes() {
        return numero_max_participantes;
    }

    public void setNumero_max_participantes(int numero_max_participantes) {
        this.numero_max_participantes = numero_max_participantes;
    }

    public int getNumero_apuntados() {
        return numero_apuntados;
    }

    public void setNumero_apuntados(int numero_apuntados) {
        this.numero_apuntados = numero_apuntados;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEdad_minima() {
        return edad_minima;
    }

    public void setEdad_minima(int edad_minima) {
        this.edad_minima = edad_minima;
    }

    public int getEdad_maxima() {
        return edad_maxima;
    }

    public void setEdad_maxima(int edad_maxima) {
        this.edad_maxima = edad_maxima;
    }

    public int getNum_monitores() {
        return num_monitores;
    }

    public void setNum_monitores(int num_monitores) {
        this.num_monitores = num_monitores;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setLonguitud(float longuitud) {
        this.longuitud = longuitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLonguitud() {
        return longuitud;
    }

    public float getLatitud() {
        return latitud;
    }

    @Override
    public String toString() {
        return "CampamentoDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio='" + fecha_inicio + '\'' +
                ", fechaFinal='" + fecha_final + '\'' +
                ", numeroMaxParticipantes=" + numero_max_participantes +
                ", numeroApuntados=" + numero_apuntados +
                ", ubicacion='" + ubicacion + '\'' +
                ", edadMinima=" + edad_minima +
                ", edadMaxima=" + edad_maxima +
                ", numMonitores=" + num_monitores +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", imagen='" + imagen + '\'' +
                ", latitud=" + latitud +
                ", longuitud=" + longuitud +
                '}';
    }
}
