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
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.databinding.FragmentTabOfPopularBinding
import retrofit2.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabOfPopularFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            override fun onItemClick(status:String, gSeq: Int) {
                val status = "$status"
                val intent = if (status == "notIn"){ // 참여 안 한 상태 -> 상세화면으로 넘어감
                    Intent(requireActivity(),GreeningDetailActivity::class.java)
                }
                else{ // 참여 상태 -> 인증화면으로 넘어감
                    Intent(requireActivity(),CertifyGreeningActivity::class.java)
                }
                intent.putExtra("gSeq", gSeq)
                startActivity(intent)
            }

        }

        loadGreeningDate(viewAdapter)
    }

    private fun loadGreeningDate(adapter: TabPopAdapter){
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        apiService.getPopGreening().enqueue(object : Callback<List<Greening>> {
            override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                if (response.isSuccessful) {
                    val greeningList = response.body() ?: emptyList()

                    // 일단 무작위로 20개의 그리닝 선택
//                    val selectedGreeningList = greeningList.shuffled().take(20)

                    // 데이터를 어댑터에 설정
                    adapter.updateData(greeningList)
                } else {
                    Log.e("TabOfPopularFragment", "Greening 데이터 로딩 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Greening>>, t: Throwable) {
                Log.e("TabOfPopularFragment", "서버 통신 중 오류 발생", t)
            }
        })
    }

}