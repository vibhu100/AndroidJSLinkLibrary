package com.affinidi.webViewJSLink

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView

class MyWebViewClient(private val context: Context, private val progressBar: ProgressBar, private val errorAnim: LottieAnimationView): WebViewClient(){
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null){
            if (url.contains("intent://")){
                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                return true
            }
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
        view?.evaluateJavascript("document.dispatchEvent(\n" +
                "  new MessageEvent('message', {\n" +
                "    data: {\n" +
                "      type: 'initialize',\n" +
                "      apiKey: '4bfcd492c52061a57df1eba07b4c6e0a716d92c1c1d79ebbb32141b489d20b84',\n" +
                "      retries: 3,\n" +
                "      theme: {\n" +
                "        primaryColor: '#73C36C',\n" +
                "        secondaryColor: '#73C36C',\n" +
                "        primaryTextColor: '#324e7e',\n" +
                "        secondaryTextColor: '#41639c',\n" +
                "        fontFamily: 'Poppins, sans-serif'\n" +
                "      },\n" +
                "      customLanguage: {\n" +
                "        signIn: {\n" +
                "          title: 'apna phone number daalein',\n" +
                "          subTitle: 'OTP verify karein aur 10 sec main jobs apply karein'\n" +
                "        },\n" +
                "        optVerify: {\n" +
                "          title: 'OTP bharein',\n" +
                "          subTitle: 'aapke number #phoneNumber# pe OTP aaya hoga use yahan bharein'\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "  }),\n" +
                ")\n", null)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        errorAnim.visibility = View.GONE
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {

        if (error?.errorCode == ERROR_HOST_LOOKUP){
            errorAnim.visibility = View.VISIBLE
            view?.loadUrl("about:blank")
        } else {
            errorAnim.visibility = View.GONE
        }

        super.onReceivedError(view, request, error)
    }
}