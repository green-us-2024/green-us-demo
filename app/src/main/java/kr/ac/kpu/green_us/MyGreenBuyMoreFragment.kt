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
import kr.ac.kpu.green_us.adapter.HomeBuyMoreAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.databinding.FragmentMyGreenBuyMoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyGreenBuyMoreFragment : Fragment() {
    lateinit var binding: FragmentMyGreenBuyMoreBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyGreenBuyMoreBinding.inflate(inflater, container, false)


        // 진행중인 그리닝
        viewManager = GridLayoutManager(requireContext() , 2)
        viewAdapter = HomeBuyMoreAdapter()
        recyclerView = binding.recyclerviewBuyGreening.apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // 데이터 가져오기
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        val today = LocalDate.now()
        apiService.getBuyGreening().enqueue(object : Callback<List<Greening>> {
            override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                if (response.isSuccessful) {
                    val allBuyGreeningList = response.body() ?: emptyList()
                    val selectedGreeningList = allBuyGreeningList.filter { greening ->
                        try {
                            val startDate = LocalDate.parse(
                                greening.gStartDate,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            )
                            (today.isEqual(startDate) || startDate.isAfter(today))
                        } catch (e: Exception) {
                            false
                        }
                    }
                    (viewAdapter as HomeBuyMoreAdapter).updateData(selectedGreeningList) // 데이터로 어댑터 초기화
                    binding.recyclerviewBuyGreening.adapter = viewAdapter
                } else {
                    Log.e("MyGreenBuyMoreFragment", "BuyGreening 데이터 로딩 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Greening>>, t: Throwable) {
                Log.e("MyGreenBuyMoreFragment", "서버 통신 중 오류 발생", t)
            }
        })

        (viewAdapter as HomeBuyMoreAdapter).itemClickListener = object : HomeBuyMoreAdapter.OnItemClickListener{
            override fun onItemClick(status: String, gSeq: Int) {
                val status = "$status"
                if (status == "notIn"){
                    // 진행중인지 아닌지에 따라 해당 내용을 intent에 값을 전달 해야 함
                    val intent = Intent(requireActivity(),GreeningDetailActivity::class.java)
                    intent.putExtra("status","notIn")
                    intent.putExtra("gSeq", gSeq)
                    startActivity(intent)
                }
                else if (status == "in"){
                    val intent = Intent(requireActivity(),GreeningDetailActivity::class.java)
                    intent.putExtra("status","in")
                    intent.putExtra("gSeq", gSeq)
                    startActivity(intent)
                }
            }

        }



        return binding.root
    }


}