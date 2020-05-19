package com.example.myjunittest.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myjunittest.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)
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
//                    RegisterRepository(context!!).saveUserId(etInputUserName.text.toString())
                }
                else -> {
                    Toast.makeText(context,"input user Id / Password incorrect !! please check ~",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val navController = findNavController(this)
//        navigate_btn.setOnClickListener {
//            navController.navigate(R.id.destination_b)
//        }

        val navController = findNavController()
        btn_spine.setOnClickListener{
            navController.navigate(R.id.action_loginFragment_to_spineFragment)
        }
    }
}
