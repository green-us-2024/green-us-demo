package kr.ac.kpu.green_us.common

import kr.ac.kpu.green_us.common.api.RetrofitAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitManager {
//baseUrl에 본인pc cmd열고 ipconfig를 통해서 주소 확인한 다음 자신의 컴퓨터 주소로 바꿔줘야함. + 휴대폰으로 할경우 휴대폰의 와이파이도 같은 주소로 연결이 되어야함.
    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.25.6:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val api: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)
    }

}