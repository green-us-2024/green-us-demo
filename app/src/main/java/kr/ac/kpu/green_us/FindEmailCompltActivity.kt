package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.green_us.databinding.ActivityFindEmailCompltBinding

class FindEmailCompltActivity:AppCompatActivity() {
    private lateinit var binding: ActivityFindEmailCompltBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindEmailCompltBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}