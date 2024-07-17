package kr.ac.kpu.green_us.common.api

import kr.ac.kpu.green_us.common.dto.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @POST("/users/new")
    fun registerUser(@Body user: Users): Call<Users>
}