package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.Participate
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.ActivityGreeningDetailSubBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// 간단한 그리닝 정보만 담은 상세페이지 보여줌
class GreeningDetailSubActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGreeningDetailSubBinding
    lateinit var auth: FirebaseAuth
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreeningDetailSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var green_state1 = intent.getStringExtra("ing")
        var green_state2 = intent.getStringExtra("end")
        var green_state3 = intent.getStringExtra("open")

        val gSeq: Int = intent.getIntExtra("gSeq", -1)

        if(gSeq <= -1){
            //gSeq조회 실패한 경우 예외처리 -> 로그아웃하고 초기화면으로
            Log.e("GreeningDetailSubActivity","gSeq 실패")
        }else{
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.getGreeningById(gSeq).enqueue(object : Callback<Greening> {
                override fun onResponse(call: Call<Greening>, response: Response<Greening>) {
                    if (response.isSuccessful) {
                        val greening = response.body() ?: null
                        if(greening != null){
                            val greenWeek = if (greening.gFreq != 0 && greening.gNumber != 0) {
                                greening.gNumber!! / greening.gFreq!!
                            } else { 0 }

                            // greening.gStartDate는 "yyyy-MM-dd" 형식의 문자열
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            val startDate = LocalDate.parse(greening.gStartDate, formatter)


                            // 이미지 로드
                            val gseq = gSeq.toString()
                            val imgName = gseq
                            val storage = Firebase.storage
                            val ref = storage.getReference("greeningImgs/").child(imgName)
                            ref.downloadUrl.addOnSuccessListener {
                                    uri -> Glide.with(this@GreeningDetailSubActivity).load(uri).into(binding.imgGreening)
                            }

                            binding.greeningTitle.text = greening.gName ?: ""
                            binding.tagTerm.text = "${greenWeek}주"
                            binding.tagFreq.text = "주${greening.gFreq}회"
                            binding.tagCertifi.text = greening.gCertiWay
                            binding.tvStartDate.text =
                                "${startDate.monthValue}월 ${startDate.dayOfMonth}일부터 시작"
                            binding.tvParticipateFee.text = "${greening.gDeposit}"
                            binding.textView10.text = when(greening.gKind){
                                1,3 -> "활동형" //1->공식 3->회원
                                2,4 -> "구매형" //2->공식 4->회원
                                else -> ""
                            }

                            if(green_state1 == "ing_state"){
                                binding.button.setText("인증하기")
                                // 리뷰 작성 버튼 클릭 시
                                binding.button.setOnClickListener{
                                    val intent = Intent(this@GreeningDetailSubActivity, CertifyGreeningActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            else if(green_state2 == "end_state"){
                                binding.button.setText("리뷰 작성")
                                // 리뷰 작성 버튼 클릭 시
                                binding.button.setOnClickListener{
                                    val intent = Intent(this@GreeningDetailSubActivity, SubActivity::class.java)
                                    intent.putExtra("3","my_review_write")
                                    startActivity(intent)
                                }
                            }
                            else if(green_state3 == "open_state"){
                                binding.button.visibility = View.GONE
                            }
/*
                            //참여하기 버튼
                            binding.button.setOnClickListener {
                                getUserByEmail { user ->
                                    if (user != null) {
                                        val participate = Participate(user = user, greening = greening)
                                        val apiService =
                                            RetrofitManager.retrofit.create(RetrofitAPI::class.java)
                                        apiService.registerParticipate(participate)
                                            .enqueue(object : Callback<Participate> {
                                                override fun onResponse(
                                                    call: Call<Participate>,
                                                    response: Response<Participate>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        Log.d("GreeningDetailSubActivity", "참여 등록 완료")
                                                        Toast.makeText(application,"${greening.gName}에 참여 완료", Toast.LENGTH_SHORT).show()
                                                        //메인 화면으로 이동
                                                    } else {
                                                        Log.e("GreeningDetailSubActivity", "그리닝 참여 실패: ${response.code()}, ${response.errorBody()?.string()}")
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<Participate>,
                                                    t: Throwable
                                                ) {
                                                    Log.e("GreeningDetailSubActivity", "서버 통신 중 오류 발생", t)
                                                    // 실패 처리 로직
                                                }
                                            })
                                    }
                                }
                            }

 */
                        }
                    } else {
                        Log.e("GreeningDetailSubActivity", "Greening 데이터 로딩 실패: ${response.code()}")
                    }
                }

                override fun onFailure(p0: Call<Greening>, p1: Throwable) {
                    Log.e("GreeningDetailSubActivity", "서버 통신 중 오류 발생", p1)
                }
            })
        }

        binding.btnEsc.setOnClickListener {
            finish()
        }
    }

    private fun getUserByEmail(callback: (User?)->Unit) {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val currentEmail = currentUser?.email

        //로그인중인 email에 해당하는 user 가져오는 코드
        if(currentEmail != null){
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.getUserbyEmail(currentEmail).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    }else{
                        Log.e("GreeningDetailSubActivity", "사용자 조회 실패: ${response.code()}, ${response.errorBody()?.string()}")
                        callback(null)
                        //실패 처리 로직
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("GreeningDetailSubActivity", "서버 통신 중 오류 발생", t)
                    callback(null)
                    // 실패 처리 로직
                }
            })
        }else{
            Log.e("GreeningOpenFragment", "로그인된 이메일을 가져올 수 없습니다.")
            callback(null)
            //로그인된 Email을 못가져온 경우
            //로그아웃 시키고 처음 화면으로 가도록
        }
    }

}