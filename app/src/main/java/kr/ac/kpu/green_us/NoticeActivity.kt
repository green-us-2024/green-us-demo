package kr.ac.kpu.green_us

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.green_us.databinding.ActivityNoticeBinding

class NoticeActivity:AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDown.setOnClickListener {
            if(binding.constraintLayout3.visibility == View.VISIBLE){
                binding.btnDown.setImageResource(R.drawable.btn_down)
                binding.constraintLayout3.visibility = View.INVISIBLE
            } else {
                binding.btnDown.setImageResource(R.drawable.btn_up)
                binding.constraintLayout3.visibility = View.VISIBLE
            }
        }

    }
}