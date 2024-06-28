package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.green_us.databinding.ActivityPointBinding

class PointActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}