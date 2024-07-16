package kr.ac.kpu.green_us

import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import org.json.JSONObject

class AddressDialogFragment : DialogFragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_address_search, container, false)
        webView = view.findViewById(R.id.webView)

        // WebView 초기화 및 설정
        setupWebView()

        // WebView에 URL 로드
        webView.loadUrl("http://192.168.25.6:8080/address")

        return view
    }

    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings

        // JavaScript 활성화
        webSettings.javaScriptEnabled = true

        // DOM Storage 활성화
        webSettings.domStorageEnabled = true

        // Mixed content 모드 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        }

        // WebViewClient 설정
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 필요 시 추가 설정
            }
        }

        // WebChromeClient 설정
        webView.webChromeClient = WebChromeClient()

        // JavaScript 인터페이스 추가
        webView.addJavascriptInterface(WebAppInterface(), "Android")
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            val addressData = JSONObject(data)
            val address = addressData.getString("address")

            val bundle = Bundle()
            bundle.putString("address", address)
            parentFragmentManager.setFragmentResult("addressData", bundle)

            dismiss() // Close the dialog fragment
        }
    }

    override fun onStart() {
        super.onStart()
        // Dialog 크기 조절
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 1).toInt(),  // 가로 크기
            (resources.displayMetrics.heightPixels * 0.8).toInt()  // 세로 크기
        )
        dialog?.window?.setGravity(Gravity.CENTER)
    }
}