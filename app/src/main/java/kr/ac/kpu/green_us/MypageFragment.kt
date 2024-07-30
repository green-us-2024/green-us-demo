package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.ac.kpu.green_us.databinding.FragmentMypageBinding

// 마이페이지 - 포인트, 개설하기, 내리뷰, 프로필관리, 공지사항, FAQ, 고객센터 화면으로 이동 가능
class MypageFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater,container,false)

        auth = Firebase.auth

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

        // Inflate the layout for this fragment
        return binding.root
    }

}