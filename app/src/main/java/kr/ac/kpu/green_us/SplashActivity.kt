package kr.ac.kpu.green_us

import kr.ac.kpu.green_us.util.FirebaseAuthUtils
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            },2000)
        }
        // 자동 로그인 구현 : 현재 사용자의 로그인 정보 가져와서 null이면 로그인, not null이면 메인화면으로 이동
//        val user = Firebase.auth.currentUser
//        Log.d("user",user.toString())
//        if (user == null){
//            Handler().postDelayed({
//                val intent = Intent(this, LoginActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                startActivity(intent)
//                finish()
//            },2000)
//        }
//        else{
//            Handler().postDelayed({
////                Log.d("uid",uid)
//                val intent = Intent(this, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                startActivity(intent)
//                finish()
//            },2000)
//        }

}