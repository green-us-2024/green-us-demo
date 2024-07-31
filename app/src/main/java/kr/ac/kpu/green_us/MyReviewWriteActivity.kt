package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            val intent = Intent(this, MyReviewActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            //intent.putExtra("3","my_review")
            startActivity(intent)
        }
    }

}