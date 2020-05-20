package com.example.myjunittest.ui.menu.animation

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.myjunittest.R
import com.example.myjunittest.utils.layoutmanager.CircleLayoutManager


class EcPagingAnimationFragment : Fragment() {

    companion object {
        fun newInstance() =
            EcPagingAnimationFragment()
    }

    private lateinit var viewModel: EcPagingAnimationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EcPagingAnimationViewModel::class.java)
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_ec_paging_animation, container, false)
        setupActionBar()
        initView(view!!)
        setupRecyclerView(view.findViewById(R.id.rv_items))
        return view
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        val circleLayoutManager = CircleLayoutManager(context!!)
        recyclerView.layoutManager = circleLayoutManager
        recyclerView.adapter = SwipItemsAdapter(viewModel.getFakeItems())
    }

    private fun initView(view: View) {
        // 讀取圖片
        val imageView = view.findViewById<ImageView>(R.id.iv_img)
        imageView.load("https://placeimg.com/1080/1080/any") {
            crossfade(true)
        }
    }

    private fun setupActionBar() {
        activity?.actionBar.also {
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeButtonEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 新增返回功能
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}

/**
 * 商品滑動RecyclerView Adapter
 *
 * @property items
 */
class SwipItemsAdapter(val items: List<Int>) :
    RecyclerView.Adapter<SwipItemsAdapter.SwipItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipItemViewHolder {
        return SwipItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.itemview_ec_paging_animation, parent, false)
        )
    }

    override fun getItemCount(): Int = if (items.isNullOrEmpty()) 0 else items.size

    override fun onBindViewHolder(holder: SwipItemViewHolder, position: Int) {
        holder.bind("https://placeimg.com/50/50/any")
    }




    inner class SwipItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItemImg = itemView.findViewById<ImageView>(R.id.iv_item_img)

        fun bind(url: String) {
            ivItemImg.load(url) {
                crossfade(true)
            }
        }
    }
}
