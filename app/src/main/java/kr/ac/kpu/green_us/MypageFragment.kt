package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.FragmentMypageBinding
import retrofit2.Callback
import retrofit2.Call

// 마이페이지 - 포인트, 개설하기, 내리뷰, 프로필관리, 공지사항, FAQ, 고객센터 화면으로 이동 가능
class MypageFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var userEmail: String
    lateinit var binding: FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(inflater,container,false)

        auth = Firebase.auth
        uid = auth.currentUser?.uid.toString()
        userEmail = auth.currentUser?.email.toString()


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 프로필
        binding.userImg.clipToOutline = true //이미지 둥글게
        uploadImgToProfile(uid) // 현재 로그인한 회원에 맞는 이미지 불러오기
        settingUserName(userEmail) // 현재 로그인한 회원에 맞는 이름 불러오기

        // 개설하기
        binding.goToGreenOpen.setOnClickListener {
            val intent = Intent(getActivity(), SubActivity::class.java)
            intent.putExtra("2","open_green")
            startActivity(intent)
        }

        // 포인트
        binding.pointV.setOnClickListener {
            val intent = Intent(getActivity(), PointActivity::class.java)
            startActivity(intent)
        }

        // 내 리뷰
        binding.myReview.setOnClickListener {
            val intent = Intent(getActivity(), MyReviewActivity::class.java)
            // intent.putExtra("3","my_review")
            startActivity(intent)
        }

        // 프로필 관리
        binding.profileSetting.setOnClickListener {
            val intent = Intent(getActivity(), MyProfileActivity::class.java)
            startActivity(intent)
        }

        // 공지사항
        binding.notice.setOnClickListener {
            val intent = Intent(getActivity(), NoticeActivity::class.java)
            startActivity(intent)
        }

        // FAQ
        binding.faq.setOnClickListener {
            val intent = Intent(getActivity(), FaqActivity::class.java)
            startActivity(intent)
        }

        // 고객센터
        binding.csc.setOnClickListener {

        }

        // 로그아웃
        binding.logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(getActivity(), LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
    private fun uploadImgToProfile(uid:String){
        val storage = Firebase.storage
        val storageRef = storage.getReference("profileImgs/${uid}")
        storageRef.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.userImg)
        }.addOnFailureListener {
            Log.d("profileImg","사진 불러오기 실패")
        }
    }
    private fun settingUserName(userEmail:String){
        val retrofitAPI = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        retrofitAPI.getUserbyEmail(userEmail).enqueue(object :Callback<User>{
            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {
                if(response.isSuccessful){
                    val userName = response.body()?.userName.toString()
                    binding.name.text = userName
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("settingUserName-retrofitApi","불러오기 실패")
            }

        })
    }

}