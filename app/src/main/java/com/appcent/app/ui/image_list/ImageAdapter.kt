package com.appcent.app.ui.image_list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appcent.app.R
import com.appcent.app.data.model.Photo
import com.appcent.app.databinding.ItemImageBinding
import com.appcent.app.ui.image_detail.ImageDetailActivity
import com.appcent.app.utils.State
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list_footer.view.*

class ImageAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Photo, RecyclerView.ViewHolder>(ImagesDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            getItem(position)?.let { (holder as ViewHolder).bind(it) }
        else (holder as ListFooterViewHolder).bind(state)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) ViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ) else ListFooterViewHolder.create(retry, parent)

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
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

    companion object {
        val ImagesDiffCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }
}


class ViewHolder(val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: Photo) {
        binding.apply {
            try {
                Glide.with(binding.root.context)
                    .load("https://farm" + photo.farm + ".staticflickr.com/" + photo.server + "/" + photo.id + "_" + photo.secret + "_m.jpg")
                    .into(binding.ivPhoto)
            } catch (e: IllegalArgumentException) {
            }
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, ImageDetailActivity::class.java)
                intent.putExtra(
                    "imageUrl",
                    "https://farm" + photo.farm + ".staticflickr.com/" + photo.server + "/" + photo.id + "_" + photo.secret + "_m.jpg"
                )
                intent.putExtra("imageName",photo.title)
                binding.root.context.startActivity(intent)
            }
        }

    }
}

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}