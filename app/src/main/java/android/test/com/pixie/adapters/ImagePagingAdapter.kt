package android.test.com.pixie.adapters

import android.arch.paging.PagedListAdapter
import android.support.constraint.ConstraintSet
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.test.com.pix.R
import android.test.com.pixie.interfaces.ImageAdapterListener
import android.test.com.pixie.models.Image
import android.test.com.pixie.utils.State
import android.test.com.pixie.utils.inflate
import android.test.com.pixie.utils.loadImage
import android.test.com.pixie.utils.showMessage
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_recycler_layout.view.*
import kotlinx.android.synthetic.main.loader_recycler_layout.view.*

class ImagePagingAdapter(private val retry: () -> Unit, val mListener: ImageAdapterListener) : PagedListAdapter<Image, RecyclerView.ViewHolder>(ImageDiffCallback) {

    private var state = State.LOADING
    private val IMAGE_VIEW = 1
    private val LOADER_VIEW = 0
    private val set = ConstraintSet()

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): RecyclerView.ViewHolder {
        when (itemViewType) {
            LOADER_VIEW -> return LoaderViewHolder(parent.inflate(R.layout.loader_recycler_layout))
            else -> return ImageViewHolder(parent.inflate(R.layout.image_recycler_layout))
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

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        fun bindImages(image: Image?) {
            view.pic.loadImage(image?.urls?.regular, image?.color)
            val ratio = String.format("%d:%d", image?.width, image?.height)
            set.clone(view.parentContranint)
            set.setDimensionRatio(view.pic.id, ratio)
            set.applyTo(view.parentContranint)
            view.setOnClickListener { mListener.onClickImage(image, view.imageCard) }
        }
    }

    class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        fun bindLoader(state: State) {
            view.progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            if (state == State.ERROR) {
                view.context.showMessage(view.context.getString(R.string.something_went_wrong))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) IMAGE_VIEW else LOADER_VIEW
    }

    fun clear() {
        notifyItemRangeRemoved(0, itemCount)
    }

    companion object {
        val ImageDiffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areContentsTheSame(oldImage: Image, newImage: Image) = oldImage.id == newImage.id
            override fun areItemsTheSame(oldImage: Image, newImage: Image) = oldImage == newImage
        }
    }
}