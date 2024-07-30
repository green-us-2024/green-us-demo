package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.ac.kpu.green_us.databinding.FragmentHeroSectionDetailBinding

class HeroSectionDetailFragment : Fragment() {
    lateinit var binding: FragmentHeroSectionDetailBinding
    var data: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHeroSectionDetailBinding.inflate(inflater, container, false)

        arguments?.let{
            data = it.getInt("img_num")
        }
        Toast.makeText(requireContext(), "${data}번째 배너", Toast.LENGTH_SHORT).show()

        if(data == 0) {
            binding.heroSection.setImageResource(R.drawable.hero_img_1)
        }
        else if(data == 1) {
            binding.heroSection.setImageResource(R.drawable.hero_img_2)
        }
        else if(data == 2) {
            binding.heroSection.setImageResource(R.drawable.hero_img_3)
        }

        return binding.root
    }
}