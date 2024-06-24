package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val ARG_OBJECT = "object"
class GreenAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            val fragment = MyGreenIngFragment()
            return fragment;
        }
        else if(position == 1){
            val fragment = MyGreenEndFragment()
            return fragment;
        }
        else{
            val fragment = MyGreenOpenFragment()
            return fragment;
        }
    }
}