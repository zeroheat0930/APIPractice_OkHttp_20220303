package com.zeroheat.apipractice_okhttp_20220303

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.zeroheat.apipractice_okhttp_20220303.databinding.ActivityViewTopicDetailBinding
import com.zeroheat.apipractice_okhttp_20220303.datas.TopicData
import com.zeroheat.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class VIewTopicDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewTopicDetailBinding

//    보여주게 될 토론 주제 데이터 > 이벤트처리, 데이터 표현 등 여러 함수에서 사용
    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

        setTopicDataToUi()
        getTopicDetailFromServer()
    }

    fun setTopicDataToUi(){

//        토론 주제에 대한 데이터들을, UI에 반영하는 함수.
//        화면 초기 진입 실행 + 서버에서 다시 받아왔을때도 실행.
        binding.txtTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopicBackground)
    }

    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(mContext, mTopicData.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

//                토론 정보 JSONObject (topicObj) => TopicData() 형태로 변환 (여러 화면에서 진행. 함수로 만들어두자)
                val topicData = TopicData.getTopicDataFromJson(topicObj)

//                변환된 객체를, mTopicData로 다시 대입. => UI 반영도 다시 실행.
                mTopicData = topicData

                runOnUiThread {
                    setTopicDataToUi()
                }

            }

        })

    }

}

