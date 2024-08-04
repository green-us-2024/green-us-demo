package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.GreenCardAdapter
import kr.ac.kpu.green_us.adapter.MyGreenDegreeAdapter
import kr.ac.kpu.green_us.adapter.MyGreenEndAdapter
import kr.ac.kpu.green_us.adapter.MyGreenIngAdapter
import kr.ac.kpu.green_us.databinding.FragmentMyGreenIngBinding

// 진행중인 그리닝 - 진행중인 그리닝, 전체 그리닝 진척도, 개별 그리닝 진척도 확인 가능
class MyGreenIngFragment : Fragment() {
    lateinit var binding: FragmentMyGreenIngBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager
    var greenExist = true // 데이터에 따라 달라지게

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyGreenIngBinding.inflate(inflater, container, false)

        // 리사이클러뷰 중복 스크롤 막기
        binding.recyclerviewGreenDegree.isNestedScrollingEnabled = false

        // 진행중인 그리닝 존재 여부에 따라
        if(greenExist) { // 존재 할 경우
            // 진행중인 그리닝
            viewManager = GridLayoutManager(requireContext(), 2)
            viewAdapter = MyGreenIngAdapter()
            recyclerView = binding.recyclerviewIngGreening.apply {
                setHasFixedSize(true)
                suppressLayout(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }

            (viewAdapter as MyGreenIngAdapter).itemClickListener = object : MyGreenIngAdapter.OnItemClickListener {
                //onItemClick(position: Int)
                override fun onItemClick() {
                    val intent = Intent(requireActivity(), GreeningDetailSubActivity::class.java)
                    intent.putExtra("ing","ing_state")
                    startActivity(intent)
                }
            }

            // 더보기 버튼 클릭 시
            binding.btnMore.setOnClickListener {
                val intent = Intent(getActivity(), SubActivity::class.java)
                intent.putExtra("0","green_ing_more")
                startActivity(intent)
            }

            // 그리닝 개별 진척도
            viewManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            viewAdapter = MyGreenDegreeAdapter()
            recyclerView = binding.recyclerviewGreenDegree.apply {
                setHasFixedSize(true)
                suppressLayout(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
        else { // 존재하지 않을 경우
            binding.notExistIng.visibility = View.VISIBLE
            binding.recyclerviewIngGreening.visibility = View.GONE
            binding.moreBtn.visibility = View.GONE
            binding.greenDegree.visibility = View.GONE
        }

        return binding.root
    }

}