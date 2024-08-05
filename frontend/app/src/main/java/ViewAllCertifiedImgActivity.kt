package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import kr.ac.kpu.green_us.adapter.CertifiedImgAdapter
import kr.ac.kpu.green_us.databinding.ActivityCertifyGreeningBinding
import kr.ac.kpu.green_us.databinding.ActivityViewAllCertifiedImgBinding

class ViewAllCertifiedImgActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllCertifiedImgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllCertifiedImgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 세팅
        viewInit()

        // 이전 버큰 클릭
        binding.btnEsc.setOnClickListener {
            this.finish()
        }
    }
    fun viewInit(){
        binding.layoutAllCertifiedImgs.apply {
            layoutManager = GridLayoutManager(this.context,3)
            adapter = CertifiedImgAdapter()
            setHasFixedSize(true)
        }
    }
}