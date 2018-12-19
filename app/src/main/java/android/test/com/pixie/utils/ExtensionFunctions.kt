package android.test.com.pixie.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadImage(url: String?, placeholderColor: String? = "#E0E0E0") {
    val placeholder = ColorDrawable()
    placeholder.color = Color.parseColor(placeholderColor)
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().dontTransform())
            .apply(RequestOptions.placeholderOf(placeholder))
            .apply(RequestOptions.errorOf(placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadCircularImage(url: String?, placeholderColor: String? = "#E0E0E0") {
    val placeholder = ColorDrawable()
    placeholder.color = Color.parseColor(placeholderColor)
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions.circleCropTransform())
            .apply(RequestOptions.errorOf(placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadBlurImage(url: String?, placeholderColor: String? = "#E0E0E0", blurRadius: Float) {
    val placeholder = ColorDrawable()
    placeholder.color = Color.parseColor(placeholderColor)
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().transform(BlurTransform(context, 50f)))
            .apply(RequestOptions().centerCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun Context.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}