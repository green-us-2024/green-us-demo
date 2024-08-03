package kr.ac.kpu.green_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.ac.kpu.green_us.databinding.DialogReportBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

interface ReportDialogInterface {
    fun onReportYesButton()
}
class ReportDialog(reportDialogInterface: ReportDialogInterface):DialogFragment() {
    // 뷰 바인딩 정의
    private var _binding: DialogReportBinding? = null
    private val binding get() = _binding!!
    private var reportDialogInterface: ReportDialogInterface? = null


    init {
        this.reportDialogInterface = reportDialogInterface
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogReportBinding.inflate(inflater, container, false)
        val view = binding.root

        // 다이얼로그 배경 투명화
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        // 취소 버튼 클릭
        binding.btnCancle.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.btnReport.setOnClickListener {
            this.reportDialogInterface?.onReportYesButton()
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}