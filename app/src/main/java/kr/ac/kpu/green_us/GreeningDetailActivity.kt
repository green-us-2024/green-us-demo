package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.GreeningDetailAdapter
import kr.ac.kpu.green_us.databinding.ActivityGreeningDetailBinding
import kr.ac.kpu.green_us.databinding.ActivityMainBinding

class GreeningDetailActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityGreeningDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreeningDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        viewAdapter = GreeningDetailAdapter()
        recyclerView = binding.recyclerviewGreeningDetail.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        binding.btnEsc.setOnClickListener {
            this.finish()
        }
    }

}