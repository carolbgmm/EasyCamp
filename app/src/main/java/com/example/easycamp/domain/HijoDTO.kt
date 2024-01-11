package com.example.easycamp.domain

import android.os.Parcel
import android.os.Parcelable

class HijoDTO(
    var id: Long,
    var nombre: String?,
    var apellidos: String?,
    var edad: Int,
    var observaciones: String?
) : Parcelable {

    init {
        requireNotNull(nombre) { "El campo nombre no puede ser nulo" }
        requireNotNull(apellidos) { "El campo apellidos no puede ser nulo" }
        require(edad in 0..120) { "La edad debe estar en el rango de 0 a 120" }
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )




    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nombre)
        parcel.writeString(apellidos)
        parcel.writeInt(edad)
        parcel.writeString(observaciones)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HijoDTO> {
        override fun createFromParcel(parcel: Parcel): HijoDTO {
            return HijoDTO(parcel)
        }

        override fun newArray(size: Int): Array<HijoDTO?> {
            return arrayOfNulls(size)
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val otherHijo = other as HijoDTO

        // Comparación de campos relevantes para la igualdad
        return id == otherHijo.id && nombre == otherHijo.nombre && apellidos == otherHijo.apellidos
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + apellidos.hashCode()
        return result
    }
}
