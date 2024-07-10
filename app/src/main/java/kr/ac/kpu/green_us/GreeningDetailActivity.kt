package kr.ac.kpu.green_us

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.kpu.green_us.adapter.GreeningDetailAdapter
import kr.ac.kpu.green_us.databinding.ActivityGreeningDetailBinding
import kr.ac.kpu.green_us.databinding.ActivityMainBinding

class GreeningDetailActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityGreeningDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var status :String
    var cameraOrGallery: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreeningDetailBinding.inflate(layoutInflater)
        if(intent.hasExtra("status")){
//            Log.d("intent value check","status 전달됨")
            status = intent.getStringExtra("status").toString()
//            Log.d("status value is", status)
            if (status == "notIn" ){
                binding.button.text = "그리닝 참여하기"
                binding.button.setOnClickListener{
                    Log.d("btn goes to", "card payments")
                }
            }else if (status == "in"){
                binding.button.text = ("인증하기")
                binding.button.setOnClickListener{
//                    Log.d("btn goes to", "camera")
                    createCamera()
                }
            }
        }
        setContentView(binding.root)
        // 화면 및 데이터 붙이기
        viewManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        viewAdapter = GreeningDetailAdapter()
        recyclerView = binding.recyclerviewGreeningDetail.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        //이전 화면으로
        binding.btnEsc.setOnClickListener {
            this.finish()
        }
    }
    fun createCamera(){
        val dlg = CameraGalleryActivity(this)
        dlg.setOnCameraClickedListener { content ->
            if (content == 1) {
                cameraOrGallery = 1
                val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                    this@GreeningDetailActivity,
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
                    this@GreeningDetailActivity,
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