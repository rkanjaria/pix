package android.test.com.pixie.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.test.com.pix.R
import android.test.com.pixie.adapters.ImageAdapter
import android.test.com.pixie.models.Image
import android.test.com.pixie.models.User
import android.test.com.pixie.utils.PARCELABLE_OBJECT
import android.test.com.pixie.utils.loadCircularImage
import android.test.com.pixie.viewmodel.UserProfileViewModel
import android.view.View
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(), ImageAdapter.ImageAdapterListener {

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
        initUI()
        initViewModel(user.username)
    }

    fun initUI() {

        userImage.loadCircularImage(user.profileImage?.large)
        userName.text = user.name

        imageAdapter = ImageAdapter({}, this)
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
                    collapsingToolbar.title = ""
                    isShow = false
                }
            }
        })
    }

    fun initViewModel(userName: String?) {
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        if (userName != null) {
            viewModel.getUserClickedImages(userName)?.observe(this, object : Observer<List<Image>> {
                override fun onChanged(imageList: List<Image>?) {
                    imageAdapter.submitList(imageList as PagedList<Image>)
                    imageAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}
