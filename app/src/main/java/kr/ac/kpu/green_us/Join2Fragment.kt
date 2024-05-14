package kr.ac.kpu.green_us

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
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Join2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Join2Fragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentJoin2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentJoin2Binding.inflate(inflater,container,false)
        return binding.root
        /*
               binding2.btnNext.setOnClickListener {
                   activity?.finishAffinity()
                   startActivity(Intent(context,JoinCompltActivity()::class.java))
               }*/

        }



}