package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.MyGreenIngAdapter
import kr.ac.kpu.green_us.adapter.MyGreenIngMoreAdapter
import kr.ac.kpu.green_us.databinding.FragmentMyGreenIngMoreBinding

class MyGreenIngMoreFragment : Fragment() {
    lateinit var binding: FragmentMyGreenIngMoreBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyGreenIngMoreBinding.inflate(inflater, container, false)

        // 진행중인 그리닝
        viewManager = GridLayoutManager(requireContext() , 2)
        viewAdapter = MyGreenIngMoreAdapter()
        recyclerView = binding.recyclerviewIngMoreGreening.apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        (viewAdapter as MyGreenIngMoreAdapter).itemClickListener = object : MyGreenIngMoreAdapter.OnItemClickListener {
            //onItemClick(position: Int)
            override fun onItemClick() {
                val intent = Intent(requireActivity(), GreeningDetailSubActivity::class.java)
                intent.putExtra("ing","ing_state")
                startActivity(intent)
            }
        }

        return binding.root
    }

}