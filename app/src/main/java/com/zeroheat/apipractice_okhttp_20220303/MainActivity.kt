package com.zeroheat.apipractice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zeroheat.apipractice_okhttp_20220303.databinding.ActivityMainBinding
import com.zeroheat.apipractice_okhttp_20220303.utils.ServerUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvent()
        setValues()
    }

    fun setupEvent(){
        binding.btnLogin.setOnClickListener {

//            id / pw 추출

            val inputId = binding.edtId.text.toString()
            val inputPw = binding.edtPassword.text.toString()

//            API서버에 아이디 / 비번을 보내서 실제로 회원인지 검사 => 로그인 시도

            ServerUtil.postRequestLogin(inputId, inputPw)

        }

    }

    fun setValues(){

    }



}