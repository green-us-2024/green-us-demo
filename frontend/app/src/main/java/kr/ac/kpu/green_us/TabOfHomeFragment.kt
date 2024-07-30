package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kr.ac.kpu.green_us.adapter.HeroAdapter
import kr.ac.kpu.green_us.adapter.HomeBuyAdapter
import kr.ac.kpu.green_us.adapter.HomeDoAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.databinding.FragmentTabOfHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class TabOfHomeFragment : Fragment() {
    lateinit var binding: FragmentTabOfHomeBinding
    private lateinit var homeDoAdapter: HomeDoAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var total_banner_num = getHeroList().size // 배너 전체 개수
    private var current_banner_position = Int.MAX_VALUE/2  // 무한스크롤처럼 좌우로 스크롤 가능하도록 중간지점으로 세팅함

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabOfHomeBinding.inflate(inflater,container,false)

        // hero banner
        binding.heroSection.adapter = HeroAdapter(getHeroList())
        binding.heroSection.orientation = ViewPager2.ORIENTATION_HORIZONTAL //가로 스크롤
        binding.heroSection.currentItem =  current_banner_position
        binding.totalBannerNum.text = total_banner_num.toString()// 전체 배너(이미지) 개수 세팅
        val showCurrentNum = (current_banner_position%total_banner_num)+1
        binding.currentBannerNum.text = showCurrentNum.toString() // 현재 이미지 순서 세팅

        //구매형 및 활동형 RecyclerView 설정
        setupRecyclerViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 이미지 위치에 따라 현재 위치 숫자를 변경함

        binding.heroSection.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val posiText = "${(position%total_banner_num)+1}"
                binding.currentBannerNum.text = posiText
            }
        })

        // 빠른 접근을 위한 버튼들 클릭 구현
        binding.btnManbo.setOnClickListener {
            val intent = Intent(requireActivity(), PedometerActivity::class.java)
            startActivity(intent)
        }
        binding.btnOpen.setOnClickListener {
            val intent = Intent(requireActivity(), GreenOpenActivity::class.java)
            startActivity(intent)
        }

        //데이터 로딩
        loadGreeningData()

    }

    private fun setupRecyclerViews(){
        // 구매형 RecyclerView 설정
        val viewManager1 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val viewAdapter1 = HomeBuyAdapter()
        binding.recyclerviewIngGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager1
            adapter = viewAdapter1
        }

        // 활동형 RecyclerView 설정
        val viewManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeDoAdapter = HomeDoAdapter(emptyList()) // 빈 리스트로 초기화
        binding.recyclerviewIngGreening2.apply {
            setHasFixedSize(true)
            layoutManager = viewManager2
            adapter = homeDoAdapter
        }
    }

    private fun loadGreeningData() {
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        apiService.getGreening().enqueue(object : Callback<List<Greening>> {
            override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                if (response.isSuccessful) {
                    val allGreeningList = response.body() ?: emptyList()
                    val selectedGreeningList = allGreeningList.shuffled().take(4) //무작위로 4개 선택
                    homeDoAdapter.updateData(selectedGreeningList) // 데이터로 어댑터 초기화
                    binding.recyclerviewIngGreening2.adapter = homeDoAdapter
                } else {
                    Log.e("TabOfHomeFragment", "Greening 데이터 로딩 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Greening>>, t: Throwable) {
                Log.e("TabOfHomeFragment", "서버 통신 중 오류 발생", t)
            }
        })
    }

    //뷰페이저에 들어갈 아이템(이미지)
    private fun getHeroList():ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.hero_img_1,R.drawable.hero_img_2,R.drawable.hero_img_3)
    }


}
