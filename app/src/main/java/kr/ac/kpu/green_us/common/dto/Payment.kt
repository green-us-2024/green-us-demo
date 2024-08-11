package kr.ac.kpu.green_us.common.dto

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("userSeq") var userSeq: Int = 0,
    @SerializedName("paymentSeq") var paymentSeq: Int = 0,
    @SerializedName("paymentContent") var paymentContent: String? = null,
    @SerializedName("paymentMethod") var paymentMethod: String? = null,
    @SerializedName("paymentDate") var paymentDate: String? = null,
    @SerializedName("paymentMoney") var paymentMoney: Int? = null
)