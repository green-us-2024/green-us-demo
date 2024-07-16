package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.adapter.GreenCardAdapter
import kr.ac.kpu.green_us.databinding.FragmentTabOfNewBinding
import kr.ac.kpu.green_us.databinding.FragmentTabOfPopularBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabOfNewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabOfNewFragment : Fragment() {

    private var _binding: FragmentTabOfNewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabOfNewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewManager = GridLayoutManager(requireContext(),2)
        val viewAdapter = GreenCardAdapter()
        //        viewAdapter.notifyDataSetChanged()

        val recyclerView = binding.recyclerviewNewGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewAdapter.itemClickListener = object : GreenCardAdapter.OnItemClickListener{
            //onItemClick(position: Int)
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
    }


}