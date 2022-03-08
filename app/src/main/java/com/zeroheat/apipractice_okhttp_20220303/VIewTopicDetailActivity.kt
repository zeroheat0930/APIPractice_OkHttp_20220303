package com.zeroheat.apipractice_okhttp_20220303

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.zeroheat.apipractice_okhttp_20220303.databinding.ActivityViewTopicDetailBinding
import com.zeroheat.apipractice_okhttp_20220303.datas.ReplyData
import com.zeroheat.apipractice_okhttp_20220303.datas.TopicData
import com.zeroheat.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class VIewTopicDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewTopicDetailBinding

//    보여주게 될 토론 주제 데이터 > 이벤트처리, 데이터 표현 등 여러 함수에서 사용
    lateinit var mTopicData : TopicData

    val mReplyList = ArrayList<ReplyData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

//        btnVote1 클릭 => 첫 진영의 id값을 찾아서, 거기에 투표.
//        서버에 전달 => API 활용.

        binding.btnVote1.setOnClickListener {

//            서버의 투표 API 호출
        ServerUtil.postRequestVote(mContext, mTopicData.sideList[0].id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
//                    토스트로, 서버가 알려준 현재 상황 (신규 투표 or 재투표 or 취소 등)
                val message = jsonObj.getString("message")

                runOnUiThread {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }

//                    변경된 득표 현황을 다시 불러오자.
                getTopicDetailFromServer()
            }
        })

        }

        binding.btnVote2.setOnClickListener {

        ServerUtil.postRequestVote(mContext, mTopicData.sideList[1].id, object : ServerUtil.JsonResponseHandler {



            override fun onResponse(jsonObj: JSONObject) {
//                    토스트로, 서버가 알려준 현재 상황 (신규 투표 or 재투표 or 취소 등)
                    val message = jsonObj.getString("message")

                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

//                    변경된 득표 현황을 다시 불러오자.
                    getTopicDetailFromServer()
                }
            })
        }


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

//        1번진영 제목, 2번진영 제목
        binding.txtSide1.text = mTopicData.sideList[0].title
        binding.txtSide2.text = mTopicData.sideList[1].title

//        1번진영 득표수, 2번진영 득표수
        binding.txtVoteCount1.text = "${mTopicData.sideList[0].voteCount}"
        binding.txtVoteCount2.text = "${mTopicData.sideList[1].voteCount}"

//        내가 선택한 진영이 있을때, (투표 해놨을때)
//        이미 투표한 진영은 문구를 변경하자. ("투표 취소")

        if (mTopicData.mySelectedSide != null) {

//            첫번째 진영을 투표했는지?
//            두번째 진영을 투표했는지?

            if (mTopicData.mySelectedSide!!.id == mTopicData.sideList[0].id) {
//                첫 진영에 투표 한경우.
                binding.btnVote1.text = "투표 취소"
                binding.btnVote2.text = "다시 투표"
            }
            else {
//                두번째 진영에 투표.
                binding.btnVote1.text = "다시 투표"
                binding.btnVote2.text = "투표 취소"
            }

        }
        else {
//            아무데도 투표하지 않은 경우.
            binding.btnVote1.text = "투표 하기"
            binding.btnVote2.text = "투표 하기"
        }



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

//                topicObj 내부에는, replies라는 댓글 목록 JSONArray도 들어있다.
//                mReplyList에 넣어주자.

                val repliesArr = topicObj.getJSONArray("replies")

                for (i in   0 until repliesArr.length()) {
                    val replyObj =  repliesArr.getJSONObject(i)

                    mReplyList.add(  ReplyData.getReplyDataFromJson(replyObj)  )

                }

//                서버의 동작이므로, 어댑터 세팅보다 늦게 끝날수 있다. (notifyDataSetChanged)




            }

        })

    }

}

