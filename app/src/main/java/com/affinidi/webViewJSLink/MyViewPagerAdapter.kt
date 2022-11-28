package com.affinidi.webViewJSLink

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> WebViewFragment("https://affinidi-web-view.dev.in.affinidi.io/")
            else -> WebViewFragment("https://affinidi-web-view.dev.in.affinidi.io/")
        }
    }
}