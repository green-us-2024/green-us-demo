package kr.ac.kpu.green_us

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalDensity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.play.integrity.internal.q
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.adapter.CertifiedRepresentAdapter
import kr.ac.kpu.green_us.adapter.StampAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.RetrofitManager.Companion.retrofit
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Certify
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.data.CertifiedImgs
import kr.ac.kpu.green_us.databinding.ActivityCertifyGreeningBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CertifyGreeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCertifyGreeningBinding
    lateinit var bitmap: Bitmap
    lateinit var uri: Uri
    var cameraOrGallery: Int = 0
    private lateinit var auth: FirebaseAuth
    private val representImgList = mutableListOf<String>()
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1000
    private var gSeq : Int = 0



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCertifyGreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // auth 인스턴스 초기화
        auth = Firebase.auth

        gSeq = intent.getIntExtra("gSeq", -1)
        if(gSeq <= -1){
            //gSeq조회 실패한 경우 예외처리 -> 로그아웃하고 초기화면으로
            Log.e("CertifyGreeningActivity","gSeq 실패")
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

                            // 이미지 로드
                            val gseq = gSeq.toString()
                            val imgName = gseq
                            val storage = Firebase.storage
                            val ref = storage.getReference("greeningImgs/").child(imgName)
                            ref.downloadUrl.addOnSuccessListener {
                                    uri -> Glide.with(this@CertifyGreeningActivity).load(uri).into(binding.imgGreening)
                            }

                            binding.greeningTitle.text = greening.gName ?: ""
                            binding.tagTerm.text = "${greenWeek}주"
                            binding.tagFreq.text = "주${greening.gFreq}회"
                            binding.tagCertifi.text = greening.gCertiWay
                            binding.tvStartDate.text = "${month}월 ${day}일부터 시작"
                            binding.tvParticipateFee.text = "${greening.gDeposit}"
                            binding.textView10.text = when(greening.gKind){
                                1,3 -> "활동형" //1->공식 3->회원
                                2,4 -> "구매형" //2->공식 4->회원
                                else -> ""
                            }
                        }

                    } else {
                        Log.e("CertifyGreeningActivity", "Greening 데이터 로딩 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Greening>, t: Throwable) {
                    Log.e("CertifyGreeningActivity", "서버 통신 중 오류 발생", t)
                }
            })
        }
        // 초기화면셋팅
        viewInit()

        // 이전 버큰 클릭
        binding.btnEsc.setOnClickListener {
            this.finish()
        }
        // 인증버튼 클릭
        binding.button.setOnClickListener { createCamera() }

        // 전체보기 클릭
        binding.btnMoreLayout.setOnClickListener {
            val intent = Intent(this,ViewAllCertifiedImgActivity::class.java)
            startActivity(intent)
        }
    }

    fun viewInit(){

        // 스탬프 영역 어댑팅
        binding.stampsLayout.apply {
            layoutManager = GridLayoutManager(this.context,3)
            adapter = StampAdapter()
            setHasFixedSize(true)
        }

        // 이미지 3개 불러와서 인증사진 대표 3개에 어댑팅
        val layoutAdapter = CertifiedRepresentAdapter(representImgList)
        layoutAdapter.notifyDataSetChanged()

        val storage = FirebaseStorage.getInstance()
        //certificationImgs경로의 사진들 참조함
        val storageRef = storage.reference.child("certificationImgs/")

        //3개의 사진을 가져와서 각각의 url representImgList에 저장함
        storageRef.list(3).addOnSuccessListener {
            listResult -> for (img in listResult.items){
                img.downloadUrl.addOnSuccessListener { uri ->
                    representImgList.add(uri.toString())
                }.addOnSuccessListener {// url 가져오기 성공하면 화면에 뷰 어댑팅
                    binding.representImgArea.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = layoutAdapter
                        setHasFixedSize(true)
                    }
                }
            }
        }
        // 대표 인증사진 3개 클릭 리스너
        // 받은 url값을 담아 디테일 액티비티로 보냄
        layoutAdapter.itemClickListener = object :CertifiedRepresentAdapter.OnItemClickListener{
            override fun onItemClick(url:String) {
                val intent = Intent(applicationContext,CertificationImgDetailActivity::class.java)
                intent.putExtra("imgUrl",url)
                startActivity(intent)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun createCamera(){
        val dlg = CameraGalleryActivity(this)
        dlg.show()
        dlg.setOnCameraClickedListener { content ->
            if (content == 1) {
                cameraOrGallery = 1
                val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                    this@CertifyGreeningActivity,
                    android.Manifest.permission.CAMERA
                )
                val cameraPermissionCheckForLower = ContextCompat.checkSelfPermission(this@CertifyGreeningActivity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (cameraPermissionCheckForLower!=PackageManager.PERMISSION_GRANTED){
                    requestWriteExternalStoragePermission()
                }
                if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) { // 권한이 없는 경우
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.CAMERA),
                        1000
                    )
                }
                if (cameraPermissionCheck == PackageManager.PERMISSION_GRANTED) { //권한이 있는 경우
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    activityResult.launch(intent)
                }
            }
            if (content == 2) {
                cameraOrGallery = 2

                val galleryPermissionCheck = ContextCompat.checkSelfPermission(
                    this@CertifyGreeningActivity,
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
//        dlg.show()
    }
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            if(cameraOrGallery == 1){
                val extras = it.data!!.extras
                bitmap = extras!!.get("data") as Bitmap
                uri = getImageUri(this@CertifyGreeningActivity, bitmap)
                imageUpload(uri) //이미지 업로드 함수
            }
            if(cameraOrGallery == 2){
                uri = it.data?.data!!
                imageUpload(uri)
            }
        }
    }
    fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        if (inImage != null) {
            inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        }
        val path = MediaStore.Images.Media.insertImage(inContext?.getContentResolver(), inImage, "Title" + " - " + Calendar.getInstance().getTime(), null)
        return Uri.parse(path)
    }
    private fun imageUpload(uri: Uri) {
        val uid = auth.uid.toString()

        if (uri != null) {
            val storage = Firebase.storage
            //현재 로그인 한 사용자 이메일 가져오기
            val user = Firebase.auth.currentUser
//            user?.let { val email = it.email }
            val userEmail = user?.email.toString()
            Log.d("currentEmail", userEmail)

            // storage에 저장할 파일명 선언 (시간)
            val fileName = getFormattedDate()
            // storage 및 store에 업로드 작업 certifiedImgs/userEmail에 시간으로 이미지 저장
            // 스토리지에 저장후 url을 다운로드 받아 스토어에 저장
            storage.getReference("certificationImgs/{$gSeq}/").child(fileName).putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
                    Log.d("storageUploadSuccess", "인증사진 스토리지 업로드 성공")
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        val store = Firebase.firestore
                        val url = it.toString()
                        val data = CertifiedImgs( url, userEmail)
                        store.collection("certificationImgs").document()
                            .set(data).addOnSuccessListener {
                                Toast.makeText(this, "사진이 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                                Log.d("storeUploadSuccess", "인증사진 db 업로드 성공")
                                registerCertify(userEmail, fileName)
                            }.addOnFailureListener {
                                Log.d("storeUploadFail", "인증사진 db 업로드 실패")
                            }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "사진 업로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("storageUploadFail", "인증사진 스토리지 업로드 실패")
                }
        } else {
            Toast.makeText(this, "사진을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            Log.d("UriError", "uri null 에러")
        }
    }
    private fun getFormattedDate() : String {
        //format 설정
        val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale.KOREA)
        //TimeZone  설정 (GMT +9)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        //현재시간에 적용
        return format.format(Date().time)
    }

    fun convertToISO8601(dateString: String): String {
        // 원래 문자열의 형식
        val originalFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale.KOREA)
        originalFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        // Date 객체로 변환
        val date = originalFormat.parse(dateString)

        // LocalDateTime으로 변환
        val localDateTime = date.toInstant()
            .atZone(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime()

        // ISO 8601 형식으로 변환
        val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
        return localDateTime.format(isoFormatter)
    }
    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
    }
    private fun requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
    }

    private fun registerCertify(userEmail:String, fileName:String){
        val gSeq: Int = intent.getIntExtra("gSeq", -1)
        val date = convertToISO8601(fileName)
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        apiService.registerCertify(userEmail, gSeq, date).enqueue(object : Callback<Certify> {
            override fun onResponse(call: Call<Certify>, response: Response<Certify>) {
                if (response.isSuccessful) {
                    val certify = response.body()
                    Log.d("CertifyGreeningActivity", "데이터 저장 성공 : ${certify!!.certifySeq}")
                } else {
                    Log.e("CertifyGreeningActivity", "데이터 저장 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Certify>, t: Throwable) {
                Log.e("CertifyGreeningActivity", "서버 통신 중 오류 발생", t)
            }
        })
    }

    private fun loadCertifyData(userEmail:String){
        val gSeq: Int = intent.getIntExtra("gSeq", -1)
        var user: User?
        var userSeq: Int = 0
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        apiService.getUserbyEmail(userEmail).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user = response.body()
                    userSeq = user!!.userSeq
                }else{
                    Log.e("CertifyGreeningActivity", "사용자 조회 실패: ${response.code()}, ${response.errorBody()?.string()}")
                    user = null
                    //실패 처리 로직
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("CertifyGreeningActivity", "서버 통신 중 오류 발생", t)
                user = null
            }
        })
        apiService.getCertifyByUserSeqAndGSeq(userSeq, gSeq).enqueue(object : Callback<List<Certify>> {
            override fun onResponse(call: Call<List<Certify>>, response: Response<List<Certify>>) {
                if (response.isSuccessful) {
                    val certifyList = response.body()
                    Log.d("CertifyGreeningActivity", "인증 정보 불러오기 성공 ")
                } else {
                    Log.e("CertifyGreeningActivity", "인증 정보 불러오기 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Certify>>, t: Throwable) {
                Log.e("CertifyGreeningActivity", "서버 통신 중 오류 발생", t)
            }
        })
    }

    //DB에서 넘어온 CertifyDate를 LocalDateTime으로 변환
    fun convertStringToLocalDateTime(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
    }

    //DB에서 넘어온 CertifyDate를 yyyy-MM-dd형식 문자열로 변환
    fun formatCertifyDate(dateString: String): String {
        // 날짜 문자열을 LocalDateTime으로 파싱
        val localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)

        // yyyy-MM-dd 형식으로 포맷팅
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDateTime.format(formatter)
    }
}



