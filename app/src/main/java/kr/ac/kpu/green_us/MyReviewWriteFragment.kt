package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.green_us.databinding.FragmentMyReviewWriteBinding

class MyReviewWriteFragment : Fragment() {
    lateinit var binding: FragmentMyReviewWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyReviewWriteBinding.inflate(inflater, container, false)

        // 리뷰 작성 버튼
        binding.writeReviewBtn.setOnClickListener {
            val intent = Intent(getActivity(), MyReviewActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        return binding.root
    }

}