package kr.ac.kpu.green_us

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.ActivityGreenOpenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

// 그리닝 개설 - 그리닝 정보 입력
class GreenOpenActivity : AppCompatActivity() {
    lateinit var binding: ActivityGreenOpenBinding
    lateinit var auth: FirebaseAuth
    var start_date = ""
    var week = 0 //주 선택 값 저장할 변수
    var certiWay = ""
    var photo = ""
    var freq = 0
    var user:User? = null

    //날짜 형식 정의
    //private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreenOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val currentEmail = currentUser?.email

        // 날짜 입력 editText 달력으로만 받게 하기 위해 비활성화
        binding.startDateEt.setClickable(false)
        binding.startDateEt.setFocusable(false)

        // 사진명 입력 editText 정보 가져와서 받게 하기 위해 비활성화
        binding.uploadPictureEt.setClickable(false)
        binding.uploadPictureEt.setFocusable(false)


        // 이전버튼
        binding.btnEsc.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("key3", "mypage")
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
            onBackPressedCallback.handleOnBackPressed()
        }

        // 개설하기 버튼
        binding.openGreenBtn.setOnClickListener {
            val gName = binding.nameEt.text.toString()
            val gInfo = binding.greenDetailEt.text.toString()
            val gDeposit = binding.depositEx.text.toString().trim().toInt()
            val gNumber = week*freq
            if (start_date.isEmpty()){
                Toast.makeText(this, "시작일을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val endDate = calculateEndDate(start_date, week)
            if (endDate != null){
                //종료일이 잘 계산되었을 때
                Log.d("GreenOpenActivity", "종료일: $endDate")
            }else{
                //종료일 계산이 잘못되었을 때
                Log.e("GreenOpenActivity", "종료일 계산 실패")
                return@setOnClickListener
            }
            if (certiWay.isEmpty()){
                //인증방법이 입력되지 않았을 때
                Log.e("GreenOpenActivity", "인증수단 입력 누락")
                Toast.makeText(this, "인증수단을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                //인증방법이 입력 되었을 때
                Log.d("GreenOpenActivity", "인증수단: $certiWay")
            }

            //인증횟수
            if (gNumber > 0){
                //인증횟수가 잘 계산되었을 때
                Log.d("GreenOpenActivity", "인증횟수 계산 완료")
            }else{
                //인증횟수 계산이 잘못되었을 때
                Toast.makeText(this, "기간과 인증빈도를 선택해주세요.", Toast.LENGTH_SHORT).show()
                Log.e("GreenOpenActivity", "인증횟수 계산 실패 : : $gNumber")
                return@setOnClickListener
            }

            getUserByEmail()

            var userSeq = 3

            //데이터 전송
            val greening = Greening(
                gSeq = 0,
                gName = gName,
                gStartDate = start_date,
                gEndDate = endDate,
                gCertiWay = certiWay,
                gInfo = gInfo,
                gMemberNum = 0,
                gFreq = freq,
                gDeposit = gDeposit,
                gTotalCount = 0,
                gNumber = gNumber,
                gKind = 3,
                user = user)
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.registerGreening(greening).enqueue(object : Callback<Greening> {
                override fun onResponse(call: Call<Greening>, response: Response<Greening>) {
                    if (response.isSuccessful) {
                        Log.d("GreenOpenActivity", "서버로 데이터 전송 성공")

                    } else {
                        Log.e("GreenOpenActivity", "서버로 데이터 전송 실패: ${response.code()}, ${response.errorBody()?.string()}")
                        // 실패 처리 로직
                    }
                }

                override fun onFailure(call: Call<Greening>, t: Throwable) {
                    Log.e("GreenOpenActivity", "서버 통신 중 오류 발생", t)
                    // 실패 처리 로직
                }
            })


            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key2_3", "open")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 달력 이미지 클릭
        binding.btnDate.setOnClickListener {
            showDatePicker()
        }

        //기간 선택
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            week = when (checkedId){
                R.id.week1 -> 1
                R.id.week2 -> 2
                R.id.week3 -> 3
                R.id.week4 -> 4
                R.id.week5 -> 5
                R.id.week6 -> 6
                R.id.week7 -> 7
                R.id.week8 -> 8
                else -> 0
            }
        }

        //기간 선택
        binding.radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            certiWay = when (checkedId){
                R.id.only_camera -> "카메라"
                R.id.camera_gallery -> "카메라/갤러리"
                else -> ""
            }
        }

        //인증빈도 선택
        binding.radioGroup3.setOnCheckedChangeListener { _, checkedId ->
            freq = when (checkedId){
                R.id.frequency1 -> 1
                R.id.frequency2 -> 2
                R.id.frequency3 -> 3
                R.id.frequency4 -> 4
                R.id.frequency5 -> 5
                R.id.frequency6 -> 6
                R.id.frequency7 -> 7
                else -> 0
            }
        }


        // 사진 업로드 이미지
        binding.btnUpload.setOnClickListener {
            val galleryPermissionCheck = ContextCompat.checkSelfPermission(
                this@GreenOpenActivity,
                android.Manifest.permission.READ_MEDIA_IMAGES
            )
            if (galleryPermissionCheck != PackageManager.PERMISSION_GRANTED) { // 권한이 없는 경우
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    1000
                )
            }
            if (galleryPermissionCheck == PackageManager.PERMISSION_GRANTED) { //권한이 있는 경우
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                activityResult.launch(intent)
            }
        }
    }

    // 달력 다이얼로그
    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        var dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            var year = y.toString()
            var month = (m + 1).toString().padStart(2,'0')
            var date = d.toString().padStart(2,'0')
            start_date = "$year-$month-$date"
            binding.startDateEt.setText(start_date)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000;
        dpd.show()
    }

    //종료일 계산
    private fun calculateEndDate(startDate: String, week: Int): String?{
        if(week==0) return null

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(startDate) ?: return null
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_YEAR, 7*week)
        return sdf.format(cal.time)
    }

    // 이미지 저장
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val uri = it.data!!.data
        if(uri != null){
            photo = uri.toString()
            binding.uploadPictureEt.setText(uri.getLastPathSegment())
        }else{
            //이미지 저장 실패시 예외처리
        }
    }
    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            isEnabled = false // 콜백 비활성화 -> 뒤로가기가 한 번만 실행되도록
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getUserByEmail() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val currentEmail = currentUser?.email

        //로그인중인 email에 해당하는 user 가져오는 코드
        if(currentEmail != null){
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.getUserbyEmail(currentEmail).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        user = response.body()
                        if (user != null) {
                            //회원이 존재하는 경우
                            Log.d("GreeningOpenActivity", "회원 찾음 : ${user!!.userEmail}")
                        } else {
                            //회원이 존재하지 않는 경우
                            Log.e("GreeningOpenActivity", "회원 못찾음")
                        }
                    }else{
                        Log.e("GreeningOpenActivity", "사용자 조회 실패: ${response.code()}, ${response.errorBody()?.string()}")
                        //실패 처리 로직
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("GreeningOpenActivity", "서버 통신 중 오류 발생", t)
                    // 실패 처리 로직
                }
            })
        }else{
            //로그인된 Email을 못가져온 경우
            //로그아웃 시키고 처음 화면으로 가도록
        }
    }

}