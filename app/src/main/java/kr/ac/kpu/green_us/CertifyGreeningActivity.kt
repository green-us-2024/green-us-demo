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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.adapter.CertifiedRepresentAdapter
import kr.ac.kpu.green_us.adapter.StampAdapter
import kr.ac.kpu.green_us.data.CertifiedImgs
import kr.ac.kpu.green_us.databinding.ActivityCertifyGreeningBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
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



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCertifyGreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // auth 인스턴스 초기화
        auth = Firebase.auth

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
        dlg.setOnCameraClickedListener { content ->
            if (content == 1) {
                cameraOrGallery = 1
                val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                    this@CertifyGreeningActivity,
                    android.Manifest.permission.CAMERA
                )
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
        dlg.show()
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
            storage.getReference("certificationImgs/").child(fileName).putFile(uri)
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
}



