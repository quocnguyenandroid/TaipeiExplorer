package com.qndev.taipeiexplorer.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.qndev.taipeiexplorer.R
import com.qndev.taipeiexplorer.data.remote.response.ImageData
import com.qndev.taipeiexplorer.databinding.ItemImageBinding

class ImageItemAdapter(private val images: List<ImageData>) :
    RecyclerView.Adapter<ImageItemAdapter.ViewHolder>() {

    private var itemClickListener: OnImageClickListener? = null

    fun setOnLocationClickListener(listener: OnImageClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onImageClick(position)
                }
            }
        }

        fun bind(item: ImageData) {

            binding.image.load(item.src) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                crossfade(true)
            }
        }
    }
}

interface OnImageClickListener {
    fun onImageClick(position: Int)
}