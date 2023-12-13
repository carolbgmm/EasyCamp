package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class CampamentoDto implements Parcelable {
    private long id;
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
    private boolean favorito;

    // Constructor vacío necesario para Firebase
    public CampamentoDto() {
    }

    public CampamentoDto(
            long id,
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
            boolean favorito
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
        this.favorito = favorito;
    }

    protected CampamentoDto(Parcel in) {
        id = in.readLong();
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
        favorito = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
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
        dest.writeByte((byte) (favorito ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CampamentoDto> CREATOR = new Creator<CampamentoDto>() {
        @Override
        public CampamentoDto createFromParcel(Parcel in) {
            return new CampamentoDto(in);
        }

        @Override
        public CampamentoDto[] newArray(int size) {
            return new CampamentoDto[size];
        }
    };

    // Getters y setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
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
                ", favorito=" + favorito +
                '}';
    }

}
