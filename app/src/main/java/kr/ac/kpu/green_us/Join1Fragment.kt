package kr.ac.kpu.green_us

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.replace
import kr.ac.kpu.green_us.databinding.FragmentHomeBinding
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class Join1Fragment : Fragment() {

    private var _binding: FragmentJoin1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoin1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 다음 프래그먼트로 이동
        val joinActivity = activity as JoinActivity
        binding.btnNext.setOnClickListener {
            joinActivity.changeFrag(2) }
        // 이전 프래그먼트로 이동
        binding.btnEsc.setOnClickListener {
            Intent(requireActivity(),LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }.also {
                startActivity(it)
            }
            activity?.finishAffinity()
        }
    }


}