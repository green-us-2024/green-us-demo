package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class Join1Fragment : Fragment() {

    private var _binding: FragmentJoin1Binding? = null
    private val binding get() = _binding!!
    private var email = ""
    private var pw = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoin1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // text input전 버튼 비활성화 -> 비밀번호까지 입력하면 활성화
        binding.btnNext.isEnabled = false
        binding.btnNext.alpha = 0.5f
        binding.etPw.addTextChangedListener(object :TextWatcher{ //입력 감지 시기에 따른
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {} //입력전
            override fun afterTextChanged(p0: Editable?) {} // 입력후
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { //입력중
                val et_email = binding.etEmail.text.toString().trim()
                val et_pw = binding.etPw.text.toString().trim()
                if (et_email.isNotEmpty()&&et_pw.isNotEmpty()){
                    binding.btnNext.isEnabled = true
                    binding.btnNext.alpha = 1f
                }
            }
        })

        // 이전 프래그먼트로 이동
        binding.btnEsc.setOnClickListener {
            Intent(requireActivity(),LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT // Task 내에 이미 활성화된 activity 를 다시 활성화 할때, 최상위 top 으로 이동하며 새로 생성하지 않고 재사용
            }.also {
                startActivity(it)
            }
            activity?.finishAffinity()
        }


        // 다음 버튼 클릭시
        binding.btnNext.setOnClickListener {
            email = binding.etEmail.text.toString().trim()
            pw = binding.etPw.text.toString().trim()
            Log.d("Join1Fragment", "$email $pw")

            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            // db에 중복된 이메일이 있는지 확인. 중복된 이메일이 아니라면 번들에 데이터를 담아 다음 프래그먼트로 이동

            apiService.getUserbyEmail(email).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            //이메일이 존재하는 경우
                            Log.e("Join1Fragment", "중복된 이메일입니다")
                            // 중복된 이메일이라면 이를 알리는 textview를 세팅
                            binding.tvExist.isVisible = true
                        } else {
                            //이메일이 존재하지 않는 경우
                            val bundle = Bundle()
                            bundle.putString("email", email)
                            bundle.putString("pw", pw)
                            val fragmentJoin2 = Join2Fragment()
                            fragmentJoin2.arguments = bundle
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.join_container, fragmentJoin2)
                                .addToBackStack(null)
                                .commit()
                        }
                    } else if(response.code() == 404) {
                        //이메일이 존재하지 않는 경우
                        val bundle = Bundle()
                        bundle.putString("email", email)
                        bundle.putString("pw", pw)
                        val fragmentJoin2 = Join2Fragment()
                        fragmentJoin2.arguments = bundle
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.join_container, fragmentJoin2)
                            .addToBackStack(null)
                            .commit()
                    }else{
                            Log.e("Join1Fragment", "사용자 조회 실패: ${response.code()}, ${response.errorBody()?.string()}")
                            //실패 처리 로직
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("Join1Fragment", "서버 통신 중 오류 발생", t)
                    // 실패 처리 로직
                }
            })

        }
    }


}