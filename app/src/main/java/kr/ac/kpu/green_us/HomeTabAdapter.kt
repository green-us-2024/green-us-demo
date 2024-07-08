package kr.ac.kpu.green_us

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val ARG_OBJECT = "object"
class HomeTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            val fragment = TabOfHomeFragment()
            return fragment;
        }
        else if(position == 1){
            val fragment = TabOfPopularFragment()
            return fragment;
        }
        else{
            val fragment = TabOfNewFragment()
            return fragment;
        }
    }
}