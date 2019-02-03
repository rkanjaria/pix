package android.test.com.pixie.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.appcompat.app.AppCompatActivity
import android.test.com.pix.R
import android.test.com.pixie.models.Image
import android.test.com.pixie.utils.PARCELABLE_OBJECT
import android.test.com.pixie.utils.STRING
import android.test.com.pixie.utils.loadCircularImage
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
        initUI()
    }

    private fun initUI() {
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

        userName.text = image?.user?.name
        userLikes.text = image?.likes.toString()
        userImage.loadCircularImage(image.user?.profileImage?.large)
        userName.setOnClickListener { userImage.performClick() }
        userImage.setOnClickListener {
            val userIntent = Intent(this, UserProfileActivity::class.java)
            userIntent.putExtra(PARCELABLE_OBJECT, image.user)
            userIntent.putExtra(STRING, image.urls?.regular)
            startActivity(userIntent)
        }

        imageCard.setOnClickListener(null)
        (imageCard.parent as View).setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }
}
