package kr.ac.kpu.green_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MyGreenFragment : Fragment() {

    lateinit var myFragment: View
    lateinit var viewPagers: ViewPager
    lateinit var tabLayouts: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFragment = inflater.inflate(R.layout.fragment_my_green, container, false)
        return myFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpViewPager()
        tabLayouts.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
    }


    private fun setUpViewPager() {
        viewPagers = myFragment.findViewById(R.id.green_view_page)
        tabLayouts = myFragment.findViewById(R.id.green_tap)

        val adapter = GreenAdapter(fragmentManager!!)
        adapter.addFragment(MyGreenIngFragment(), "진행중")
        adapter.addFragment(MyGreenEndFragment(), "진행완료")
        adapter.addFragment(MyGreenOpenFragment(), "개설")

        viewPagers.setAdapter(adapter)

        viewPagers.adapter = adapter
        tabLayouts!!.setupWithViewPager(viewPagers)
    }
}
