package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.green_us.databinding.ActivityFindEmail2Binding
import kr.ac.kpu.green_us.databinding.ActivityFindEmailBinding

class FindEmail2Activity: AppCompatActivity() {

    private lateinit var binding: ActivityFindEmail2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindEmail2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var num_msg = ""

        // 뒤로가기 버튼 클릭 시 로그인 화면으로
        binding.btnEsc.setOnClickListener{
            val intent = Intent(this, FindEmailActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.checkCertification.isEnabled = false
        binding.checkCertification.setAlpha(0.5f)

        // 인증 번호 입력 전까지 인증번호 확인 버튼 비활성화
        binding.phone.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                num_msg = binding.phone.getText().toString()
                if(num_msg.isNotEmpty()) {
                    binding.checkCertification.isEnabled = true
                    binding.checkCertification.setAlpha(1f)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                num_msg = binding.phone.getText().toString()
                if(num_msg.isNotEmpty()) {
                    binding.checkCertification.isEnabled = true
                    binding.checkCertification.setAlpha(1f)
                }
            }
        })

        binding.checkCertification.setOnClickListener {
            val intent = Intent(this, FindEmailCompltActivity::class.java)
            startActivity(intent)
        }
    }
}