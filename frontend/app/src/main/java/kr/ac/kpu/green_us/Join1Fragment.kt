package kr.ac.kpu.green_us

import android.content.ContentValues.TAG
import android.content.Context
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class Join1Fragment : Fragment() {

    private var _binding: FragmentJoin1Binding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var email = ""
    private var pw = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoin1Binding.inflate(inflater, container, false)

        // 인스턴스 초기화
        auth = Firebase.auth
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
                val et_email = binding.etEmail.text.toString()
                val et_pw = binding.etPw.text.toString()
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
//            Log.d("email",email)
//            Log.d("pw",pw)
            createUser(email,pw) //파이어베이스에 신규 사용자 추가 함수

            }
    }
    private fun createUser(userEmail:String,userPw:String){
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 가입 성공
                    Log.d(TAG, "createUserWithEmail:success")
                    // 이메일,비번 데이터 bundle에 담기
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    bundle.putString("pw", pw)

                    val fragmentJoin2 = Join2Fragment()
                    fragmentJoin2.arguments = bundle
                    parentFragmentManager.beginTransaction().replace(R.id.join_container,fragmentJoin2).addToBackStack(null).commit()
                } else {
                    //존재하는 이메일일 경우
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    binding.tvExist.isVisible = true
                }
            }
    }


}