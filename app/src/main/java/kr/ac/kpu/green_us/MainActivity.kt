package kr.ac.kpu.green_us

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.ac.kpu.green_us.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityMainBinding
    val manager = supportFragmentManager
    private val multiplePermissionsCode = 100



    private val requiredPermissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_MEDIA_IMAGES)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        showInit() //최초 프래그먼트
        initBottomNavi() //아이템 선택시
    }

    //퍼미션 체크 및 권한 요청 함수
    private fun checkPermissions() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }

    private fun initBottomNavi(){
        binding.bottomNavigationView.itemIconTintList = null

        // 이전 버튼을 눌러서 main으로 나왔을 경우
        val value1 = intent.getStringExtra("key3")
        if(value1 == "mypage"){
            MypageFragment().changeFragment()
            binding.bottomNavigationView.selectedItemId = R.id.icon_mypage
        }

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

