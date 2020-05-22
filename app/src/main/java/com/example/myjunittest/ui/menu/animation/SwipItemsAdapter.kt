package com.example.myjunittest.ui.menu.animation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.myjunittest.R
import com.example.myjunittest.api.GankData

/**
 * 商品滑動RecyclerView Adapter
 *
 * @property items
 */
class SwipItemsAdapter(val items: MutableList<GankData>) :
    RecyclerView.Adapter<SwipItemsAdapter.SwipItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipItemViewHolder {
        return SwipItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.itemview_ec_paging_animation, parent, false)
        )
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

    override fun onBindViewHolder(holder: SwipItemViewHolder, position: Int) {
        val gankData = items[position]
        holder.bind(gankData.url)
    }

    fun setNewData(newData: MutableList<GankData>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    // ViewHolder
    inner class SwipItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItemImg = itemView.findViewById<ImageView>(
            R.id.iv_item_img
        )

        fun bind(url: String) {
            ivItemImg.load(url) {
                crossfade(true)
            }
        }
    }
}