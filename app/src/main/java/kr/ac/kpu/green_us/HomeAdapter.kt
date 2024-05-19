package kr.ac.kpu.green_us

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeAdapter(fm: FragmentManager,lc: Lifecycle) : FragmentStateAdapter(fm,lc)  {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TabHomeFragment()
            1 -> TabPopFragment()
            2-> TabNewFragment()
            else -> TabHomeFragment()
        }
    }
}