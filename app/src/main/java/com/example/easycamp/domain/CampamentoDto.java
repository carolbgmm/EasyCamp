package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class CampamentoDto implements Parcelable {
    private long id;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFinal;
    private int numeroMaxParticipantes;
    private int numeroApuntados;
    private String ubicacion;
    private int edadMinima;
    private int edadMaxima;
    private int numMonitores;
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
            String fechaInicio,
            String fechaFinal,
            int numeroMaxParticipantes,
            int numeroApuntados,
            String ubicacion,
            int edadMinima,
            int edadMaxima,
            int numMonitores,
            double precio,
            String categoria,
            String imagen,
            boolean favorito
    ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.numeroMaxParticipantes = numeroMaxParticipantes;
        this.numeroApuntados = numeroApuntados;
        this.ubicacion = ubicacion;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.numMonitores = numMonitores;
        this.precio = precio;
        this.categoria = categoria;
        this.imagen = imagen;
        this.favorito = favorito;
    }

    protected CampamentoDto(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        descripcion = in.readString();
        fechaInicio = in.readString();
        fechaFinal = in.readString();
        numeroMaxParticipantes = in.readInt();
        numeroApuntados = in.readInt();
        ubicacion = in.readString();
        edadMinima = in.readInt();
        edadMaxima = in.readInt();
        numMonitores = in.readInt();
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
        dest.writeString(fechaInicio);
        dest.writeString(fechaFinal);
        dest.writeInt(numeroMaxParticipantes);
        dest.writeInt(numeroApuntados);
        dest.writeString(ubicacion);
        dest.writeInt(edadMinima);
        dest.writeInt(edadMaxima);
        dest.writeInt(numMonitores);
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getNumeroMaxParticipantes() {
        return numeroMaxParticipantes;
    }

    public void setNumeroMaxParticipantes(int numeroMaxParticipantes) {
        this.numeroMaxParticipantes = numeroMaxParticipantes;
    }

    public int getNumeroApuntados() {
        return numeroApuntados;
    }

    public void setNumeroApuntados(int numeroApuntados) {
        this.numeroApuntados = numeroApuntados;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public int getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(int edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public int getNumMonitores() {
        return numMonitores;
    }

    public void setNumMonitores(int numMonitores) {
        this.numMonitores = numMonitores;
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
}
