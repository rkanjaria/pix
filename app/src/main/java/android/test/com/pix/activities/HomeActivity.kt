package android.test.com.pix.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.test.com.pix.R
import android.test.com.pix.adapters.ImageAdapter
import android.test.com.pix.utils.State
import android.test.com.pix.utils.showMessage
import android.test.com.pix.viewmodel.ImageListViewModel
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageListViewModel
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProviders.of(this).get(ImageListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initState() {
        viewModel.getState()
                .observe(this, Observer {
                    if (it == State.ERROR) baseContext.showMessage("Error")
                    progressBar.visibility = if (viewModel.listIsEmpty() && it == State.LOADING) View.VISIBLE else View.GONE
                    if (!viewModel.listIsEmpty()) {
                        imageAdapter.setState(it ?: State.DONE)
                    }
                })
    }

    private fun initAdapter() {
        imageAdapter = ImageAdapter { viewModel.retry() }
        imageRecyclerview.layoutManager = LinearLayoutManager(this)
        imageRecyclerview.adapter = imageAdapter
        viewModel.imageList.observe(this, Observer {
            imageAdapter.submitList(it)
        })

    }
}
