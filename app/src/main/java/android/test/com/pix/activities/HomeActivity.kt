package android.test.com.pix.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.test.com.pix.R
import android.test.com.pix.adapters.ImageAdapter
import android.test.com.pix.utils.State
import android.test.com.pix.utils.showMessage
import android.test.com.pix.viewmodel.ImageListViewModel
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.setQuery(query)
        imageAdapter.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextChange(newText: String?) = false

    private lateinit var viewModel: ImageListViewModel
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProviders.of(this).get(ImageListViewModel::class.java)
        initAdapter()
        initState()
        initSearchView()
    }

    fun initSearchView() {
        val searchText = pixSearch.findViewById<TextView>(android.support.v7.appcompat.R.id.search_src_text)
        searchText.typeface = ResourcesCompat.getFont(this, R.font.niramit_regular)
        searchText.textSize = 18f
        searchText.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        searchText.setHintTextColor(ContextCompat.getColor(this, R.color.colorOpacityWhite))
        val searchButtonImage = pixSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_mag_icon)
        searchButtonImage.setImageResource(R.drawable.ic_search)
        val closeButtonImage = pixSearch.findViewById<ImageView>(R.id.search_close_btn)
        closeButtonImage.setImageResource(R.drawable.ic_close)
        pixSearch.queryHint = "Eg. Cars, Superheros..."
        pixSearch.setOnQueryTextListener(this)
    }

    private fun initState() {
        viewModel.getState()
                .observe(this, Observer {
                    if (it == State.ERROR) baseContext.showMessage(getString(R.string.something_went_wrong))
                    progressBar.visibility = if (viewModel.listIsEmpty() && it == State.LOADING) View.VISIBLE else View.GONE
                    if (!viewModel.listIsEmpty()) {
                        imageAdapter.setState(it ?: State.DONE)
                    }
                })
    }

    private fun initAdapter() {
        imageAdapter = ImageAdapter { viewModel.retry() }
        imageRecyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        imageRecyclerview.adapter = imageAdapter
        viewModel.imageList.observe(this, Observer {
            imageAdapter.submitList(it)
        })
    }
}
