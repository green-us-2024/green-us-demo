package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.MyGreenOpenAdapter
import kr.ac.kpu.green_us.databinding.FragmentMyGreenOpenBinding

// 개설 그리닝 - 개설한 그리닝 볼 수 있음
class MyGreenOpenFragment : Fragment() {

    lateinit var binding: FragmentMyGreenOpenBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager
    var greenExist = true // 데이터에 따라 달라지게

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyGreenOpenBinding.inflate(inflater)

        // 개설 그리닝 존재 여부에 따라
        if(greenExist) { // 존재 할 경우
            // 개설 그리닝
            viewManager = GridLayoutManager(requireContext(),2)
            viewAdapter = MyGreenOpenAdapter()
            recyclerView = binding.recyclerviewOpenGreening.apply {
                setHasFixedSize(true)
                suppressLayout(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
        else { // 존재하지 않을 경우
            binding.notExistOpen.visibility = View.VISIBLE
            binding.recyclerviewOpenGreening.visibility = View.GONE
        }

        return binding.root
    }
}