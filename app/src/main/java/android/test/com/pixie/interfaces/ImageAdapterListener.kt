package android.test.com.pixie.interfaces

import android.test.com.pixie.models.Image
import android.view.View

interface ImageAdapterListener {
    fun onClickImage(image: Image?, imageCard: View)
}