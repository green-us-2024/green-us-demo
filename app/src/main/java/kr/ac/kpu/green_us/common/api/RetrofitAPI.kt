package kr.ac.kpu.green_us.common.api

import kr.ac.kpu.green_us.common.dto.Certify
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.Notice
import kr.ac.kpu.green_us.common.dto.Participate
import kr.ac.kpu.green_us.common.dto.Payment
import kr.ac.kpu.green_us.common.dto.Users
import kr.ac.kpu.green_us.common.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @POST("/users/new")
    fun registerUser(@Body user: Users): Call<Users>

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

    @POST("/participate/new")
    fun registerParticipate(@Body participate: Participate): Call<Participate>

    @GET("/participate/GreeningByUserSeq/{userSeq}")
    fun findGreeningByUserSeq(@Path("userSeq") userSeq: Int): Call<List<Greening>>

    @FormUrlEncoded
    @POST("/certify/new")
    fun registerCertify(
        @Field("userEmail") userEmail: String,
        @Field("gSeq") gSeq: Int,
        @Field("certifyDate") certifyDate: String // ISO 8601 형식으로 날짜를 전달
    ): Call<Certify>

    @GET("/byGreeningUser/{userSeq}/{gSeq}")
    fun getCertifyByUserSeqAndGSeq(
        @Path("userSeq") userSeq: Int,
        @Path("gSeq") gSeq: Int)
    :Call<List<Certify>>

    @GET("/notice/list")
    fun getNotices():Call<List<Notice>>

    @POST("/payment/new")
    fun createPayment(@Body payment: Payment): Call<Payment>

}