package android.test.com.pixie.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.test.com.pix.R
import android.test.com.pixie.adapters.ImageAdapter
import android.test.com.pixie.interfaces.ImageAdapterListener
import android.test.com.pixie.models.Image
import android.test.com.pixie.models.User
import android.test.com.pixie.utils.PARCELABLE_OBJECT
import android.test.com.pixie.utils.STRING
import android.test.com.pixie.utils.loadCircularImage
import android.test.com.pixie.utils.loadImage
import android.test.com.pixie.viewmodel.UserProfileViewModel
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(), ImageAdapterListener {

    override fun onClickImage(image: Image?, imageCard: View) {
        val imageIntent = Intent(this, ImageActivity::class.java)
        imageIntent.putExtra(PARCELABLE_OBJECT, image)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                imageCard, ViewCompat.getTransitionName(imageCard)!!)
        startActivity(imageIntent, activityOptions.toBundle())
    }

    private lateinit var viewModel: UserProfileViewModel
    private lateinit var user: User
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        user = intent.getParcelableExtra(PARCELABLE_OBJECT)
        initViewModel(user.username)
        initUI()
    }

    fun initUI() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userBigImage.loadImage(intent.getStringExtra(STRING))
        userImage.loadCircularImage(user.profileImage?.large)
        userName.text = user.name

        imageAdapter = ImageAdapter(this)
        userPixRecyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        userPixRecyclerview.adapter = imageAdapter

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) appBarLayout?.totalScrollRange
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = user.name
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    fun initViewModel(userName: String?) {
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        userName?.let {
            viewModel.getUserClickedImages(it)?.observe(this, object : Observer<List<Image>> {
                override fun onChanged(imageList: List<Image>?) {
                    imageAdapter.submitList(imageList)
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }
}
