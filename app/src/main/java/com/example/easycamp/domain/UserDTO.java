package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDTO implements Parcelable {
    private String id;
    private String nombreUsuario;
    private String tipoUsuario;
    private String nombre;
    private String apellidos;
    private int edad;
    private String contrasena;


    public UserDTO() {

    }
    public UserDTO(String id, String nombreUsuario, String tipoUsuario, String nombre, String apellidos, int edad, String contrasena) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.contrasena = contrasena;

        // Validaciones
        requireNotNull(nombreUsuario, "El campo nombreUsuario no puede ser nulo");
        requireNotNull(tipoUsuario, "El campo tipoUsuario no puede ser nulo");
        requireNotNull(nombre, "El campo nombre no puede ser nulo");
        requireNotNull(apellidos, "El campo apellidos no puede ser nulo");
        require(edad >= 0 && edad <= 120, "La edad debe estar en el rango de 0 a 120");
        require(tipoUsuario.equals("CLIENTE") || tipoUsuario.equals("TRABAJADOR"), "El tipo de usuario debe ser CLIENTE o TRABAJADOR");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Parcelable methods
    public static final Parcelable.Creator<UserDTO> CREATOR = new Parcelable.Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombreUsuario);
        dest.writeString(tipoUsuario);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeInt(edad);
        dest.writeString(contrasena);
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

    private UserDTO(Parcel in) {
        id = in.readString();
        nombreUsuario = in.readString();
        tipoUsuario = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        edad = in.readInt();
        contrasena = in.readString();
    }
}

