package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.GreenCardAdapter
import kr.ac.kpu.green_us.adapter.TabPopAdapter
import kr.ac.kpu.green_us.databinding.FragmentTabOfPopularBinding

class TabOfPopularFragment : Fragment() {

    private var _binding: FragmentTabOfPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabOfPopularBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewInit()
    }

    fun viewInit(){
        val viewManager = GridLayoutManager(requireContext(),2)
        val viewAdapter = TabPopAdapter()
        binding.recyclerviewPopularGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewAdapter.itemClickListener = object : TabPopAdapter.OnItemClickListener{
            override fun onItemClick(status:String) {
                val status = "$status"
                if (status == "notIn"){ // 참여 안 한 상태 -> 상세화면으로 넘어감
                    val intent = Intent(requireActivity(),GreeningDetailActivity::class.java)
                    startActivity(intent)
                }
                else if (status == "in"){ // 참여 상태 -> 인증화면으로 넘어감
                    val intent = Intent(requireActivity(),CertifyGreeningActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }
}