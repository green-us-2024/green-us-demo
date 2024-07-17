package kr.ac.kpu.green_us

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.replace
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Users
import kr.ac.kpu.green_us.databinding.FragmentHomeBinding
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding
import org.w3c.dom.Text
import java.util.regex.Pattern
import javax.security.auth.callback.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class Join1Fragment : Fragment() {

    private var _binding: FragmentJoin1Binding? = null
    private val binding get() = _binding!!
    private var email = ""
    private var pw = ""
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"


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
        // 이메일 형식 확인
        binding.etEmail.addTextChangedListener(object :TextWatcher{ //입력 감지 시기에 따른
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {} //입력전
            override fun afterTextChanged(p0: Editable?) {
            } // 입력후
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { //입력중
                checkEmailValidation()
            }
        })
        // 비밀번호 형식 확인
        binding.etPw.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val et_email = binding.etEmail.text.toString().trim()
                val et_pw = binding.etPw.text.toString().trim()
                if (et_pw.length >= 6 && et_email.isNotEmpty()){
                    binding.tvPw.isVisible = false
                    binding.etPw.setTextColor(resources.getColor(R.color.black))
                    binding.btnNext.isEnabled = true
                    binding.btnNext.alpha = 1f
                }else{
                    binding.etPw.setTextColor(resources.getColor(R.color.red))
                    binding.tvPw.isVisible = true
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
            // db에 중복된 이메일이 있는지 확인. 중복된 이메일이 아니라면 번들에 데이터를 담아 다음 프래그먼트로 이동

            val bundle = Bundle()
            bundle.putString("email", email)
            bundle.putString("pw", pw)
            val fragmentJoin2 = Join2Fragment()
            fragmentJoin2.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.join_container,fragmentJoin2).addToBackStack(null).commit()

            // 중복된 이메일이라면 이를 알리는 textview를 세팅
//            binding.tvExist.isVisible = true
            }
    }
    fun checkEmailValidation():Boolean{
        val email = binding.etEmail.text.toString().trim() //공백제거
        val patternMatching = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (patternMatching) {
            //이메일 형태가 정상일 경우
            binding.etEmail.setTextColor(resources.getColor(R.color.black))
            binding.tvEmail.isVisible = false
            return true
        } else {
            binding.etEmail.setTextColor(resources.getColor(R.color.red))
            binding.tvEmail.text = "올바른 이메일 형식으로 입력해 주세요"
            binding.tvEmail.isVisible = true
            return false
        }
    }


}