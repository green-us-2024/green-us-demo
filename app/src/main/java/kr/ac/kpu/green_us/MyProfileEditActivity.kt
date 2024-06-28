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
import kr.ac.kpu.green_us.databinding.ActivityMyProfileEditBinding


class MyProfileEditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyProfileEditBinding
    lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카메라 버튼 클릭 시 카메라/갤러리 창 띄우기
        binding.camera.setOnClickListener(this)

        // 완료 버튼 클릭 시 activity_my_profile로 이동
        binding.complete.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.camera.id -> {
                val dlg = CameraGalleryActivity(this)
                dlg.setOnCameraClickedListener { content ->
                    if (content == 1) {
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

                    }
                }
                dlg.show()
            }
        }
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            val extras = it.data!!.extras
            bitmap = extras?.get("data") as Bitmap
            binding.userImg.setImageBitmap(bitmap)
        }
    }

}