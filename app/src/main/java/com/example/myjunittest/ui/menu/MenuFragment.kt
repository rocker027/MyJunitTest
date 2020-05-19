package com.example.myjunittest.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myjunittest.R
import com.example.myjunittest.databinding.FragmentMenuBinding
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
                ViewModelProvider(this).get(MenuViewModel::class.java)
        val binding = FragmentMenuBinding.inflate(inflater,container,false)
        binding.fragment = this
        return binding.root
    }

    fun navigateToEcPadingAnimation(){
        findNavController().navigate(R.id.action_navigation_menu_to_ecPagingAnimationFragment)
    }
}
