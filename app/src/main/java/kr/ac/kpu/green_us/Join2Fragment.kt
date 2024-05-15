package kr.ac.kpu.green_us

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    private var joinActivity : JoinActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentJoin2Binding.inflate(inflater,container,false)
        val joinActivity = activity as JoinActivity
        binding.btnNext2.setOnClickListener { joinActivity.changeFrag(3) }
        return binding.root

        }



}