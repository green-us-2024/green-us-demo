package kr.ac.kpu.green_us

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kpu.green_us.databinding.ActivityMyReviewWriteBinding


// 리뷰 작성 - 리뷰 입력
class MyReviewWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyReviewWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 리뷰 작성 버튼
        binding.writeReviewBtn.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("3","my_review")
            startActivity(intent)
        }
    }
}