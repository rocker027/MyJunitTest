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
 * TopImageViewPagerAdapter
 *
 * @property datas
 */
class TopImagePagerAdapter(val datas: MutableList<GankData>) :
    RecyclerView.Adapter<TopImagePagerAdapter.TopImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemview_ec_paging_animation_top_image, parent, false)
        return TopImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (datas.isNullOrEmpty()) 0 else datas.size
    }

    override fun onBindViewHolder(holder: TopImageViewHolder, position: Int) {
        val gankData = datas[position]
        holder.bind(gankData.url)
    }

    fun setNewData(newData: List<GankData>) {
        datas.clear()
        datas.addAll(newData)
        notifyDataSetChanged()
    }

    inner class TopImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivTopImageView: ImageView = view.findViewById(
            R.id.iv_top_img
        )

        fun bind(url: String) {
            ivTopImageView.load(url) {
                crossfade(true)
            }
        }
    }
}