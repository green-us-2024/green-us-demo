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

    @POST("/greening/new")
    fun registerGreening(@Body greening: Greening): Call<Greening>

    @GET("/greening/list")
    fun getGreening(): Call<List<Greening>>

    @GET("/notice/list")
    fun getNotices():Call<List<Notice>>
}