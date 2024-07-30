package kr.ac.kpu.green_us

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kr.ac.kpu.green_us.databinding.ActivitySubBinding

// 이전버튼, 타이틀바 중복 화면 프래그먼트를 위한 액티비티
class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전버튼
        binding.btnEsc.setOnClickListener {
            onBackPressedCallback.handleOnBackPressed()
        }

        // 0 진행중 그리닝 전체보기
        val value0 = intent.getStringExtra("0")
        if(value0 == "green_ing_more"){
            binding.subject.setText("진행중인 그리닝")
            MyGreenIngMoreFragment().changeFragment()
        }

        // 1 완료 그리닝 전체보기
        val value1 = intent.getStringExtra("1")
        if(value1 == "green_end_more"){
            binding.subject.setText("완료한 그리닝")
            MyGreenEndMoreFragment().changeFragment()
        }

        // 2 개설하기
        val value2 = intent.getStringExtra("2")
        if(value2 == "open_green"){
            binding.subject.setText("개설하기")
            GreenOpenFragment().changeFragment()
        }

        // 3 내리뷰
/*
        val value3 = intent.getStringExtra("3")
        if(value3 == "my_review"){
            binding.subject.setText("내리뷰")
            MyReviewActivity().changeFragment()
        }
*/
        // 4 프로필관리

        // 5 공지사항

        // 6 faq

        // 7 포인트

        // 8 만보기
        val value8 = intent.getStringExtra("8")
        if(value8 == "pedometer"){
            binding.subject.setText("만보기")
            PedometerFragment().changeFragment()
        }

        // 9 내주변

        // 10 히어로섹션
        val value10 = intent.getStringExtra("10")
        if(value10 == "hero_detail"){
            binding.subject.setText(value10)
            val value10_0 = intent.getIntExtra("10_num",0)
            var bundle = Bundle()
            bundle.putInt("img_num", value10_0)
            val fm = HeroSectionDetailFragment() //프래그먼트 선언
            fm.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
            fm.changeFragment()
        }

        // 11 구매형 그리닝 전체보기
        val value11 = intent.getStringExtra("11")
        if(value11 == "buy_green"){
            binding.subject.setText("구매형 그리닝")
            MyGreenBuyMoreFragment().changeFragment()
        }

        // 12 활동형 그리닝 전체보기
        val value12 = intent.getStringExtra("12")
        if(value12 == "do_green"){
            binding.subject.setText("활동형 그리닝")
            MyGreenDoMoreFragment().changeFragment()
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            isEnabled = false // 콜백 비활성화 -> 뒤로가기가 한 번만 실행되도록
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun Fragment.changeFragment(){
        manager.beginTransaction().replace(R.id.sub_frame,this).commit()
    }
}