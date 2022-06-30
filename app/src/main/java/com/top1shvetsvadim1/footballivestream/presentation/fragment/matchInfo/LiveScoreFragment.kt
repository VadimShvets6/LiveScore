package com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentDetailMatchInfoBinding
import com.top1shvetsvadim1.footballivestream.databinding.FragmentLiveScoreBinding

class LiveScoreFragment : Fragment() {

    private var _binding: FragmentLiveScoreBinding? = null
    private val binding: FragmentLiveScoreBinding
        get() = _binding ?: throw RuntimeException("FragmentLiveScoreBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myOnBackPressed()
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            loadUrl(URL_LIVE_SCORE)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
        binding.innerToolbarMenu.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        //--------------------------VideoFullScreen-------------------------------------------------
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeVideoFullScreenClient(requireActivity())
        //--------------------------VideoFullScreen-------------------------------------------------
    }

    private fun myOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.webView.canGoBack()){
                    binding.webView.goBack()
                }else {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {
        private const val URL_LIVE_SCORE =
            "https://www.scorebat.com/embed/livescore/?token=MjA5NzJfMTY1NjUwODcwNV82ODkzNmQ3OTJiMWJmYjU1NTE2ZTEzYTZlMDU5NTdlM2YwOGM1YTkz"

        @JvmStatic
        fun newInstance() = LiveScoreFragment()
    }
}