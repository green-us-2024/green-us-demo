package kr.ac.kpu.green_us

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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
import kr.ac.kpu.green_us.adapter.MarketAdapter
import kr.ac.kpu.green_us.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.bottomLayout.recyclerviewMarketList.adapter = MarketAdapter()
        binding.bottomLayout.recyclerviewMarketList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val behavior = BottomSheetBehavior.from(binding.bottomLayout.root)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.bottomLayout.btnArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                    BottomSheetBehavior.STATE_EXPANDED -> binding.bottomLayout.btnArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.btnEsc.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.bottomLayout.btnArrow.setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
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