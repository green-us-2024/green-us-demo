package kr.ac.kpu.green_us.common.api

import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.Notice
import kr.ac.kpu.green_us.common.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitAPI {
    @POST("/users/new")
    fun registerUser(@Body user: User): Call<User>

    @GET("/users/byEmail/{email}")
    fun getUserbyEmail(@Path("email") email: String): Call<User>

    @GET("/users/seqByEmail/{email}")
    fun getUserSeqByEmail(@Path("email") email: String): Call<Int>

    @POST("/greening/new")
    fun registerGreening(@Body greening: Greening): Call<Greening>

    @GET("/greening/byId/{id}")
    fun getGreeningById(@Path("id") id: Int): Call<Greening>

    @GET("/greening/list")
    fun getGreening(): Call<List<Greening>>

    @GET("/greening/list/do")
    fun getDoGreening(): Call<List<Greening>>

    @GET("/greening/list/buy")
    fun getBuyGreening(): Call<List<Greening>>

    @GET("/greening/list/new")
    fun getNewGreening(): Call<List<Greening>>

    @GET("/greening/list/pop")
    fun getPopGreening(): Call<List<Greening>>

    @GET("/greening/byUserSeq/{userSeq}")
    fun getGreeningByUserSeq(@Path("userSeq") userSeq: Int): Call<List<Greening>>

    @GET("/notice/list")
    fun getNotices():Call<List<Notice>>

}