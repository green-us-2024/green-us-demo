package kr.ac.kpu.green_us

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.PointAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Prize
import kr.ac.kpu.green_us.databinding.ActivityPointBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PointActivity : AppCompatActivity() {
    lateinit var binding: ActivityPointBinding
    lateinit var recyclerView: RecyclerView
    lateinit var pointAdapter: PointAdapter
    private var allPrizes: List<Prize> = emptyList()
    private var selectedMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1 // 기본값은 현재 월
    private var currentBalance: Int = 0 // 현재 잔액
    private var totalWithdrawn: Int = 0 // 총 출금 금액 추가
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 버튼 설정
        binding.btnEsc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key3", "mypage")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        // RecyclerView 설정
        recyclerView = findViewById(R.id.recyclerview_point)
        recyclerView.layoutManager = LinearLayoutManager(this)
        pointAdapter = PointAdapter(emptyList())
        recyclerView.adapter = pointAdapter

        // API 호출
        fetchPrizes()

        // 출금하기 버튼 설정
        binding.pointWithdraw.setOnClickListener {
            val intent = Intent(this, PointWithdrawActivity::class.java)
            startActivity(intent)
        }

        // 월 변경 버튼 설정
        binding.btnBack.setOnClickListener {
            if (selectedMonth > 1) {
                selectedMonth--
                updateRecyclerView()
            }
        }

        binding.btnFront.setOnClickListener {
            if (selectedMonth < 12) {
                selectedMonth++
                updateRecyclerView()
            }
        }
        sharedPreferences = getSharedPreferences("point_prefs", Context.MODE_PRIVATE)
        totalWithdrawn = sharedPreferences.getInt("totalWithdrawn", 0) // 저장된 출금 금액 불러오기


        // 출금액이 전달되었는지 확인하고 잔액을 업데이트합니다.
        val withdrawAmount = intent.getIntExtra("withdrawAmount", 0)
        Log.d("PointActivity", "받은 출금 금액: $withdrawAmount") // 로그 출력
        if (withdrawAmount > 0) {
            totalWithdrawn += withdrawAmount // 총 출금 금액을 누적시킴
            saveWithdrawnAmount(totalWithdrawn) // 출금 금액 저장
        }
        fetchPrizes()
    }

    private fun fetchPrizes() {
        val retrofitAPI = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        retrofitAPI.getPrizes().enqueue(object : Callback<List<Prize>> {
            override fun onResponse(call: Call<List<Prize>>, response: Response<List<Prize>>) {
                if (response.isSuccessful) {
                    allPrizes = response.body() ?: emptyList()
                    updateRecyclerView()
                    Log.d(
                        "PointActivity",
                        "총 포인트: ${allPrizes.sumOf { it.prizeMoney ?: 0 }}"
                    ) // 포인트 합계 로그
                } else {
                    Log.d("PointActivity", "API 응답 실패: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Prize>>, t: Throwable) {
                Log.d("PointActivity", "API 호출 실패: ${t.message}")
            }
        })
    }

    private fun updateRecyclerView() {
        val totalPoints = allPrizes.sumOf { it.prizeMoney ?: 0 }
        currentBalance = totalPoints - totalWithdrawn // 출금 금액을 반영하여 잔액 초기화
        Log.d("PointActivity", "총 포인트(출금 반영): $currentBalance") // 포인트 합계 로그
        binding.remainingPoint.text = "$currentBalance"

        // 선택된 월에 따른 필터링
        val filteredPrizes = allPrizes.filter {
            val prizeDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.prizeDate ?: "") ?: Date()
            val calendar = Calendar.getInstance().apply { time = prizeDate }
            calendar.get(Calendar.MONTH) + 1 == selectedMonth
        }
        pointAdapter.updateData(filteredPrizes)
        binding.month.text = selectedMonth.toString()
    }


    private fun updateBalance(amount: Int) {
        Log.d("PointActivity", "잔액 업데이트 전: $currentBalance, 출금액: $amount") // 로그 출력
        currentBalance += amount
        totalWithdrawn += -amount // 총 출금 금액 누적
        saveWithdrawnAmount(totalWithdrawn) // 출금 금액 저장
        Log.d("PointActivity", "잔액 업데이트 후: $currentBalance, 총 출금: $totalWithdrawn") // 로그 출력
        binding.remainingPoint.text = "$currentBalance"
        fetchPrizes() // 최신 데이터를 다시 가져옵니다.
    }

    private fun saveWithdrawnAmount(amount: Int) {
        with(sharedPreferences.edit()) {
            putInt("totalWithdrawn", amount)
            apply()
        }
    }



}