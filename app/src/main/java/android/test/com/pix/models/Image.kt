package android.test.com.pix.models

import com.google.gson.annotations.SerializedName

data class Image(
        @SerializedName("id") val id: String?,
        @SerializedName("width") val width: Long,
        @SerializedName("height") val height: Long,
        @SerializedName("color") val color: String?,
        @SerializedName("urls") val urls: Urls?,
        @SerializedName("likes") val likes: Int)


data class Urls(
        @SerializedName("raw") val raw: String?,
        @SerializedName("full") val full: String?,
        @SerializedName("regular") val regular: String?,
        @SerializedName("small") val small: String?,
        @SerializedName("thumb") val thumb: String?
)