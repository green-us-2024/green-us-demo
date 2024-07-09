package kr.ac.kpu.green_us

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding
import kr.ac.kpu.green_us.databinding.FragmentJoin2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class Join2Fragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentJoin2Binding? = null
    private val binding get() = _binding!!
    private var user_name = ""
    private var user_phone = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentJoin2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 휴대폰번호 입력 전까지 다음 버튼 비활성화
        binding.btnTojoin3.isEnabled = false
        binding.btnTojoin3.alpha = 0.5f
        binding.etPhone.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                user_name = binding.etName.text.toString() // 따로 저장 필요
                user_phone = binding.etPhone.text.toString()
                if(user_name.isNotEmpty()&&user_phone.isNotEmpty()){
                    binding.btnTojoin3.isEnabled = true
                    binding.btnTojoin3.alpha = 1f
                }
            }
        })

        //다음 프래그먼트로
        val joinActivity = activity as JoinActivity
        binding.btnTojoin3.setOnClickListener { joinActivity.changeFrag(3) }
        //이전 프래그먼트로
        binding.btnEsc.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}