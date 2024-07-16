package kr.ac.kpu.green_us

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.ac.kpu.green_us.databinding.ActivityMyProfileEditBinding
import java.io.File

// 프로필 정보 수정 - 주소만 수정 가능
class MyProfileEditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileEditBinding
    lateinit var bitmap: Bitmap
    var cameraOrGallery: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    // 카메라/갤러리 창 띄우기
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
                bitmap = extras?.get("data") as Bitmap
                binding.userImg.setImageBitmap(bitmap)
                binding.userImg.clipToOutline = true
            }
            if(cameraOrGallery == 2){
                val uri = it.data!!.data
                Glide.with(this)
                    .load(uri)
                    .apply(RequestOptions().circleCrop())
                    .into(binding.userImg)
                binding.userImg.clipToOutline = true
            }
        }
    }

}