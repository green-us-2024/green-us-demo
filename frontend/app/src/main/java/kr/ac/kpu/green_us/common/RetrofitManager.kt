package kr.ac.kpu.green_us.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import kr.ac.kpu.green_us.adapter.LocalDateTypeAdapter
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit


class RetrofitManager {
//baseUrl에 본인pc cmd열고 ipconfig를 통해서 주소 확인한 다음 자신의 컴퓨터 주소로 바꿔줘야함. + 휴대폰으로 할경우 휴대폰의 와이파이도 같은 주소로 연결이 되어야함.
    companion object{
        private val gson = GsonBuilder()
            .registerTypeAdapter(Greening::class.java, GreeningDeserializer())
            .create()

//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()

        // Retrofit 설정

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(client)
            .build()
        val api: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)
        }
}

//fun createGson(): Gson {
//    return GsonBuilder()
//        .excludeFieldsWithoutExposeAnnotation() // @Expose가 없는 필드는 직렬화/역직렬화에서 제외
//        .create()
//}