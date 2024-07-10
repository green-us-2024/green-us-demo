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
import kr.ac.kpu.green_us.adapter.MyGreenIngAdapter
import kr.ac.kpu.green_us.databinding.FragmentMyGreenEndBinding

class MyGreenEndFragment : Fragment() {
    lateinit var binding: FragmentMyGreenEndBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyGreenEndBinding.inflate(inflater)

        // 진행중인 그리닝
        viewManager = GridLayoutManager(requireContext(),2)
        viewAdapter = MyGreenIngAdapter()
        recyclerView = binding.recyclerviewEndGreening.apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // 더보기 버튼 클릭 시
        binding.btnMore.setOnClickListener {
            val intent = Intent(getActivity(), MyGreenEndMoreActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}