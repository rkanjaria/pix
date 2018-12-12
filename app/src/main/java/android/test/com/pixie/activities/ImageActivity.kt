package android.test.com.pixie.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.test.com.pix.R
import android.test.com.pixie.models.Image
import android.test.com.pixie.utils.PARCELABLE_OBJECT
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    val set = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        supportPostponeEnterTransition()
        initImage()

        imageCard.setOnClickListener(null)
        (imageCard.parent as View).setOnClickListener { onBackPressed() }
    }

    private fun initImage() {
        val image = intent.getParcelableExtra<Image>(PARCELABLE_OBJECT)
        val ratio = String.format("%d:%d", image?.width, image?.height)
        set.clone(parentContranint)
        set.setDimensionRatio(bigImage.id, ratio)
        set.applyTo(parentContranint)

        Glide.with(this)
                .load(image.urls?.regular)
                .apply(RequestOptions().dontTransform())
                .apply(RequestOptions().dontAnimate())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                }).into(bigImage)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }
}
