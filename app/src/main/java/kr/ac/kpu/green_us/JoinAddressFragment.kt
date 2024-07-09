package kr.ac.kpu.green_us

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val binding get() = _binding!!
    private var address = ""
    private var address_detail = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoinAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToJoinComplt.isEnabled = false
        binding.btnToJoinComplt.alpha = 0.5f
        binding.etAddressDetail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                address = binding.etAddress.text.toString()
                address_detail = binding.etAddressDetail.text.toString()
                if (address.isNotEmpty()&&address_detail.isNotEmpty()){
                    binding.btnToJoinComplt.isEnabled = true
                    binding.btnToJoinComplt.alpha = 1f
                }
            }
        })
        //다음
        val joinActivity = activity as JoinActivity
        binding.btnToJoinComplt.setOnClickListener { joinActivity.changeFrag(5) }
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