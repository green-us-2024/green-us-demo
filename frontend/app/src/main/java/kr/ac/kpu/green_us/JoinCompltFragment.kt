package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.FragmentJoinCompltBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        address = arguments?.getString("address").toString()
        address_detail = arguments?.getString("address_detail").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 회원 이름 보이기
        binding.tvUserId.text = name
        // 버튼 클릭시
        binding.btnGotoLogin.setOnClickListener {
            // db로 정보 넘겨서 저장
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            val user = User(0,name, pw, email,"$address $address_detail", phoneNumber,null,0)
            apiService.registerUser(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Log.d("JoinCompltFragment", "서버로 데이터 전송 성공")
                        // 로그인 화면으로 이동
                        val intent = Intent(requireActivity(), LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e("JoinCompltFragment", "서버로 데이터 전송 실패")
                        // 실패 처리 로직
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("JoinCompltFragment", "서버 통신 중 오류 발생", t)
                    // 실패 처리 로직
                }
            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}