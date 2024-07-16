package kr.ac.kpu.green_us.common.api

import kr.ac.kpu.green_us.common.dto.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("/users/new")
    fun registerUser(@Body user: Users): Call<Users>
}