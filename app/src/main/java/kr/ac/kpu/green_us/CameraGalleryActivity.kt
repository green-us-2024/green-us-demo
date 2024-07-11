package kr.ac.kpu.green_us

import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.green_us.databinding.ActivityCameraGalleryBinding

// 카메라, 갤러리 다이얼로그 띄우기
class CameraGalleryActivity(private val context : AppCompatActivity) {
    private lateinit var binding : ActivityCameraGalleryBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    private lateinit var listener : MyDialogCameraClickedListener

    fun show() {
        binding = ActivityCameraGalleryBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴

        // 카메라 버튼 동작
        binding.useCamera.setOnClickListener {
            listener.onCameraClicked(1)
            dlg.dismiss()
        }

        // 갤러리 버튼 동작
        binding.useGallery.setOnClickListener {
            listener.onCameraClicked(2)
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnCameraClickedListener(listener: (Int) -> Unit) {
        this.listener = object: MyDialogCameraClickedListener {
            override fun onCameraClicked(content: Int) {
                listener(content)
            }
        }
    }


    interface MyDialogCameraClickedListener {
        fun onCameraClicked(content : Int)
    }

}