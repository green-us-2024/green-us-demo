package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kr.ac.kpu.green_us.adapter.*
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
    private lateinit var homeBuyAdapter: HomeBuyAdapter

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
        binding.heroSection.adapter = HeroAdapter(getHeroList(),requireActivity())
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
        binding.heroSection.apply {
            registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val posiText = "${(position%total_banner_num)+1}"
                    binding.currentBannerNum.text = posiText
                }
            })
            binding.heroSection.setOnClickListener{

            }
        }
        // 빠른 접근을 위한 버튼들 클릭 구현
        // 만보기 버튼
        binding.btnManbo.setOnClickListener {
            val intent = Intent(requireActivity(), SubActivity::class.java)
            intent.putExtra("8","pedometer")
            startActivity(intent)
        }
        // 개설하기 버튼
        binding.btnOpen.setOnClickListener {
            val intent = Intent(getActivity(), SubActivity::class.java)
            intent.putExtra("2","open_green")
            startActivity(intent)
        }
        // 내주변 버튼
        binding.btnNearMarket.setOnClickListener {
            val intent = Intent(requireActivity(), MapActivity::class.java)
            startActivity(intent)
        }
        // 히어로 섹션 전체보기
        binding.adPaging.setOnClickListener {
            val intent = Intent(requireActivity(), SubActivity::class.java)
            intent.putExtra("13","hero_list")
            startActivity(intent)
        }
        //데이터 로딩
        loadGreeningData()

    }

    private fun setupRecyclerViews(){
        // 구매형 RecyclerView 설정
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeBuyAdapter = HomeBuyAdapter(emptyList())
        recyclerView=binding.recyclerviewIngGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = homeBuyAdapter
        }

        (viewAdapter as HomeBuyAdapter).itemClickListener = object : HomeBuyAdapter.OnItemClickListener{
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

        // 더보기 버튼 클릭 시
        binding.imageView3.setOnClickListener {
            val intent = Intent(getActivity(), SubActivity::class.java)
            intent.putExtra("11","buy_green")
            startActivity(intent)
        }

        // 활동형 RecyclerView 설정
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeDoAdapter = HomeDoAdapter(emptyList()) // 빈 리스트로 초기화
        recyclerView=binding.recyclerviewIngGreening2.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = homeDoAdapter
        }

        // 더보기 버튼 클릭 시
        binding.imageView5.setOnClickListener {
            val intent = Intent(getActivity(), SubActivity::class.java)
            intent.putExtra("12","do_green")
            startActivity(intent)
        }

    }

    private fun loadGreeningData() {
        val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        apiService.getDoGreening().enqueue(object : Callback<List<Greening>> {
            override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                if (response.isSuccessful) {
                    val allDoGreeningList = response.body() ?: emptyList()
                    val selectedGreeningList = allDoGreeningList.shuffled().take(4) //무작위로 4개 선택
                    homeDoAdapter.updateData(selectedGreeningList) // 데이터로 어댑터 초기화
                    binding.recyclerviewIngGreening2.adapter = homeDoAdapter
                } else {
                    Log.e("TabOfHomeFragment", "DoGreening 데이터 로딩 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Greening>>, t: Throwable) {
                Log.e("TabOfHomeFragment", "서버 통신 중 오류 발생", t)
            }
        })


        apiService.getBuyGreening().enqueue(object : Callback<List<Greening>> {
            override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                if (response.isSuccessful) {
                    val allBuyGreeningList = response.body() ?: emptyList()
                    val selectedGreeningList = allBuyGreeningList.shuffled().take(4) //무작위로 4개 선택
                    homeBuyAdapter.updateData(selectedGreeningList) // 데이터로 어댑터 초기화
                    binding.recyclerviewIngGreening.adapter = homeBuyAdapter
                } else {
                    Log.e("TabOfHomeFragment", "BuyGreening 데이터 로딩 실패: ${response.code()}")
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
