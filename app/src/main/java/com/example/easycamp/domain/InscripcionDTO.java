package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class InscripcionDTO implements Parcelable {
    private long id;
    private long hijoId;
    private long campamentoId;


    public InscripcionDTO() {

    }
    public InscripcionDTO(long id, long hijoId, long campamentoId) {
        this.id = id;
        this.hijoId = hijoId;
        this.campamentoId = campamentoId;

        // Validaciones
        requireNotNull(hijoId, "El campo hijoId no puede ser nulo");
        requireNotNull(campamentoId, "El campo campamentoId no puede ser nulo");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHijoId() {
        return hijoId;
    }

    public void setHijoId(long hijoId) {
        this.hijoId = hijoId;
    }

    public long getCampamentoId() {
        return campamentoId;
    }

    public void setCampamentoId(long campamentoId) {
        this.campamentoId = campamentoId;
    }

    // Parcelable methods
    public static final Parcelable.Creator<InscripcionDTO> CREATOR = new Parcelable.Creator<InscripcionDTO>() {
        @Override
        public InscripcionDTO createFromParcel(Parcel in) {
            return new InscripcionDTO(in);
        }

        @Override
        public InscripcionDTO[] newArray(int size) {
            return new InscripcionDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(hijoId);
        dest.writeLong(campamentoId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void requireNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    private InscripcionDTO(Parcel in) {
        id = in.readLong();
        hijoId = in.readLong();
        campamentoId = in.readLong();
    }
}
