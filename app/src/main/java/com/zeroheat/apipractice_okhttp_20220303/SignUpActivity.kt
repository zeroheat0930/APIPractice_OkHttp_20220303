package com.zeroheat.apipractice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zeroheat.apipractice_okhttp_20220303.databinding.ActivitySignUpBinding
import com.zeroheat.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {


    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding =  DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setValues()
        setupEvents()
    }


    override fun setupEvents() {

        binding.btnSignUp.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            ServerUtil.putRequestSignUp(
                inputEmail,
                inputPassword,
                inputNickname,
                object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {


                    }

                }
            )

        }

    }

    override fun setValues() {

    }









}