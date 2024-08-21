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
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.databinding.ActivityMyProfileEditBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


// 프로필 정보 수정 - 주소만 수정 가능
class MyProfileEditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileEditBinding
    lateinit var bitmap: Bitmap
    lateinit var uri: Uri
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    var cameraOrGallery: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // auth 인스턴스 초기화
        auth = Firebase.auth
        uid = auth.currentUser?.uid.toString()
        // 프로필 이미지 둥글게
        binding.userImg.clipToOutline = true
        uploadImgToProfile(uid)


        // 이전버튼
        binding.btnEsc.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 카메라 버튼 클릭 시 카메라/갤러리 창 띄우기
        binding.camera.setOnClickListener(this)

        // 완료 버튼 클릭 시 activity_my_profile로 이동
        binding.complete.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
//    private fun cropImage(uri: Uri?) {
//        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)  // 크롭 위한 가이드 열어서 크롭할 이미지 받아오기
//            .setCropShape(CropImageView.CropShape.RECTANGLE)            // 사각형으로 자르기
//            .start(this@MainActivity)
//        // 프레그먼트에서 사용할 땐 .start(activity as 프레그먼트의 부모 Activity, this@형재 프레그먼트 이름)
//    }


    // 카메라/갤러리 창 띄우기
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onClick(view: View?) {
        when (view?.id) {
            binding.camera.id -> {
                val dlg = CameraGalleryActivity(this)
                dlg.setOnCameraClickedListener { content ->
                    if (content == 1) {
                        cameraOrGallery = 1
                        val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                            this@MyProfileEditActivity,
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
                            this@MyProfileEditActivity,
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
                            intent.putExtra("crop",true)
                            intent.type = "image/*"
                            activityResult.launch(intent)
                        }
                    }
                }
                dlg.show()
            }
        }
    }

    // 카메라/ 갤러리 이미지 이미지뷰에 띄우기
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            if(cameraOrGallery == 1){
                val extras = it.data!!.extras
                bitmap = extras!!.get("data") as Bitmap
                uri = getImageUri(this@MyProfileEditActivity, bitmap)
//                binding.userImg.setImageBitmap(bitmap)
//                binding.userImg.clipToOutline = true
                imageUpload(uri,uid) //이미지 업로드 함수

            }
            if(cameraOrGallery == 2){
                uri = it.data?.data!!
//                Glide.with(this)
//                    .load(uri)
//                    .apply(RequestOptions().circleCrop())
//                    .into(binding.userImg)
//                binding.userImg.clipToOutline = true
                imageUpload(uri,uid)
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

    private fun imageUpload(uri: Uri,uid:String) {
        // storage 인스턴스 생성
        val storage = Firebase.storage
        // storage 참조
        val storageRef = storage.getReference("profileImgs/")
        // storage에 저장할 파일명 선언
        val fileName = uid
        val mountainsRef = storageRef.child(fileName)

        val uploadTask = mountainsRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
            Toast.makeText(this, "사진 업로드 성공", Toast.LENGTH_SHORT).show();
            uploadImgToProfile(uid)
        }.addOnFailureListener {
            // 파일 업로드 실패
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_SHORT).show();
        }
    }
    private fun uploadImgToProfile(uid:String){
        val storage = Firebase.storage
        val storageRef = storage.getReference("profileImgs/${uid}")
        storageRef.downloadUrl.addOnSuccessListener {
            Log.d("profileImg",it.toString())
            Glide.with(this).load(it).into(binding.userImg)
        }.addOnFailureListener {
            Log.d("profileImg","사진 불러오기 실패")
        }
    }

}