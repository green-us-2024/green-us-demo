package kr.ac.kpu.green_us

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.MyReviewAdapter
import kr.ac.kpu.green_us.databinding.ActivityMyReviewBinding

class MyReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReviewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전버튼
        binding.btnEsc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key3","mypage")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        viewAdapter = MyReviewAdapter()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_review).apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }

}