package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.GreeningDetailSubAdapter
import kr.ac.kpu.green_us.databinding.ActivityGreeningDetailSubBinding

// 간단한 그리닝 정보만 담은 상세페이지 보여줌
class GreeningDetailSubActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGreeningDetailSubBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreeningDetailSubBinding.inflate(layoutInflater)

        // 리뷰 작성 버튼 클릭 시
        binding.button.setOnClickListener{
            val intent = Intent(this, MyReviewWriteActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
        // 화면 및 데이터 붙이기
        viewManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        viewAdapter = GreeningDetailSubAdapter()
        recyclerView = binding.recyclerviewGreeningDetailSub.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        //이전 화면으로
        binding.btnEsc.setOnClickListener {
            this.finish()
        }
    }

}