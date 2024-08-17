package kr.ac.kpu.green_us
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ac.kpu.green_us.adapter.MarketAdapter
import kr.ac.kpu.green_us.data.Market
import kr.ac.kpu.green_us.databinding.ActivityMapBinding
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.TimerTask

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var bottomAdapter: MarketAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val keyword ="제로웨이스트샵"
        val list = marketTask("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="+keyword)

//        https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&ssc=tab.nx.all&query=사용자 위치 구+keyword&oquery=+keyword+&tqi=iry68sqVOsVssS1Xl%2B0ssssssYs-467466

        // 위치 권한 요청
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // 권한이 부여되었으면 지도 초기화
                initializeMap()
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            initializeMap()
        }

        // bottom sheet 설정
        bottomAdapter = MarketAdapter(list)
        binding.bottomLayout.recyclerviewMarketList.adapter = bottomAdapter
        bottomAdapter.notifyDataSetChanged()
        binding.bottomLayout.recyclerviewMarketList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val behavior = BottomSheetBehavior.from(binding.bottomLayout.root)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, state: Int) {
                when (state) {
                    // 바텀시트가 확장된 상태라면 화살표 버튼이 아래로 향하게 이미지 변경
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.bottomLayout.btnArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                    //접혔을 시 화살표 버튼이 위로 향하게 이미지 변경
                    BottomSheetBehavior.STATE_EXPANDED -> binding.bottomLayout.btnArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        // 이전 버튼
        binding.btnEsc.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        // 바텀 시트 화살표 버튼 클릭시
        binding.bottomLayout.btnArrow.setOnClickListener {
            // 접힌 상태에서 클릭하면 확장시킴
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else { // 확장상태에서 클릭하면 접힘
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun marketTask(url:String):ArrayList<Market>{
        val itemList :ArrayList<Market> = arrayListOf()
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(url).get()
            val elements:Elements = doc.select("li.VLTHu.JJ4Cd")
            elements.forEach { element ->
                val name = element.select("span.YwYLL").text();
                val location = element.select("span.Pb4bU").text();
                val closed = element.select("div.Gvf9B span:nth-child(2)").text();


                val data = Market(name,location,closed)
                itemList.add(data)
//                 쓰레드 사용
                val task:TimerTask = object :TimerTask(){
                    @SuppressLint("NotifyDataSetChanged")
                    override fun run() {
                        runOnUiThread{
                            bottomAdapter.notifyDataSetChanged()
                        }
                    }

                }
                task.run()
            }
        }
        return itemList
    }
    private fun initializeMap() {
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val uiSettings: UiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        // 현재 위치로 카메라 이동
        val cameraUpdate = CameraUpdate.scrollTo(locationSource.lastLocation?.let {
            com.naver.maps.geometry.LatLng(it.latitude, it.longitude)
        } ?: com.naver.maps.geometry.LatLng(37.5666102, 126.9783881)) // 기본 위치는 서울로 설정
        naverMap.moveCamera(cameraUpdate)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}