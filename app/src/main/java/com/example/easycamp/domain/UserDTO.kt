package com.example.easycamp.domain

import android.os.Parcel
import android.os.Parcelable

class UserDTO(
    var id: Long,
    var nombreUsuario: String?,
    var tipoUsuario: String?,
    var nombre: String?,
    var apellidos: String?,
    var edad: Int
) : Parcelable {

    init {
        requireNotNull(nombreUsuario) { "El campo nombreUsuario no puede ser nulo" }
        requireNotNull(tipoUsuario) { "El campo tipoUsuario no puede ser nulo" }
        requireNotNull(nombre) { "El campo nombre no puede ser nulo" }
        requireNotNull(apellidos) { "El campo apellidos no puede ser nulo" }
        require(edad in 0..120) { "La edad debe estar en el rango de 0 a 120" }
        require(tipoUsuario == "CLIENTE" || tipoUsuario == "TRABAJADOR") { "El tipo de usuario debe ser CLIENTE o TRABAJADOR" }
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nombreUsuario)
        parcel.writeString(tipoUsuario)
        parcel.writeString(nombre)
        parcel.writeString(apellidos)
        parcel.writeInt(edad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDTO> {
        override fun createFromParcel(parcel: Parcel): UserDTO {
            return UserDTO(parcel)
        }

        override fun newArray(size: Int): Array<UserDTO?> {
            return arrayOfNulls(size)
        }
    }
}
