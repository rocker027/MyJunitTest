package com.example.myjunittest.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myjunittest.R
import com.example.myjunittest.ui.home.ProductResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        val jsonStr = """  {
        "vote_count": 2026,
        "id": 19404,
        "title": "Example Movie",
        "image_path": "/example-movie-image.jpg",
        "overview": "Overview of example movie"
    }"""

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Movie::class.java)
        val movie = adapter.fromJson(jsonStr)
        println(movie)


        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "vote_count") val voteCount: Int = -1,
    val id: Int,
    val title: String,
    @Json(name = "image_path") val imagePath: String,
    val overview: String
)