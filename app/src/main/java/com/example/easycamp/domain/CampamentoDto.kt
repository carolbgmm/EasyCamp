package com.example.easycamp.domain



import android.os.Parcel
import android.os.Parcelable


class CampamentoDto(
    val id: Long,
    val nombre: String?,
    val descripcion: String?,
    val fechaInicio: String?,
    val fechaFinal: String?,
    val numeroMaxParticipantes: Int,
    val numeroApuntados: Int,
    val ubicacion: String?,
    val edadMinima: Int,
    val edadMaxima: Int,
    val numMonitores: Int,
    val precio: Double,
    val categoria: String?,
    val imagen: String?,
    var favorito: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeString(fechaInicio)
        parcel.writeString(fechaFinal)
        parcel.writeInt(numeroMaxParticipantes)
        parcel.writeInt(numeroApuntados)
        parcel.writeString(ubicacion)
        parcel.writeInt(edadMinima)
        parcel.writeInt(edadMaxima)
        parcel.writeInt(numMonitores)
        parcel.writeDouble(precio)
        parcel.writeString(categoria)
        parcel.writeString(imagen)
        parcel.writeByte(if (favorito) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CampamentoDto> {
        override fun createFromParcel(parcel: Parcel): CampamentoDto {
            return CampamentoDto(parcel)
        }

        override fun newArray(size: Int): Array<CampamentoDto?> {
            return arrayOfNulls(size)
        }
    }
}
