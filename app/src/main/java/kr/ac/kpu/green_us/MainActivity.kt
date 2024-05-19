package kr.ac.kpu.green_us

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.ac.kpu.green_us.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityMainBinding
    val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showInit() //최초 프래그먼트
        initBottomNavi() //아이템 선택시
    }
    private fun initBottomNavi(){
        binding.bottomNavigationView.itemIconTintList = null// 없으면 아이템 컬러 색 적용안됨

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.icon_home -> {
                    HomeFragment().changeFragment()
                }
                R.id.icon_mygreen -> {
                    MyGreenFragment().changeFragment()
                }
                R.id.icon_mypage -> {
                    MypageFragment().changeFragment()
                }
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigationView.setOnItemReselectedListener {  } // 재클릭 재생성 방지
        Log.e("MainActivity","bottomNavi complt")
    }
    private fun Fragment.changeFragment(){
        manager.beginTransaction().replace(R.id.main_frame,this).commit()
    }
    private fun showInit(){
        val transaction = manager.beginTransaction()
            .add(R.id.main_frame,HomeFragment())
        transaction.commit()

    }
}

