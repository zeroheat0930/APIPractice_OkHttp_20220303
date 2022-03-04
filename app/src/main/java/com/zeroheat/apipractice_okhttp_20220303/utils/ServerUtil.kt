package com.zeroheat.apipractice_okhttp_20220303.utils

import okhttp3.*
import java.io.IOException

class ServerUtil {

//    서버에 Request를 날리는 역할.
//    함수를 만들려고 하는데, 어떤 객체가 실행해도 결과만 잘 나오면 그만인 함수
//    코틀린에서 static에 해당하는 개념? companion object {} 에 만들자.


    companion object{

//        서버 컴퓨터 주소만 변수로 저장 (관리 일원화) => 외부 노출 X
       private val BASE_URL = "http://54.180.52.26"


//        로그인 기능 호출 함수
        fun postRequestLogin (id: String, pw: String) {

//            Request 제작 -> 실제 호출 -> 서버의 응답을, 화면에 전달

//            제작 1) 어느 주소(url)로 접근할지? => 서버 주소 + 기능 주소
            val urlString = "${BASE_URL}/user"

//            제작 2) 파라미터 담아주기 => 어떤 이름표 / 어느 공간에

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

//            제작 3) 모든 Request 정보를 종합한 객체 생성. (어느 주소로 + 어느 메쏘드로 + 어떤 파라미터를)
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

//            종합한 Request도 실제 호출은 해 줘야 API 호출이 실행됨. (startActivity 같은 동작 필요함)
//            실제 호출 : 앱이 클라이언트로써 동작 > OkHttpClient 클래스

            val client = OkHttpClient()


//            OkHttpClinet 객체를 이용 > 서버에 로그인 기능 실제 호출
//            호출을 했으면, 서버가 수행한 결과를 받아서 처리.
//            => 서버에 다녀와서 할 일을 등록 : enqueue( Callback)

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                }

            })



        }
    }


}





