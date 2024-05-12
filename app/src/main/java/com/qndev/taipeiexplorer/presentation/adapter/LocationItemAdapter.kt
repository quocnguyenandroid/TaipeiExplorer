package com.qndev.taipeiexplorer.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.qndev.taipeiexplorer.R
import com.qndev.taipeiexplorer.databinding.ItemLocationBinding
import com.qndev.taipeiexplorer.domain.model.Location

class LocationItemAdapter : RecyclerView.Adapter<LocationItemAdapter.ViewHolder>() {

    private var items = listOf<Location>()
    private var itemClickListener: OnLocationClickListener? = null
    private var isClickable = true

    fun setOnLocationClickListener(listener: OnLocationClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (isClickable) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener?.onLocationClick(position)
                    }
                    isClickable = false
                    itemView.postDelayed({ isClickable = true }, 500L)
                }
            }
        }

        fun bind(item: Location) {

            binding.locationDist.text = item.district
            binding.locationName.text = item.name

            // Clear previous image
            binding.locationImage.setImageDrawable(null)

            binding.locationImage.load(item.images.firstOrNull()?.src) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                crossfade(true)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateLocationList(locations: List<Location>) {
        items = locations
        notifyDataSetChanged()
    }
}

interface OnLocationClickListener {
    fun onLocationClick(position: Int)
}