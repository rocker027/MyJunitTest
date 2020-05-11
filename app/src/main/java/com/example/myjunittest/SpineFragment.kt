package com.example.myjunittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * A simple [Fragment] subclass.
 */
class SpineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spine, container, false)
    }

    fun genAnim() {
        val camera: OrthographicCamera? = null

    }


}
