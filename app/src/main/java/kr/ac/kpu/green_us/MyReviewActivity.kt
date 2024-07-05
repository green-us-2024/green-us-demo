package kr.ac.kpu.green_us

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kr.ac.kpu.green_us.databinding.ActivityFaqBinding
import kr.ac.kpu.green_us.databinding.ActivityMyReviewBinding

class MyReviewActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMyReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전버튼
        binding.btnEsc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key3","mypage")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 삭제 버튼 클릭 시 삭제확인 창 띄우기
        binding.delete.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            binding.delete.id -> {
                val dlg = DeleteCheckActivity(this)
                dlg.setOnDeleteClickedListener { content ->
                    if (content == 3) {

                    }
                    if (content == 4) {

                    }
                }
                dlg.show()
            }
        }
    }
}