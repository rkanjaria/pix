package android.test.com.pixie.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Urls(
        @SerializedName("raw") val raw: String?,
        @SerializedName("full") val full: String?,
        @SerializedName("regular") val regular: String?,
        @SerializedName("small") val small: String?,
        @SerializedName("thumb") val thumb: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(raw)
        parcel.writeString(full)
        parcel.writeString(regular)
        parcel.writeString(small)
        parcel.writeString(thumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Urls> {
        override fun createFromParcel(parcel: Parcel): Urls {
            return Urls(parcel)
        }

        override fun newArray(size: Int): Array<Urls?> {
            return arrayOfNulls(size)
        }
    }
}