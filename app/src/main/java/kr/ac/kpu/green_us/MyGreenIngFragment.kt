package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kr.ac.kpu.green_us.adapter.GreenCardAdapter
import kr.ac.kpu.green_us.adapter.MyGreenDegreeAdapter
import kr.ac.kpu.green_us.adapter.MyGreenEndAdapter
import kr.ac.kpu.green_us.adapter.MyGreenIngAdapter
import kr.ac.kpu.green_us.adapter.MyGreenIngMoreAdapter
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.FragmentMyGreenIngBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// 진행중인 그리닝 - 진행중인 그리닝, 전체 그리닝 진척도, 개별 그리닝 진척도 확인 가능
class MyGreenIngFragment : Fragment() {
    lateinit var binding: FragmentMyGreenIngBinding
    lateinit var recyclerViewIng: RecyclerView
    lateinit var recyclerViewDegree: RecyclerView
    lateinit var viewAdapterIng: RecyclerView.Adapter<*>
    lateinit var viewAdapterDegree: RecyclerView.Adapter<*>
    lateinit var viewManagerIng: RecyclerView.LayoutManager
    lateinit var viewManagerDegree: RecyclerView.LayoutManager
    var greenExist = true // 데이터에 따라 달라지게

    lateinit var auth: FirebaseAuth
    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyGreenIngBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        //showNoDataView() // 데이터가 늦게 불러지면 뷰가 떴다 사라지는 경우가 있어서 코드 추가, 나중에 삭제 가능

        val today = LocalDate.now()

        // 리사이클러뷰 중복 스크롤 막기
        binding.recyclerviewGreenDegree.isNestedScrollingEnabled = false

        // 데이터 가져오기
        getUserByEmail { user ->
            if (user != null) {
                val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
                apiService.findGreeningByUserSeq(user.userSeq).enqueue(object :
                    Callback<List<Greening>> {
                    override fun onResponse(call: Call<List<Greening>>, response: Response<List<Greening>>) {
                        if (response.isSuccessful) {
                            val greeningList = response.body() ?: emptyList()
                            val selectedGreeningList = greeningList.filter { greening ->
                                try {
                                    val startDate = LocalDate.parse(greening.gStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                    val endDate = LocalDate.parse(greening.gEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                    today.isEqual(startDate) || (today.isAfter(startDate) && today.isBefore(endDate))
                                } catch (e: Exception) {
                                    false
                                }
                            }.shuffled().take(4)
                            Log.d("MyGreenIngFragment", "Greening Size : ${greeningList.size} -> ${selectedGreeningList.size}")
                            setupRecyclerViews(selectedGreeningList)
                        } else {
                            Log.e("MyGreenIngFragment", "Greening 데이터 로딩 실패: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Greening>>, t: Throwable) {
                        Log.e("MyGreenIngFragment", "서버 통신 중 오류 발생", t)
                    }
                })
            } else {
                showNoDataView()
            }
        }

        return binding.root
    }

    private fun getUserByEmail(callback: (User?) -> Unit) {
        val currentUser = auth.currentUser
        val currentEmail = currentUser?.email.toString()
        Log.d("currentEmail", currentEmail)

        if (currentEmail.isNotEmpty()) {
            val apiService = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
            apiService.getUserbyEmail(currentEmail).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        user = response.body()
                        if (user != null) {
                            Log.d("MyGreenIngFragment", "회원 찾음 : ${user!!.userSeq}")
                        } else {
                            Log.e("MyGreenIngFragment", "회원 못찾음")
                        }
                    } else {
                        Log.e("MyGreenIngFragment", "사용자 조회 실패: ${response.code()}, ${response.errorBody()?.string()}")
                    }
                    callback(user)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("MyGreenIngFragment", "서버 통신 중 오류 발생", t)
                    callback(null)
                }
            })
        } else {
            callback(null)
        }
    }

    private fun setupRecyclerViews(greeningList: List<Greening>) {
        // 진행중인 그리닝 리사이클러뷰 설정
        viewManagerIng = GridLayoutManager(requireContext(), 2)
        viewAdapterIng = MyGreenIngAdapter()
        recyclerViewIng = binding.recyclerviewIngGreening.apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManagerIng
            adapter = viewAdapterIng
        }

        (viewAdapterIng as MyGreenIngAdapter).itemClickListener = object : MyGreenIngAdapter.OnItemClickListener {
            override fun onItemClick(gSeq: Int) {
                val intent = Intent(requireActivity(), GreeningDetailSubActivity::class.java)
                intent.putExtra("ing", "ing_state")
                intent.putExtra("gSeq", gSeq)
                startActivity(intent)
            }
        }

        // 더보기 버튼 클릭 시
        binding.btnMore.setOnClickListener {
            val intent = Intent(requireActivity(), SubActivity::class.java)
            intent.putExtra("0", "green_ing_more")
            startActivity(intent)
        }

        // 그리닝 개별 진척도 리사이클러뷰 설정
        viewManagerDegree = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        viewAdapterDegree = MyGreenDegreeAdapter()
        recyclerViewDegree = binding.recyclerviewGreenDegree.apply {
            setHasFixedSize(true)
            suppressLayout(true)
            layoutManager = viewManagerDegree
            adapter = viewAdapterDegree
        }

        (viewAdapterIng as MyGreenIngAdapter).updateData(greeningList)
    }

    private fun showNoDataView() {
        binding.notExistIng.visibility = View.VISIBLE
        binding.recyclerviewIngGreening.visibility = View.GONE
        binding.moreBtn.visibility = View.GONE
        binding.greenDegree.visibility = View.GONE
        binding.recyclerviewGreenDegree.visibility = View.GONE
    }
}