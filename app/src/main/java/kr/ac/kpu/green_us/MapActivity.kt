package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import kr.ac.kpu.green_us.adapter.MarketAdapter
import kr.ac.kpu.green_us.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // bottom sheet 어댑팅
        binding.bottomLayout.recyclerviewMarketList.adapter = MarketAdapter()
        binding.bottomLayout.recyclerviewMarketList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        // 바텀 시트 행동에 따른 조절
        val behavior = BottomSheetBehavior.from(binding.bottomLayout.root)
        behavior.apply {
            addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){ // 콜백으로써 상태값에 따른 동작을 조절함
                override fun onStateChanged(bottomSheet: View, state: Int) { // 상태가 변화했을 때
                    when(state){
                        BottomSheetBehavior.STATE_COLLAPSED -> { // 바텀 시트가 접혔을 때
                            //arrow 아이콘 위로 향함
                            binding.bottomLayout.btnArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> { // 바텀 시트가 펼쳐졌을 때
                            // arrow 아이콘 아래로 향함
                            binding.bottomLayout.btnArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                        }
                    }
                }
                override fun onSlide(bottomSheet: View, slide: Float) {
                    //
                }

            })
        }
        // 이전 버튼
        binding.btnEsc.setOnClickListener {
            onBackPressedCallback.handleOnBackPressed()
        }

        // arrow 버튼 클릭시
        binding.bottomLayout.btnArrow.setOnClickListener {
             //바텀시트가 접혀있는 상태라면 확장시킴
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED){behavior.state = BottomSheetBehavior.STATE_EXPANDED }
            // 접혀있지 않다면 (확장상태라면) 접음
            else{behavior.state = BottomSheetBehavior.STATE_COLLAPSED}
        }
    }
    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            isEnabled = false // 콜백 비활성화 -> 뒤로가기가 한 번만 실행되도록
            onBackPressedDispatcher.onBackPressed()
        }
    }
}