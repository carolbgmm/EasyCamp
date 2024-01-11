package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class TareaDTO implements Parcelable {
    private String id;
    private String usuarioID;
    private String titulo;
    private String descripcion;
    private String fechaLimite;
    private boolean tick;

    public TareaDTO(String id, String usuarioID, String titulo, String descripcion, String fechaLimite, boolean tick) {
        this.id = id;
        this.usuarioID = usuarioID;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.tick = tick;
    }

    protected TareaDTO(Parcel in) {
        id = in.readString();
        usuarioID = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
        fechaLimite = in.readString();
        tick = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(usuarioID);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeString(fechaLimite);
        dest.writeByte((byte) (tick ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TareaDTO> CREATOR = new Creator<TareaDTO>() {
        @Override
        public TareaDTO createFromParcel(Parcel in) {
            return new TareaDTO(in);
        }

        @Override
        public TareaDTO[] newArray(int size) {
            return new TareaDTO[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setTick(boolean tick) {
        this.tick = tick;
    }

    public boolean isTick() {
        return tick;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}

