package kr.ac.kpu.green_us

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
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

        binding.etAddressDetail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = binding.etName.text.toString()
                address = binding.etAddress.text.toString()
                address_detail = binding.etAddressDetail.text.toString()
                if (name.isNotEmpty()) {
                    binding.btnToJoinComplt.isEnabled = true
                    binding.btnToJoinComplt.alpha = 1f
                }
            }
        })

        binding.btnToJoinComplt.setOnClickListener {
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

        binding.btnEsc.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}