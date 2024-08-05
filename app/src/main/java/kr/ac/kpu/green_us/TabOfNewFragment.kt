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
import kr.ac.kpu.green_us.adapter.TabNewAdapter
import kr.ac.kpu.green_us.adapter.TabPopAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.databinding.FragmentTabOfNewBinding
import kr.ac.kpu.green_us.databinding.FragmentTabOfPopularBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabOfNewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabOfNewFragment : Fragment() {

    private var _binding: FragmentTabOfNewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabOfNewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewInit()
    }
    fun viewInit(){
        val viewManager = GridLayoutManager(requireContext(),2)
        val viewAdapter = TabNewAdapter()
        binding.recyclerviewNewGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewAdapter.itemClickListener = object : TabNewAdapter.OnItemClickListener{
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
        loadGreeningDate(viewAdapter)
    }

    private fun loadGreeningDate(adapter: TabNewAdapter){
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        apiService.getNewGreening().enqueue(object : Callback<List<Greening>> {
            override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                if (response.isSuccessful) {
                    val greeningList = response.body() ?: emptyList()

                    // 순서대로 20개의 그리닝 선택
//                    val selectedGreeningList = greeningList.take(20)

                    // 데이터를 어댑터에 설정
                    adapter.updateData(greeningList)
                } else {
                    Log.e("TabOfNewFragment", "Greening 데이터 로딩 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Greening>>, t: Throwable) {
                Log.e("TabOfNewFragment", "서버 통신 중 오류 발생", t)
            }
        })
    }


}