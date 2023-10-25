package com.example.easycamp.domain

import android.os.Parcel
import android.os.Parcelable

class CampamentoDto(val nombre: String?,
                    val descripcion: String?,
                    val image: String?,
                    var favourite: Boolean = false) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeString(image)
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