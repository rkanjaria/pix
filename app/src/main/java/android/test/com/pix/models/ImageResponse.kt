package android.test.com.pix.models

import com.google.gson.annotations.SerializedName

data class ImageResponse(
        @SerializedName("total") val total: Long,
        @SerializedName("total_pages") val totalPages: Long,
        @SerializedName("results") val imageList: List<Image>
)

