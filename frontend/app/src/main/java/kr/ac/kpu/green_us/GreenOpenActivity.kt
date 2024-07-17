package kr.ac.kpu.green_us

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.ac.kpu.green_us.databinding.ActivityGreenOpenBinding
import java.util.*
import kotlin.collections.ArrayList

// 그리닝 개설 - 그리닝 정보 입력
class GreenOpenActivity : AppCompatActivity() {
    lateinit var binding: ActivityGreenOpenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreenOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 날짜 입력 editText 달력으로만 받게 하기 위해 비활성화
        binding.startDateEt.setClickable(false);
        binding.startDateEt.setFocusable(false);

        // 사진명 입력 editText 정보 가져와서 받게 하기 위해 비활성화
        binding.uploadPictureEt.setClickable(false);
        binding.uploadPictureEt.setFocusable(false);

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
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key2_3", "open")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 달력 이미지 클릭
        binding.btnDate.setOnClickListener {
            showDatePicker()
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
            var connect = "-"
            var year = y.toString()
            var month = (m + 1).toString()
            var date = d.toString()
            var start_date = year + connect + month + connect + date
            binding.startDateEt.setText(start_date)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000;
        dpd.show()
    }

    // 이미지 저장
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val uri = it.data!!.data
        binding.uploadPictureEt.setText(uri?.getLastPathSegment())
    }
    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            isEnabled = false // 콜백 비활성화 -> 뒤로가기가 한 번만 실행되도록
            onBackPressedDispatcher.onBackPressed()
        }
    }

}