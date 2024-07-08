package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kr.ac.kpu.green_us.databinding.ActivityFindPwBinding

class FindPwActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var email_msg = ""

        // 뒤로가기 버튼 클릭 시 로그인 화면으로
        binding.btnEsc.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.sendEmail.isEnabled = false
        binding.sendEmail.setAlpha(0.5f)
        binding.email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                email_msg = binding.email.getText().toString()
                if(email_msg.isNotEmpty()) {
                    binding.sendEmail.isEnabled = true
                    binding.sendEmail.setAlpha(1f)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email_msg = binding.email.getText().toString()
                if(email_msg.isNotEmpty()){
                    binding.sendEmail.isEnabled = true
                    binding.sendEmail.setAlpha(1f)
                }
                else{
                    binding.sendEmail.isEnabled = false
                    binding.sendEmail.setAlpha(0.5f)
                }
            }
        })
        
        binding.sendEmail.setOnClickListener { 
            binding.showText.setText("메일이 전송되었습니다")
        }

    }
}