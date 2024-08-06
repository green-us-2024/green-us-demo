package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.HomeDoAdapter
import kr.ac.kpu.green_us.adapter.HomeDoMoreAdapter
import kr.ac.kpu.green_us.databinding.FragmentMyGreenDoMoreBinding

class MyGreenDoMoreFragment : Fragment() {
    lateinit var binding: FragmentMyGreenDoMoreBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyGreenDoMoreBinding.inflate(inflater, container, false)

        // 진행중인 그리닝
        viewManager = GridLayoutManager(requireContext() , 2)
        viewAdapter = HomeDoMoreAdapter()
        recyclerView = binding.recyclerviewDoGreening.apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        (viewAdapter as HomeDoMoreAdapter).itemClickListener = object : HomeDoMoreAdapter.OnItemClickListener{
            override fun onItemClick(status:String) {
                val status = "$status"
                if (status == "notIn"){
                    // 진행중인지 아닌지에 따라 해당 내용을 intent에 값을 전달 해야 함
                    val intent = Intent(requireActivity(),GreeningDetailActivity::class.java)
                    intent.putExtra("status","notIn")
                    startActivity(intent)
                }
                else if (status == "in"){
                    val intent = Intent(requireActivity(),GreeningDetailActivity::class.java)
                    intent.putExtra("status","in")
                    startActivity(intent)
                }
            }

        }

        return binding.root
    }

}