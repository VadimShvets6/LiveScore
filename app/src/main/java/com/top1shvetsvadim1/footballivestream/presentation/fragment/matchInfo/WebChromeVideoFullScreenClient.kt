package com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity

class WebChromeVideoFullScreenClient(private val requireActivity: FragmentActivity) : WebChromeClient() {

    private var customView: View? = null
    private var customViewCallback: CustomViewCallback? = null
    private var originalOrientation = requireActivity.requestedOrientation
    private var originalSystemUiVisibility = requireActivity.window.decorView.systemUiVisibility

    override fun getDefaultVideoPoster(): Bitmap? {
        return if (customView == null) {
            null
        } else BitmapFactory.decodeResource(requireActivity.applicationContext.resources, 2130837573)
    }

    override fun onHideCustomView() {
        (requireActivity.window.decorView as FrameLayout).removeView(customView)
        customView = null
        requireActivity.window.decorView.systemUiVisibility = originalSystemUiVisibility
        requireActivity.requestedOrientation = originalOrientation
        customViewCallback!!.onCustomViewHidden()
        customViewCallback = null
        requireActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: CustomViewCallback) {
        if (customView != null) {
            onHideCustomView()
            return
        }
        customView = paramView
        originalSystemUiVisibility = requireActivity.window.decorView.systemUiVisibility
        originalOrientation = requireActivity.requestedOrientation
        customViewCallback = paramCustomViewCallback
        (requireActivity.window.decorView as FrameLayout).addView(customView, FrameLayout.LayoutParams(-1, -1))
        requireActivity.window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        requireActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}