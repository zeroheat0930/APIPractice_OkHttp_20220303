package com.zeroheat.apipractice_okhttp_20220303

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zeroheat.apipractice_okhttp_20220303.databinding.ActivityViewTopicDetailBinding

class VIewTopicDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewTopicDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

    }
}
