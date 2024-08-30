package kr.ac.kpu.green_us

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.WithdrawAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Withdraw
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
class WithdrawsActivity : AppCompatActivity() {
    private lateinit var withdrawAdapter: WithdrawAdapter
    private var withdraws: List<Withdraw> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraws)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_withdraws)
        recyclerView.layoutManager = LinearLayoutManager(this)
        withdrawAdapter = WithdrawAdapter(withdraws)
        recyclerView.adapter = withdrawAdapter

        fetchWithdraws()
    }

    private fun fetchWithdraws() {
        val retrofitAPI = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        retrofitAPI.getWithdraws().enqueue(object : Callback<List<Withdraw>> {
            override fun onResponse(call: Call<List<Withdraw>>, response: Response<List<Withdraw>>) {
                if (response.isSuccessful) {
                    withdraws = response.body() ?: emptyList()
                    withdrawAdapter = WithdrawAdapter(withdraws)
                    findViewById<RecyclerView>(R.id.recyclerview_withdraws).adapter = withdrawAdapter
                } else {
                    Log.d("WithdrawsActivity", "출금 데이터 API 응답 실패: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Withdraw>>, t: Throwable) {
                Log.d("WithdrawsActivity", "출금 데이터 API 호출 실패: ${t.message}")
            }
        })
    }
}
