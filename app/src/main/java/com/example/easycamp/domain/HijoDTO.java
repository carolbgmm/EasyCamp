package com.example.easycamp.domain;

import android.os.Parcel;
import android.os.Parcelable;



public class HijoDTO implements Parcelable {
    private String id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String observaciones;
    private String id_padre;

    public HijoDTO() {

    }

    public HijoDTO(String nombre, String apellidos, int edad, String observaciones, String id_padre) {
        this.id = null;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.observaciones = observaciones;
        this.id_padre = id_padre;

        // Validaciones
        requireNotNull(nombre, "El campo nombre no puede ser nulo");
        requireNotNull(apellidos, "El campo apellidos no puede ser nulo");
        require(edad >= 0 && edad <= 120, "La edad debe estar en el rango de 0 a 120");
    }

    public HijoDTO(String id, String nombre, String apellidos, int edad, String observaciones, String id_padre) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.observaciones = observaciones;
        this.id_padre = id_padre;

        // Validaciones
        requireNotNull(nombre, "El campo nombre no puede ser nulo");
        requireNotNull(apellidos, "El campo apellidos no puede ser nulo");
        require(edad >= 0 && edad <= 120, "La edad debe estar en el rango de 0 a 120");
    }

    protected HijoDTO(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        edad = in.readInt();
        observaciones = in.readString();
        id_padre = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeInt(edad);
        dest.writeString(observaciones);
        dest.writeString(id_padre);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HijoDTO> CREATOR = new Creator<HijoDTO>() {
        @Override
        public HijoDTO createFromParcel(Parcel in) {
            return new HijoDTO(in);
        }

        @Override
        public HijoDTO[] newArray(int size) {
            return new HijoDTO[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        HijoDTO otherHijo = (HijoDTO) obj;

        // Comparación de campos relevantes para la igualdad
        return id.equals(otherHijo.id) && nombre.equals(otherHijo.nombre) && apellidos.equals(otherHijo.apellidos);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + apellidos.hashCode();
        return result;
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

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getId_padre() {
        return id_padre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setId_padre(String id_padre) {
        this.id_padre = id_padre;
    }
}

