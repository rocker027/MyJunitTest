package com.example.myjunittest.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myjunittest.R
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.support.v4.*

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel =
            ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnVerifyRegister.setOnClickListener { view ->
            val verify = RegisterVerify()

            when {
                verify.verifyRegisterInfo(
                    etInputUserName.text.toString(),
                    etInputPassword.text.toString()) ->
                    toast("it right")
                else -> {
                    toast("input user Id / Password incorrect !! please check ~")
                }
            }
        }
    }
}
