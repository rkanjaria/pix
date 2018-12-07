package android.test.com.pixie.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Image(
        @SerializedName("id") val id: String?,
        @SerializedName("width") val width: Long,
        @SerializedName("height") val height: Long,
        @SerializedName("color") val color: String?,
        @SerializedName("urls") val urls: Urls?,
        @SerializedName("likes") val likes: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readParcelable(Urls::class.java.classLoader),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(width)
        parcel.writeLong(height)
        parcel.writeString(color)
        parcel.writeParcelable(urls, flags)
        parcel.writeInt(likes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}