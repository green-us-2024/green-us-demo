package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.ActivityMyProfileBinding
import retrofit2.Call
import retrofit2.Callback

// 프리필 정보 확인 가능
class MyProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var userEmail: String
    private lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // auth 인스턴스 초기화
        auth = Firebase.auth
        uid = auth.currentUser?.uid.toString()
        userEmail = auth.currentUser?.email.toString()

        // 현재 사용자 uid에 맞는 프로필 이미지 셋팅
        binding.userImg.clipToOutline = true
        uploadImgToProfile(uid)
        
        // 사용자 정보 불러오기
        val retrofitAPI = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        retrofitAPI.getUserbyEmail(userEmail).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {
                if(response.isSuccessful){
                    val userName = response.body()?.userName.toString()
                    val userPhone = response.body()?.userPhone.toString()
                    lateinit var userPhone1:String
                    lateinit var userPhone2:String
                    lateinit var userPhone3:String
                    if(userPhone.length == 11){
                        userPhone1 = userPhone.substring(0,3)
                        userPhone2 = userPhone.substring(3,7)
                        userPhone3 = userPhone.substring(7 .. userPhone.lastIndex)
                    }
                    else if(userPhone.length==10){
                        userPhone1 = userPhone.substring(0,2)
                        userPhone2 = userPhone.substring(2,6)
                        userPhone3 = userPhone.substring(6 .. userPhone.lastIndex)
                    }
                    val userAddr = response.body()?.userAddr.toString()

                    binding.name2.text = userName
                    binding.email2.text = userEmail
                    binding.phone2.text = userPhone1 + "-"+userPhone2 + "-" + userPhone3
                    binding.address2.text = userAddr
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("MyProfileActivity","사용자 정보 조회 실패")
            }

        })

        // 편집 버튼 클릭 시
        binding.edit.setOnClickListener {
            val intent = Intent(this, MyProfileEditActivity::class.java)
            startActivity(intent)
        }

        // 이전버튼
        binding.btnEsc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("key3","mypage")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
    private fun uploadImgToProfile(uid:String){
        val storage = Firebase.storage
        val storageRef = storage.getReference("profileImgs/${uid}")
        storageRef.downloadUrl.addOnSuccessListener {
            Log.d("profileImg",it.toString())
            Glide.with(this).load(it).into(binding.userImg)
        }.addOnFailureListener {
            Log.d("profileImg","사진 불러오기 실패")
        }
    }
}