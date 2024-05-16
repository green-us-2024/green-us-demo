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
import kr.ac.kpu.green_us.databinding.ActivityFindEmailBinding

class FindEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var phone_msg = ""
        var num_msg = ""

        // 뒤로가기 버튼 클릭 시 로그인 화면으로
        binding.btnEsc.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.phoneCk.isEnabled = false
        binding.numCk.isEnabled = false
        binding.goToLogin.isEnabled = false
        binding.phoneCk.setAlpha(0.5f)
        binding.numCk.setAlpha(0.5f)
        binding.goToLogin.setAlpha(0.5f)

        // 휴대전화 입력 전까지 인증 번호 발송 버튼 비활성화
        binding.phone.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                phone_msg = binding.phone.getText().toString()
                if(phone_msg.isNotEmpty()) {
                    binding.phoneCk.isEnabled = true
                    binding.phoneCk.setAlpha(1f)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone_msg = binding.phone.getText().toString()
                if(phone_msg.isNotEmpty()) {
                    binding.phoneCk.isEnabled = true
                    binding.phoneCk.setAlpha(1f)
                }
                else{
                    binding.phoneCk.isEnabled = false
                    binding.phoneCk.setAlpha(0.5f)
                }
            }
        })

        var email_show1 = "가입하신 이메일은 "
        var email_show2 = "입니다"
        var user_email = "asdf@naver.com" // 사용자 이메일

        // 인증번호 입력 전까지 인증 확인 버튼 비활성화
        binding.num.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                num_msg = binding.num.getText().toString()
                if(num_msg.isNotEmpty()) {
                    binding.numCk.isEnabled = true
                    binding.goToLogin.isEnabled = true
                    binding.numCk.setAlpha(1f)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                num_msg = binding.num.getText().toString()
                if(num_msg.isNotEmpty()) {
                    binding.numCk.isEnabled = true
                    binding.goToLogin.isEnabled = true
                    binding.numCk.setAlpha(1f)
                }
                else{
                    binding.numCk.isEnabled = true
                    binding.goToLogin.isEnabled = true
                    binding.numCk.setAlpha(1f)
                }
            }
        })

        binding.numCk.setOnClickListener {
            binding.showEmail.setText(email_show1 + user_email + email_show2)
            binding.goToLogin.isEnabled = true
            binding.goToLogin.setAlpha(1f)
        }

        binding.goToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}