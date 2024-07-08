package kr.ac.kpu.green_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.ac.kpu.green_us.adapter.GreenAdapter

class MyGreenFragment : Fragment() {

    private lateinit var greenAdapter: GreenAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_green, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        greenAdapter = GreenAdapter(this)
        viewPager = view.findViewById(R.id.green_view_page)
        viewPager.setUserInputEnabled(false);
        viewPager.adapter = greenAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.green_tap)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if(position == 0){
                tab.text = "진행중"
            }
            else if(position == 1){
                tab.text = "진행완료"
            }
            else{
                tab.text = "개설"
            }
        }.attach()
    }

}
