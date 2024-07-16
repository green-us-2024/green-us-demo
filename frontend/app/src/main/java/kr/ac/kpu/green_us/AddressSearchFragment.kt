package kr.ac.kpu.green_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import org.json.JSONObject

class AddressSearchFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_address_search, container, false)
        webView = view.findViewById(R.id.webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 페이지 로딩 완료 후 추가 작업 필요한 경우
            }
        }

        // JavaScript 인터페이스 추가
        webView.addJavascriptInterface(WebAppInterface(), "Android")
            //여기 주소도 마찬가지로 본인 pc주소로 맞춰줘야함.
        webView.loadUrl("http://192.168.25.6:8080/address")
        return view
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            val addressData = JSONObject(data)
            val address = addressData.getString("address")

            val bundle = Bundle()
            bundle.putString("address", address)
            parentFragmentManager.setFragmentResult("addressData", bundle)

            // Fragment를 닫기 위해
            parentFragmentManager.popBackStack()
        }
    }
}