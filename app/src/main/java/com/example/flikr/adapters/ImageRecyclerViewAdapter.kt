package com.example.flikr.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flikr.R
import com.example.flikr.models.Photos
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlin.collections.ArrayList

class ImageRecyclerViewAdapter(private val listener: AdapterCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var photos: List<Photos> = ArrayList()
    private var isFavoriteTab = false

    interface AdapterCallback {
        fun click(id: String, isFavorite: Boolean)
    }

    fun setDataList(list: List<Photos>, isFavoriteTab: Boolean) {
        //set images data on adapter
        this.isFavoriteTab = isFavoriteTab
        photos = if (isFavoriteTab) {
            getFilteredItems(list)
        } else {
            list
        }
        notifyDataSetChanged()
    }

    private fun getFilteredItems(rawList: List<Photos>): List<Photos> {
        val result = ArrayList<Photos>()
        for (item in rawList) {
            if (item.isFavorite) {
                result.add(item)
            }
        }

        return result
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: SimpleDraweeView = view.findViewById(R.id.image_view)
        val title: TextView = view.findViewById(R.id.title)
        val photographer: TextView = view.findViewById(R.id.photographer)
        val favorite: ImageView = view.findViewById(R.id.favorite)

        init {
            favorite.setOnClickListener { v ->
                val position = v.tag as Int
                val item = photos[position]
                item.isFavorite = !item.isFavorite
                listener.click(item.id, item.isFavorite)
                if (isFavoriteTab) {
                    photos = getFilteredItems(photos)
                    notifyDataSetChanged()
                } else {
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            val image = photos[position]

            if (isFavoriteTab && !image.isFavorite) return

            //load image to image view
            val imageRequestCover = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(image.imageUrl))
                .setCacheChoice(ImageRequest.CacheChoice.SMALL)
                .build()
            val imageCtrlCover: DraweeController = Fresco.newDraweeControllerBuilder()
                .setOldController(viewHolder.imageView.controller)
                .setImageRequest(imageRequestCover)
                .build()

            viewHolder.imageView.controller = imageCtrlCover

            viewHolder.title.text = image.title
            viewHolder.photographer.text = image.photographer

            val drawableId = if (image.isFavorite) R.drawable.favorites_on else R.drawable.favorites_off
            viewHolder.favorite.setImageResource(drawableId)

            viewHolder.favorite.tag = position

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        return ViewHolder(view)
    }
}