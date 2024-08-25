package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.common.dto.Withdraw
import kr.ac.kpu.green_us.databinding.ActivityPointWithdrawBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.math.log

class PointWithdrawActivity : AppCompatActivity() {
    lateinit var binding: ActivityPointWithdrawBinding
    private val retrofitAPI = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
    private var userSeq: Int = 0 // userSeq 변수를 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 버튼 설정
        binding.btnEsc.setOnClickListener {
            finish() // 현재 액티비티 종료
        }

        // 출금 버튼 설정
        binding.complete.setOnClickListener {
            // 사용자 정보를 가져온 후 출금 요청
            getUserByEmail { user ->
                user?.let {
                    userSeq = it.userSeq
                    handleWithdrawRequest()
                } ?: run {
                    Toast.makeText(this, "사용자 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleWithdrawRequest() {
        val bank = binding.bank.text.toString()
        val accountHolder = binding.accountHolder.text.toString()
        val amountStr = binding.withdrawPoint.text.toString()
        val user = User(userSeq = userSeq)
        if (amountStr.isNotEmpty() && bank.isNotEmpty() && accountHolder.isNotEmpty()) {
            val amount = amountStr.toInt()
            Log.d("PointWithdrawActivity", "출금 금액: $amount")

            val withdraw = Withdraw(
                withdrawSeq = 0, // 서버에서 자동 생성됨
                user = user, // 사용자 ID를 설정
                withdrawContent = "출금 요청 - $bank, $accountHolder",
                withdrawDate = LocalDate.now().toString(),
                withdrawAmount = amount
            )
            Log.d("userSeq값", "사용자id: $user")
            retrofitAPI.createWithdraw(withdraw).enqueue(object : Callback<Withdraw> {
                override fun onResponse(call: Call<Withdraw>, response: Response<Withdraw>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PointWithdrawActivity, "출금 성공", Toast.LENGTH_SHORT).show()
                        updatePointInActivity(amount)
                    } else {
                        Toast.makeText(this@PointWithdrawActivity, "출금 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Withdraw>, t: Throwable) {
                    Toast.makeText(this@PointWithdrawActivity, "출금 요청 실패", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserByEmail(callback: (User?) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val currentEmail = currentUser?.email

        if (currentEmail != null) {
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.getUserbyEmail(currentEmail).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("GetUserByEmail", "사용자 조회 실패: ${response.code()}, ${response.errorBody()?.string()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("GetUserByEmail", "서버 통신 중 오류 발생", t)
                    callback(null)
                }
            })
        } else {
            Log.e("GetUserByEmail", "로그인된 이메일을 가져올 수 없습니다.")
            callback(null)
        }
    }

    private fun updatePointInActivity(amount: Int) {
        val intent = Intent(this, PointActivity::class.java).apply {
            putExtra("withdrawAmount", amount) // Int 값을 전달
            Log.d("PointWithdrawActivity", "출금 금액2: $amount")
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 기존 활동을 닫고 새로운 활동을 열기 위함
        }
        startActivity(intent)
    }
}
