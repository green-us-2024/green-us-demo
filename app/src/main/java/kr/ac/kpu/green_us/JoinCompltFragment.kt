package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.green_us.databinding.FragmentJoinCompltBinding

class JoinCompltFragment : Fragment() {
    private var email = ""
    private var pw = ""
    private var phoneNumber = ""
    private var address = ""
    private var address_detail = ""
    private var name = ""
    private var _binding: FragmentJoinCompltBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoinCompltBinding.inflate(inflater, container, false)
        // 이전 프래그먼트로부터 온 bundle 데이터 받기
        email = arguments?.getString("email").toString()
        pw = arguments?.getString("pw").toString()
        phoneNumber = arguments?.getString("phone").toString()
        name = arguments?.getString("name").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 회원 이름 보이기
        binding.tvUserId.text = name
        // 버튼 클릭시
        binding.btnGotoLogin.setOnClickListener {
            // db에 정보 저장?? 하고 ??

            // 로그인 화면으로 이동
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}