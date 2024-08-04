package kr.ac.kpu.green_us

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.databinding.ActivityCertificationImgDetailBinding
import kr.ac.kpu.green_us.databinding.ActivityCertifyGreeningBinding

class CertificationImgDetailActivity : AppCompatActivity(),ReportDialogInterface {
    private lateinit var binding: ActivityCertificationImgDetailBinding
    private var url : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCertificationImgDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 액티비티에서 보낸 url 받기
        url = intent.getStringExtra("imgUrl").toString()
//        Log.d("url check",url)

        //초기세팅
        viewInit(url)

        // 신고 버튼 클릭
        binding.btnReport.setOnClickListener {
            showDialog()
        }

        // 이전 버튼 클릭
        binding.btnEsc.setOnClickListener { finish() }

        // 닫기 버튼 클릭
        binding.btnClose.setOnClickListener { finish() }
    }

    private fun viewInit(url:String){
        // 클릭한 이미지 url 받아와서 이미지뷰에 붙임
        Glide.with(this).load(url).into(binding.selectedImg)
    }
    // 다이얼로그에서 신고 버튼 클릭시 해당 url의 이메일 값 받아옴
    private fun searchUrlUid(url: String) {
        val store = Firebase.firestore
        val storageUrlList = arrayListOf<String>() //firestore에 저장된 이미지 url 담을 리스트
        store.collection("certificationImgs").get().addOnSuccessListener {
            dataList -> for (data in dataList){
                val aimData = data.data.get("url").toString()
                storageUrlList.add(aimData)
                if (aimData == url){ // 선택된 이미지와 같은 url을 찾아 그것의 uid를 가져옴
                    val result = data.data.get("userEmail").toString() // result = 찾은 email 값
                    if (result != null){ // email이 null이 아니라면 reportedEmail 이라는 태그로 로그에 찍음
                        Log.d("reportedEmail",result)
                        Toast.makeText(this, "신고되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{ // url은 있는데 email이 없는 경우
                        Log.d("reportedEmail","null Error")
                        Toast.makeText(this, "삭제된 회원입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (url !in storageUrlList){ // firebasestore에 없는 url인 경우
                Toast.makeText(this,"삭제되거나 이미 신고된 사진입니다.",Toast.LENGTH_SHORT).show()
                Log.d("CertificationImgError","firebasestore(DB)에 없는 이미지 url")
            }
        }
    }
    // 신고 다이얼로그 띄우기
    private fun showDialog(){
        val dialog = ReportDialog(this)
        dialog.isCancelable = false //다이얼로그 띄워진동안 클릭 막기
        this.let { dialog.show(it.supportFragmentManager,"ReportDialog") }
    }
    // 다이얼로그에서 신고버튼 클릭시
    override fun onReportYesButton() {
        searchUrlUid(url)
    }
}
