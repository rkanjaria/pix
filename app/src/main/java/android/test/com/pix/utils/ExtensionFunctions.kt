package android.test.com.pix.utils

import android.content.Context
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

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            //.apply(RequestOptions.placeholderOf(placeholder))
            //.apply(RequestOptions.errorOf(placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun Context.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}