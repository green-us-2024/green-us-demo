package kr.ac.kpu.green_us

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.StampAdapter
import kr.ac.kpu.green_us.databinding.ActivityCertifyGreeningBinding

class CertifyGreeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCertifyGreeningBinding
    var cameraOrGallery: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCertifyGreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
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
                // ? 이미지 촬영하면 어떻게 해야 할지....
//                bitmap = extras?.get("data") as Bitmap
//                binding.userImg.setImageBitmap(bitmap)
//                binding.userImg.clipToOutline = true
            }
            if(cameraOrGallery == 2){
                val uri = it.data!!.data
//                Glide.with(this)
//                    .load(uri)
//                    .into(binding.userImg)
//                binding.userImg.clipToOutline = true
            }
        }
    }

}


