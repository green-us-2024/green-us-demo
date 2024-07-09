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
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.fragment.app.replace
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.ac.kpu.green_us.databinding.FragmentHomeBinding
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class Join1Fragment : Fragment() {

    private var _binding: FragmentJoin1Binding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var user_email = ""
    private var user_pw = ""

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

        binding.btnNext.isEnabled = false
        binding.btnNext.alpha = 0.5f

        // text input전 버튼 비활성화 -> 비밀번호까지 입력하면 활성화
        binding.etPw.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                user_email = binding.etEmail.text.toString()
                user_pw = binding.etPw.text.toString()
                if (user_email.isNotEmpty()&&user_pw.isNotEmpty()){
                    binding.btnNext.isEnabled = true
                    binding.btnNext.alpha = 1f
                }
            }
        })

        // 이전 프래그먼트로 이동
        binding.btnEsc.setOnClickListener {
            Intent(requireActivity(),LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }.also {
                startActivity(it)
            }
            activity?.finishAffinity()
        }


        // 사용자 추가 성공시 다음 프래그먼트로 이동
        binding.btnNext.setOnClickListener {
            user_email = binding.etEmail.text.toString().trim()
            user_pw = binding.etPw.text.toString().trim()
            createUser(user_email,user_pw)
            }
    }
    private fun createUser(user_email:String,user_pw:String){
        auth.createUserWithEmailAndPassword(user_email, user_pw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 가입 성공
                    Log.d(TAG, "createUserWithEmail:success")
                    val joinActivity = activity as JoinActivity
                    joinActivity.changeFrag(2)
                } else {
                    //가입 실패
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


}