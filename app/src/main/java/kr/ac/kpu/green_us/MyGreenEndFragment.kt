package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.MyGreenEndAdapter
import kr.ac.kpu.green_us.databinding.FragmentMyGreenEndBinding

// 완료한 그리닝 - 완료한 그리닝, 완료한 그리닝 수, 총 획득 포인트 확인 가능
class MyGreenEndFragment : Fragment() {
    lateinit var binding: FragmentMyGreenEndBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager
    var greenExist = true // 데이터에 따라 달라지게

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyGreenEndBinding.inflate(inflater, container, false)

        // 리사이클러뷰 중복 스크롤 막기
        binding.recyclerviewEndGreening.isNestedScrollingEnabled = false

        // 완료 그리닝 존재 여부에 따라
        if(greenExist){ // 존재 할 경우
            // 진행 완료한 그리닝
            viewManager = GridLayoutManager(requireContext(),2)
            viewAdapter = MyGreenEndAdapter()
            recyclerView = binding.recyclerviewEndGreening.apply {
                setHasFixedSize(true)
                suppressLayout(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }

            (viewAdapter as MyGreenEndAdapter).itemClickListener = object : MyGreenEndAdapter.OnItemClickListener {
                //onItemClick(position: Int)
                override fun onItemClick() {
                    val intent = Intent(requireActivity(), GreeningDetailSubActivity::class.java)
                    startActivity(intent)
                }
            }

            // 더보기 버튼 클릭 시
            binding.btnMore.setOnClickListener {
                val intent = Intent(getActivity(), SubActivity::class.java)
                intent.putExtra("1","green_end_more")
                startActivity(intent)
            }
        }
        else{ // 존재하지 않을 경우
            binding.notExistEnd.visibility = View.VISIBLE
            binding.recyclerviewEndGreening.visibility = View.GONE
            binding.moreBtn.visibility = View.GONE
            binding.endGreeningStatistics.visibility = View.GONE
            binding.endGreenCnt.visibility = View.GONE
            binding.totalPoint.visibility = View.GONE
        }

        return binding.root
    }
}