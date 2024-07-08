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

        // 뒤로가기 버튼 클릭 시 로그인 화면으로
        binding.btnEsc.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.sendCertification.isEnabled = false
        binding.sendCertification.setAlpha(0.5f)

        // 휴대전화 입력 전까지 인증 번호 발송 버튼 비활성화
        binding.phone.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                phone_msg = binding.phone.getText().toString()
                if(phone_msg.isNotEmpty()) {
                    binding.sendCertification.isEnabled = true
                    binding.sendCertification.setAlpha(1f)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone_msg = binding.phone.getText().toString()
                if(phone_msg.isNotEmpty()) {
                    binding.sendCertification.isEnabled = true
                    binding.sendCertification.setAlpha(1f)
                }
            }
        })

        binding.sendCertification.setOnClickListener {
            val intent = Intent(this, FindEmail2Activity::class.java)
            startActivity(intent)
        }
    }
}