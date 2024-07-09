package kr.ac.kpu.green_us

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.green_us.databinding.FragmentJoin3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Join3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Join3Fragment : Fragment() {
    private var _binding: FragmentJoin3Binding? = null
    private val binding get() = _binding!!
    private var num =""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoin3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼비활성화
        binding.btnTojoin4.isEnabled = false
        binding.btnTojoin4.alpha = 0.5f
        binding.etValidationNum.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                num = binding.etValidationNum.text.toString()
                if (num.isNotEmpty()){
                    binding.btnTojoin4.isEnabled = true
                    binding.btnTojoin4.alpha = 1f
                }
            }
        })
        //다음
        val joinActivity = activity as JoinActivity
        binding.btnTojoin4.setOnClickListener { joinActivity.changeFrag(4) }
        //이전
        binding.btnEsc.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}