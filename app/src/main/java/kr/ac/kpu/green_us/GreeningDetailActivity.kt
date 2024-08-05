package kr.ac.kpu.green_us

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.databinding.ActivityGreeningDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GreeningDetailActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityGreeningDetailBinding
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var viewAdapter: RecyclerView.Adapter<*>
//    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreeningDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gSeq: Int = intent.getIntExtra("gSeq", -1)

        if(gSeq != -1){
            //gSeq조회 실패한 경우 예외처리 -> 로그아웃하고 초기화면으로
            Log.e("GreeningDetailActivity","gSeq 실패")
        }else{
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.getGreeningById(gSeq).enqueue(object : Callback<Greening> {
                override fun onResponse(call: Call<Greening>, response: Response<Greening>) {
                    if (response.isSuccessful) {
                        val greening = response.body() ?: null
                        if(greening != null){
                            var greenWeek = 0
                            if(greening.gFreq != 0 && greening.gNumber != 0 && greening.gFreq != null && greening.gNumber != null) {
                                greenWeek = (greening.gNumber)/(greening.gFreq)
                            }

                            // greening.gStartDate는 "yyyy-MM-dd" 형식의 문자열
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            val startDate = LocalDate.parse(greening.gStartDate, formatter)

                            // 년/월/일 추출하여 변수에 저장
                            val year = startDate.year
                            val month = startDate.monthValue
                            val day = startDate.dayOfMonth

                            binding.greeningTitle.text = greening.gName ?: ""
                            binding.tagTerm.text = "${greenWeek}주"
                            binding.tagFreq.text = "주${greening.gFreq}회"
                            binding.tagCertifi.text = greening.gCertiWay
                            binding.tvStartDate.text = "${month}월 ${day}일부터 시작"
                            binding.tvParticipateFee.text = "${greening.gDeposit}"
                            binding.tvHowto.text = "${greening.gInfo}"
                        }

                    } else {
                        Log.e("GreeningDetailActivity", "Greening 데이터 로딩 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Greening>, t: Throwable) {
                    Log.e("GreeningDetailActivity", "서버 통신 중 오류 발생", t)
                }
            })
        }

        //이전 화면으로
        binding.btnEsc.setOnClickListener {
            this.finish()
        }
    }
}