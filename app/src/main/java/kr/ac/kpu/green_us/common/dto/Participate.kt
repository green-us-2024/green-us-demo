package kr.ac.kpu.green_us.common.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Participate (
    @SerializedName("pSeq") @Expose val pSeq: Int = 0,
    @SerializedName("user") @Expose val user: User? = null,
    @SerializedName("greening") @Expose val greening: Greening? = null,
    @SerializedName("pComplete") @Expose val pComplete: String? = "N",
    @SerializedName("pCount") @Expose val pCount: Int? = 0
)