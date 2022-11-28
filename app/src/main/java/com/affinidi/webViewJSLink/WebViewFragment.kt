package com.affinidi.webViewJSLink

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.affinidi.webViewJSLink.databinding.FragmentWebviewBinding
import org.json.JSONObject


class WebViewFragment(private val webViewUrl: String): Fragment() {
    private lateinit var binding: FragmentWebviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    @JavascriptInterface
    fun postMessage(json: String?, transferList: String?): Boolean {
        if (json !== null) {Log.d("Data from JS", json)}
        return false // here we return true if we handled the post.
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.webview){
            val myJSONData = "{\n" +
                    "  type: 'initialize',\n" +
                    "  apiKey: '4bfcd492c52061a57df1eba07b4c6e0a716d92c1c1d79ebbb32141b489d20b84',\n" +
                    "  retries: 3,\n" +
                    "  theme: {\n" +
                    "    primaryColor: '#73C36C',\n" +
                    "    secondaryColor: '#73C36C',\n" +
                    "    primaryTextColor: '#324e7e',\n" +
                    "    secondaryTextColor: '#41639c',\n" +
                    "    fontFamily: 'Poppins, sans-serif'\n" +
                    "  },\n" +
                    "  customLanguage: {\n" +
                    "    signIn: {\n" +
                    "      title: 'apna phone number daalein',\n" +
                    "      subTitle: 'OTP verify karein aur 10 sec main jobs apply karein'\n" +
                    "    },\n" +
                    "    optVerify: {\n" +
                    "      title: 'OTP bharein',\n" +
                    "      subTitle: 'aapke number #phoneNumber# pe OTP aaya hoga use yahan bharein'\n" +
                    "    }\n" +
                    "  }\n" +
                    "}"
            val objJSON = JSONObject(myJSONData)
            this.clearCache(true)
//            this.loadDataWithBaseURL("https://affinidi-web-view.dev.in.affinidi.io/",objJSON.toString(), "text/html", "utf-8", null)
            this.loadUrl(webViewUrl)

            settings.javaScriptEnabled = true
//            this.settings.javaScriptEnabled = true
//            this.webChromeClient = WebChromeClient()
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

            webViewClient = MyWebViewClient(requireActivity().applicationContext, binding.progressBar, binding.errorAnim)

        }
    }


    fun canGoBack(): Boolean {
        return binding.webview.canGoBack()
    }

    fun goBack(){
        binding.webview.goBack()
    }
}