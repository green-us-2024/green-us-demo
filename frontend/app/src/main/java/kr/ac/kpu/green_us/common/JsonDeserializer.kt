package kr.ac.kpu.green_us.common

import com.google.gson.*
import kr.ac.kpu.green_us.common.dto.Greening
import java.lang.reflect.Type

class GreeningDeserializer : JsonDeserializer<Greening> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Greening {
        val jsonObject = json.asJsonObject

        // JSON 필드를 직접 읽고 변환 -> JSON에서 LocalDate를 배열로 보내줌 -> String으로 변환
        val gStartDate = jsonObject.getAsJsonArray("gstartDate").toFormattedString()
        val gEndDate = jsonObject.getAsJsonArray("gendDate").toFormattedString()

        return Greening(
            gSeq = jsonObject.get("gseq").asInt,
            gName = jsonObject.get("gname").asString,
            gStartDate = gStartDate,
            gEndDate = gEndDate,
            gCertiWay = jsonObject.get("gcertiWay").asString,
            gInfo = jsonObject.get("ginfo").asString,
            gMemberNum = jsonObject.get("gmemberNum").asInt,
            gFreq = jsonObject.get("gfreq").asInt,
            gDeposit = jsonObject.get("gdeposit").asInt,
            gTotalCount = jsonObject.get("gtotalCount").asInt,
            gNumber = jsonObject.get("gnumber").asInt
        )
    }

    private fun JsonArray.toFormattedString(): String {
        return if (this.size() == 3) {
            String.format("%04d-%02d-%02d", this[0].asInt, this[1].asInt, this[2].asInt)
        } else {
            "" // 잘못된 데이터 처리
        }
    }
}