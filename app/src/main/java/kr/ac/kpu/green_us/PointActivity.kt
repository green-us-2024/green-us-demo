package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.PointAdapter
import kr.ac.kpu.green_us.databinding.ActivityPointBinding

// 포인트 - 포인트 잔액, 포인트 획득 내역 달마다 확인 가능
class PointActivity: AppCompatActivity() {
    lateinit var binding: ActivityPointBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전버튼
        binding.btnEsc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key3","mypage")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        viewAdapter = PointAdapter()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_point).apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

        // 출금하기 버튼
        binding.pointWithdraw.setOnClickListener {
            val intent = Intent(this, PointWithdrawActivity::class.java)
            startActivity(intent)
        }
    }
}