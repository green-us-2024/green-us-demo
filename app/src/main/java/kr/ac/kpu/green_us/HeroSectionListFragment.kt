package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.storage.FirebaseStorage
import kr.ac.kpu.green_us.adapter.HeroAdapter
import kr.ac.kpu.green_us.adapter.HeroListAdapter
import kr.ac.kpu.green_us.adapter.HomeBuyAdapter
import kr.ac.kpu.green_us.databinding.FragmentHeroSectionListBinding

class HeroSectionListFragment : Fragment() {
    lateinit var binding: FragmentHeroSectionListBinding
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager
    private var representImgList  = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHeroSectionListBinding.inflate(inflater, container, false)

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("heroImgs/")
        // 스토리지 이미지 전체 가져옴
        storageRef.listAll().addOnSuccessListener { listResult ->
            for (img in listResult.items) {
                img.downloadUrl.addOnSuccessListener { uri ->
                    representImgList.add(uri.toString())
                }.addOnSuccessListener {
                    // hero banner
                    viewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    viewAdapter = HeroListAdapter(representImgList)
                    recyclerView = binding.recyclerviewHero.apply {
                        setHasFixedSize(true)
                        suppressLayout(true)
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                }
            }
        }
//        // hero banner
//        viewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        viewAdapter = HeroListAdapter()
//        recyclerView = binding.recyclerviewHero.apply {
//            setHasFixedSize(true)
//            suppressLayout(true)
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }

        return binding.root
    }

    private fun getHeroList():ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.hero_img_1,R.drawable.hero_img_2,R.drawable.hero_img_3)
    }

}