package com.example.easycamp.domain;


import android.os.Parcel;
import android.os.Parcelable;

public class TickDTO implements Parcelable {

    private String id;
    private String usuarioNombreTick;
    private String campamentoNombreTick;

    // Constructor vacío necesario para Firebase
    public TickDTO() {
    }

    public TickDTO(String id, String usuarioNombreTick, String campamentoNombreTick) {
        this.id = id;
        this.usuarioNombreTick = usuarioNombreTick;
        this.campamentoNombreTick = campamentoNombreTick;
    }

    protected TickDTO(Parcel in) {
        id = in.readString();
        usuarioNombreTick = in.readString();
        campamentoNombreTick = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(usuarioNombreTick);
        dest.writeString(campamentoNombreTick);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TickDTO> CREATOR = new Creator<TickDTO>() {
        @Override
        public TickDTO createFromParcel(Parcel in) {
            return new TickDTO(in);
        }

        @Override
        public TickDTO[] newArray(int size) {
            return new TickDTO[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioNombreTick() {
        return usuarioNombreTick;
    }

    public void setUsuarioNombreTick(String usuarioNombreTick) {
        this.usuarioNombreTick = usuarioNombreTick;
    }

    public String getCampamentoNombreTick() {
        return campamentoNombreTick;
    }

    public void setCampamentoNombreTick(String campamentoNombreTick) {
        this.campamentoNombreTick = campamentoNombreTick;
    }
}

