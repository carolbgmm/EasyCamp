package com.example.easycamp.domain

import android.os.Parcel
import android.os.Parcelable

class TareaDTO(
        val id: Long,
        val usuarioNombre: String?,
        val titulo: String?,
        val descripcion: String?,
        val fechaLimite: String?,
        var hecha: Boolean = false

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(usuarioNombre)
        parcel.writeString(titulo)
        parcel.writeString(descripcion)
        parcel.writeString(fechaLimite)
        parcel.writeByte(if (hecha) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TareaDTO> {
        override fun createFromParcel(parcel: Parcel): TareaDTO {
            return TareaDTO(parcel)
        }

        override fun newArray(size: Int): Array<TareaDTO?> {
            return arrayOfNulls(size)
        }
    }

}