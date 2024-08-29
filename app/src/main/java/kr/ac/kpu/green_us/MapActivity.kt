package kr.ac.kpu.green_us
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.*
import kr.ac.kpu.green_us.adapter.MarketAdapter
import kr.ac.kpu.green_us.data.Market
import kr.ac.kpu.green_us.data.MarketTime
import kr.ac.kpu.green_us.databinding.ActivityMapBinding
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.Locale
import kotlin.concurrent.thread


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationListener? = null
    lateinit var keyword :String
    val searchId = BuildConfig.SEARCH_ID
    val searchSecret = BuildConfig.SEARCH_SECRET
    val mapId = BuildConfig.MAP_ID
    val mapSecret = BuildConfig.MAP_SECRET



    @SuppressLint("NotifyDataSetChanged")
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
            this.finish()
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
    private fun getAddress(){
        mLocationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        // 위치정보의 위도, 경도 값 찾아서 한국어 주소로 변환
        mLocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                var lat = 0.0
                var lng = 0.0
                if (location != null) {
                    lat = location.latitude
                    lng = location.longitude
                    Log.d("GmapViewFragment", "Lat: ${lat}, lon: ${lng}")

                    // 위도, 경도를 주소값으로 변환
                    val geocoder = Geocoder(applicationContext, Locale.KOREAN)
                    // 안드로이드 API 레벨이 33 이상인 경우
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geocoder.getFromLocation(
                            lat, lng, 1
                        ) { address ->
                            if (address.size != 0) {
                                // (반환 값에서) 전체 주소 중 첫번째 값만 사용하여 한국어 주소로 변환하러 간다
                                filterAddress(address[0].getAddressLine(0))
                                println("address -> {$address}")
                            }
                        }
                    } else { // API 레벨이 33 미만인 경우
                        val addresses = geocoder.getFromLocation(lat, lng, 1)
                        if (addresses != null) {
                            filterAddress(addresses[0].getAddressLine(0))
                        }
                    }
                }
            }
        }
        // 위치 변경시 위치 정보 업데이트 코드
        // 위치 권한 확인하고, 부여됐으면
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, // gps 이용해서
                3000L, // 3초 간격 또는
                30f, // 거리가 30미터 변할 때마다 위치 정보를 업데이트 함
                mLocationListener as LocationListener
            )

        }
    }
    private fun filterAddress(words:String){
        val totalKeyword: String
        var result = ""
        var cursor = 0

        cursor = words.indexOf("시 ") // oo시를 찾고 자른다. 없으면 커서 -1
        if (cursor == -1) { //'시' 없으면 '군'을 찾음
            cursor = words.indexOf("군 ")
        }
        cursor += 2 // 두 칸 이동

        // 시 및 군의 다음 3글자를 추출함
        // 3글자 이상인 것도 있으나, 네이버 검색 api에서 잘 인식하여 찾아줌
        result = words.substring(cursor, cursor + 3)
        val filterAddress =
            try {
                URLEncoder.encode(result, "UTF-8")
            }catch (e: UnsupportedEncodingException){
                throw RuntimeException("검색어 인코딩 실패", e)
            }
        keyword =
            try {
                URLEncoder.encode("제로웨이스트", "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("검색어 인코딩 실패", e)
            }
        totalKeyword = filterAddress+" "+keyword // 예시) 해운대구 제로웨이스트
//        println("totalKeyword -> {$totalKeyword}")
        conHttp(totalKeyword) // url에 붙일 query로 네트워킹 작업하러 감
    }
    //네이버 검색,geocoding api 통신 및 웹크롤링 함수
    private fun conHttp(address:String){
        thread {
            try {
                // 검색api
                var marketList:ArrayList<Market>
                val apiURL = "https://openapi.naver.com/v1/search/local?query=$address&display=10"
                val requestHeaders = mapOf(
                    "X-Naver-Client-Id" to searchId,
                    "X-Naver-Client-Secret" to searchSecret
                )
                val httpReslt = get(apiURL, requestHeaders) // 검색 api 요청 및 응답
                marketList = jsonData(httpReslt) // 마켓 데이터 추출

                // geocoding api -> 마커 찍기를 위해 마켓 주소로 위도, 경도 불러와서 marketList에 추가함
                for (i in 0 until marketList.size){
                    val query =
                        try {
                            URLEncoder.encode(marketList[i].location, "UTF-8")
                        }catch (e: UnsupportedEncodingException) {
                            throw RuntimeException("geocoding api address 인코딩 실패", e)
                        }
                    println("query -> {$query}")
                    val apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${query}"
                    val requestHeaders = mapOf(
                        "X-NCP-APIGW-API-KEY-ID" to mapId ,
                        "X-NCP-APIGW-API-KEY" to mapSecret
                    )
                    val httpResult = get(apiUrl,requestHeaders)
                    val root = JSONObject(httpResult)
                    val dataArray = root.getJSONArray("addresses")
                    for (index in 0 until dataArray.length()){
                        val data = dataArray.getJSONObject(index)
                        val lati = data.getDouble("y")
//                    println("geocoding -> {$lati}")
                        val longi = data.getDouble("x")
//                    println("geocoding -> {$longi}")
                        marketList[i].lati = lati
                        marketList[i].longi = longi
                    }
                }
                println("marketList -> {${marketList}}")


                // 웹크롤링 -> api로는 영업에 대한 정보가 없기에 웹크롤링으로 찾음 검색 api가 가져오는 정보리스트와 동일한 url
                val timeList: ArrayList<MarketTime> = arrayListOf()
                val crawlingURL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=$address"
                val doc = Jsoup.connect(crawlingURL).get()
                val elements: Elements = doc.select("li.VLTHu.JJ4Cd")

                elements.forEach { element ->
                   val closed = element.select("div.Gvf9B span:nth-child(2)").text()
                    val type = closed.javaClass
                    println("closedType ->  {$type}")
                    println("closed -> {$closed}")
                    if (closed.isNullOrEmpty()) { // 웹크롤링으로도 정보가 없는 것은 "정보없음"으로 저장함
                        val data = MarketTime("정보없음")
                        timeList.add(data)
                    } else { 
                        val data = MarketTime(closed)
                        timeList.add(data)
                    }
                }
                runOnUiThread { // ui 업데이트
                    // 바텀시트 업데이트
                    val bottomAdapter = MarketAdapter(marketList, timeList)
                    binding.bottomLayout.recyclerviewMarketList.adapter = bottomAdapter
                    bottomAdapter.notifyDataSetChanged()
                    bottomAdapter.itemClickListener = object : MarketAdapter.OnItemClickListener {
                        override fun onItemClick(url: String) {
                            if(url.isNotEmpty()){
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)
                            }else{
                                Toast.makeText(applicationContext,"링크 정보가 없습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    // 마커 업데이트
                    marking(marketList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun marking(positions : ArrayList<Market>){
        val markers = arrayOfNulls<Marker>(positions.size)
        for (i in 0 until positions.size){
            markers[i] =  Marker()
            val lati = positions[i].lati
            println("markin lati -> {$lati}")
            val longi = positions[i].longi
            println("markin longi -> {$longi}")
            markers[i]?.position = LatLng(lati, longi)
            markers[i]?.captionText = positions[i].name
            markers[i]?.map = naverMap
        }
    }
    private fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
        val url = URL(apiUrl)
        val con = url.openConnection() as HttpURLConnection
        return try {
            con.requestMethod = "GET"
            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                readBody(con.inputStream)
            } else { // 오류 발생
                readBody(con.errorStream)
            }
        } catch (e: IOException) {
            throw RuntimeException("API 요청과 응답 실패", e)
        } finally {
            con.disconnect()
        }
    }
    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)

        return BufferedReader(streamReader).use { lineReader ->
            val responseBody = StringBuilder()

            var line: String?
            while (lineReader.readLine().also { line = it } != null) {
                responseBody.append(line)
            }
            responseBody.toString()
        }
    }
    private fun jsonData(body:String):ArrayList<Market>{
        val root = JSONObject(body)
        val dataArray = root.getJSONArray("items")
        val marketInfos : ArrayList<Market> = arrayListOf()
        for (index in 0 until dataArray.length()){
            val data = dataArray.getJSONObject(index)
            val name = data.getString("title").replace("<b>","").replace("</b>","")
            val location = data.getString("address")
            val link = data.getString("link")
            val infos = Market(name,location,link,0.0,0.0)
            marketInfos.add(infos)
        }
        return marketInfos
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
        getAddress()

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}