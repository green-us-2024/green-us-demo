package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.databinding.ActivityGreeningDetailBinding

class GreeningDetailActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityGreeningDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreeningDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이전 화면으로
        binding.btnEsc.setOnClickListener {
            this.finish()
        }
    }
}