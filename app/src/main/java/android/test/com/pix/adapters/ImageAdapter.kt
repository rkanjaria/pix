package android.test.com.pix.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.test.com.pix.R
import android.test.com.pix.models.Image
import android.test.com.pix.utils.State
import android.test.com.pix.utils.inflate
import android.test.com.pix.utils.loadImage
import android.test.com.pix.utils.showMessage
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_recycler_layout.view.*
import kotlinx.android.synthetic.main.loader_recycler_layout.view.*

class ImageAdapter(private val retry: () -> Unit) : PagedListAdapter<Image, RecyclerView.ViewHolder>(ImageDiffcallback) {

    private var state = State.LOADING
    private val IMAGE_VIEW = 1
    private val LOADER_VIEW = 0

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): RecyclerView.ViewHolder {
        when (itemViewType) {
            LOADER_VIEW -> return LoaderViewHolder(parent.inflate(R.layout.loader_recycler_layout, false))
            else -> return ImageViewHolder(parent.inflate(R.layout.image_recycler_layout, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == IMAGE_VIEW) {
            (holder as ImageViewHolder).bindImages(getItem(position))
        } else {
            (holder as LoaderViewHolder).bindLoader(state)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        fun bindImages(images: Image?) {
            view.pic.loadImage(images?.urls?.regular)
        }
    }

    class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        fun bindLoader(state: State) {
            view.progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            if (state == State.ERROR) {
                view.context.showMessage("Something went wrong")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) IMAGE_VIEW else LOADER_VIEW
    }

    companion object {
        val ImageDiffcallback = object : DiffUtil.ItemCallback<Image>() {

            override fun areContentsTheSame(oldImage: Image, newImage: Image): Boolean {
                return oldImage.urls == newImage.urls
            }

            override fun areItemsTheSame(oldImage: Image, newImage: Image): Boolean {
                return oldImage == newImage
            }

        }
    }
}