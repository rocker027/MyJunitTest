package com.example.myjunittest.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myjunittest.R
import kotlinx.android.synthetic.main.fragment_register.*

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
                    etInputPassword.text.toString()) -> {
                    Toast.makeText(context,"it right",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context,"input user Id / Password incorrect !! please check ~",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
