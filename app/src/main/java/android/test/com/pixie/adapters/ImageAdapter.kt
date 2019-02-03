package android.test.com.pixie.adapters

import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.test.com.pix.R
import android.test.com.pixie.interfaces.ImageAdapterListener
import android.test.com.pixie.models.Image
import android.test.com.pixie.utils.inflate
import android.test.com.pixie.utils.loadImage
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_recycler_layout.view.*


class ImageAdapter(val mListener: ImageAdapterListener) : ListAdapter<Image, ImageAdapter.ImageViewHolder>(ImageDiffCallback) {
    val set = ConstraintSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(parent.inflate(R.layout.image_recycler_layout))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        fun bindItems(image: Image?) {
            view.pic.loadImage(image?.urls?.regular, image?.color)
            val ratio = String.format("%d:%d", image?.width, image?.height)
            set.clone(view.parentContranint)
            set.setDimensionRatio(view.pic.id, ratio)
            set.applyTo(view.parentContranint)
            view.setOnClickListener { mListener.onClickImage(image, view.imageCard) }
        }
    }

    companion object {
        val ImageDiffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldImage: Image, newImage: Image) = oldImage.id == newImage.id
            override fun areContentsTheSame(oldImage: Image, newImage: Image) = oldImage == newImage
        }
    }
}