package kr.ac.kpu.green_us

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kr.ac.kpu.green_us.databinding.FragmentGreenOpenBinding
import java.util.*

class GreenOpenFragment : Fragment() {
    lateinit var binding: FragmentGreenOpenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGreenOpenBinding.inflate(inflater,container,false)

        // 날짜 입력 editText 달력으로만 받게 하기 위해 비활성화
        binding.startDateEt.setClickable(false);
        binding.startDateEt.setFocusable(false);

        // 사진명 입력 editText 정보 가져와서 받게 하기 위해 비활성화
        binding.uploadPictureEt.setClickable(false);
        binding.uploadPictureEt.setFocusable(false);

        // 개설하기 버튼
        binding.openGreenBtn.setOnClickListener {
            val intent = Intent(getActivity(), MainActivity::class.java)
            intent.putExtra("key2_3", "open")
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 달력 이미지 클릭
        binding.btnDate.setOnClickListener {
            showDatePicker()
        }

        // 사진 업로드 이미지
        binding.btnUpload.setOnClickListener {
            val galleryPermissionCheck = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_MEDIA_IMAGES
            )
            if (galleryPermissionCheck != PackageManager.PERMISSION_GRANTED) { // 권한이 없는 경우
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    1000
                )
            }
            if (galleryPermissionCheck == PackageManager.PERMISSION_GRANTED) { //권한이 있는 경우
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                activityResult.launch(intent)
            }
        }

        return binding.root
    }

    // 달력 다이얼로그
    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        var dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            var connect = "-"
            var year = y.toString()
            var month = (m + 1).toString()
            var date = d.toString()
            var start_date = year + connect + month + connect + date
            binding.startDateEt.setText(start_date)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000;
        dpd.show()
    }

    // 이미지 저장
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val uri = it.data!!.data
        binding.uploadPictureEt.setText(uri?.getLastPathSegment())
    }

}