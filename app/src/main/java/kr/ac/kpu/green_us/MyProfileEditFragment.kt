package kr.ac.kpu.green_us

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import kr.ac.kpu.green_us.common.RetrofitManager
import kr.ac.kpu.green_us.common.api.RetrofitAPI
import kr.ac.kpu.green_us.common.dto.User
import kr.ac.kpu.green_us.databinding.FragmentMyProfileEditBinding
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.util.Calendar


class MyProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentMyProfileEditBinding
    lateinit var bitmap: Bitmap
    lateinit var uri: Uri
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var userEmail: String
    var cameraOrGallery: Int = 0
    private var address = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyProfileEditBinding.inflate(inflater,container,false)

        // auth 인스턴스 초기화
        auth = Firebase.auth
        uid = auth.currentUser?.uid.toString()
        userEmail = auth.currentUser?.email.toString()

        // 프로필 이미지 둥글게
        binding.userImg.clipToOutline = true
        uploadImgToProfile(uid)

        // 사용자 정보 불러오기
        val retrofitAPI = RetrofitManager.retrofit.create(RetrofitAPI::class.java)
        retrofitAPI.getUserbyEmail(userEmail).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {
                if(response.isSuccessful){
                    val userName = response.body()?.userName.toString()
                    val userPhone = response.body()?.userPhone.toString()
                    var userPhone1:String = "000"
                    var userPhone2:String = "0000"
                    var userPhone3:String = "0000"
                    if(userPhone.length == 10){
                        userPhone1 = userPhone.substring(0,2)
                        userPhone2 = userPhone.substring(2,6)
                        userPhone3 = userPhone.substring(6 .. userPhone.lastIndex)
                        binding.phone2.text = userPhone1 + "-"+userPhone2 + "-" + userPhone3
                    }
                    else if(userPhone.length == 11){
                        userPhone1 = userPhone.substring(0,3)
                        userPhone2 = userPhone.substring(3,7)
                        userPhone3 = userPhone.substring(7 .. userPhone.lastIndex)
                        binding.phone2.text = userPhone1 + "-"+userPhone2 + "-" + userPhone3
                    }
                    else{
                        binding.phone2.text = userPhone
                    }
                    val userAddr = response.body()?.userAddr.toString()

                    binding.name2.text = userName
                    binding.email2.text = userEmail
                    binding.address2.text = userAddr
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("MyProfileActivity","사용자 정보 조회 실패")
            }

        })

        // 주소찾기 버튼 클릭 시
        binding.btnSearchAddress.setOnClickListener {
            val dialogFragment = AddressDialogFragment()
            dialogFragment.show(parentFragmentManager, "AddressDialog")
        }
        setFragmentResultListener("addressData") { _, bundle ->
            address = bundle.getString("address", "")
            address?.let {
                binding.address2.setText(it)
            }
        }


        // 카메라 버튼 클릭 시 카메라/갤러리 창 띄우기
        //binding.camera.setOnClickListener(this)

        // 완료 버튼 클릭 시 activity_my_profile로 이동
        binding.complete.setOnClickListener {
            (activity as SubActivity).changeVisibility()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        return binding.root
    }

    fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        if (inImage != null) {
            inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        }
        val path = MediaStore.Images.Media.insertImage(inContext?.getContentResolver(), inImage, "Title" + " - " + Calendar.getInstance().getTime(), null)
        return Uri.parse(path)
    }

    private fun imageUpload(uri: Uri,uid:String) {
        // storage 인스턴스 생성
        val storage = Firebase.storage
        // storage 참조
        val storageRef = storage.getReference("profileImgs/")
        // storage에 저장할 파일명 선언
        val fileName = uid
        val mountainsRef = storageRef.child(fileName)

        val uploadTask = mountainsRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
            //Toast.makeText(this, "사진 업로드 성공", Toast.LENGTH_SHORT).show();
            uploadImgToProfile(uid)
        }.addOnFailureListener {
            // 파일 업로드 실패
            //Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_SHORT).show();
        }
    }
    private fun uploadImgToProfile(uid:String){
        val storage = Firebase.storage
        val storageRef = storage.getReference("profileImgs/${uid}")
        storageRef.downloadUrl.addOnSuccessListener {
            Log.d("profileImg",it.toString())
            Glide.with(this).load(it).into(binding.userImg)
        }.addOnFailureListener {
            Log.d("profileImg","사진 불러오기 실패")
        }
    }

}