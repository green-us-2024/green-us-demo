package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kr.ac.kpu.green_us.adapter.HeroAdapter
import kr.ac.kpu.green_us.adapter.HomeBuyAdapter
import kr.ac.kpu.green_us.adapter.HomeDoAdapter
import kr.ac.kpu.green_us.databinding.FragmentTabOfHomeBinding

class TabOfHomeFragment : Fragment() {
    lateinit var binding: FragmentTabOfHomeBinding

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



        // 구매형
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = HomeBuyAdapter()
        recyclerView = binding.recyclerviewIngGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        // 활동형
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = HomeDoAdapter()
        recyclerView = binding.recyclerviewIngGreening2.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

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
        }

    }
    //뷰페이저에 들어갈 아이템(이미지)
    private fun getHeroList():ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.hero_img_1,R.drawable.hero_img_2,R.drawable.hero_img_3)
    }

}