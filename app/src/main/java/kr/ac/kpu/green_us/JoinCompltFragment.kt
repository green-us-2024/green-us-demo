package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.green_us.databinding.FragmentJoinCompltBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinCompltFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinCompltFragment : Fragment() {
    private var _binding: FragmentJoinCompltBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoinCompltBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 로그인 화면으로 이동
        val joinActivity = activity as JoinActivity
        binding.btnGotoLogin.setOnClickListener { joinActivity.changeFrag(6)}
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}