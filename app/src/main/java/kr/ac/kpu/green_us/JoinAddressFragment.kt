package kr.ac.kpu.green_us

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.ac.kpu.green_us.databinding.FragmentJoinAddressBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinAddressFragment : Fragment() {

    private var _binding: FragmentJoinAddressBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    private var email = ""
    private var pw = ""
    private var phoneNumber = ""
    private var address = ""
    private var address_detail = ""
    private var name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinAddressBinding.inflate(inflater, container, false)
        // 인스턴스 초기화
        auth = Firebase.auth
        // 이전 프래그먼트로부터 온 bundle 데이터 받기
        email = arguments?.getString("email").toString()
        pw = arguments?.getString("pw").toString()
        phoneNumber = arguments?.getString("phone").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToJoinComplt.isEnabled = false
        binding.btnToJoinComplt.alpha = 0.5f

        binding.btnSearchAddress.setOnClickListener {
            val dialogFragment = AddressDialogFragment()
            dialogFragment.show(parentFragmentManager, "AddressDialog")
        }
        setFragmentResultListener("addressData") { _, bundle ->
            address = bundle.getString("address", "")
            address?.let {
                binding.etAddress.setText(it)
            }
        }
        binding.etAddressDetail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //주소 찾기 api 추가하면 주소까지 입력해야 넘어가는 것으로 바꿀 예정
                //현재는 이름만 입력하면 넘어가도록
                name = binding.etName.text.toString().trim()
                address = binding.etAddress.text.toString()
                address_detail = binding.etAddressDetail.text.toString()
                if (name.isNotEmpty()){
                    binding.btnToJoinComplt.isEnabled = true
                    binding.btnToJoinComplt.alpha = 1f
                }
            }
        })
        //다음버튼 클릭시
        binding.btnToJoinComplt.setOnClickListener {
            // 이메일, 휴대폰 인증, 이름받기 성공하면 파이어베이스에 신규 사용자 생성
            createUser(email,pw)
            val bundle3 = Bundle()
            bundle3.putString("email", email)
            bundle3.putString("pw", pw)
            bundle3.putString("phone", phoneNumber)
            bundle3.putString("name", name)
            bundle3.putString("address", address)
            bundle3.putString("address_detail", address_detail)
            val joinLast = JoinCompltFragment()
            joinLast.arguments = bundle3
            parentFragmentManager.beginTransaction().replace(R.id.join_container, joinLast).addToBackStack(null).commit()
        }
        //이전
        binding.btnEsc.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }
    private fun createUser(userEmail:String,userPw:String){
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 가입 성공
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                } else {
                    // 가입 실패
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}