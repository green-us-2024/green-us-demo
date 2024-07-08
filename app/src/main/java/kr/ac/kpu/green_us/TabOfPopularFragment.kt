package kr.ac.kpu.green_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.databinding.FragmentJoin1Binding
import kr.ac.kpu.green_us.databinding.FragmentTabOfPopularBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabOfPopularFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabOfPopularFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentTabOfPopularBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabOfPopularBinding.inflate(inflater,container,false)
        viewManager = GridLayoutManager(requireContext(),2)
        viewAdapter = GreenCardAdapter()
        recyclerView = binding.recyclerviewPopularGreening.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}