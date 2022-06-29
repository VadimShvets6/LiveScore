package com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.top1shvetsvadim1.footballivestream.databinding.FragmentDetailMatchInfoBinding


class DetailMatchInfoFragment : Fragment() {

    private var _binding: FragmentDetailMatchInfoBinding? = null
    private val binding: FragmentDetailMatchInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailMatchInfoBinding == null")

    private lateinit var urlMatch : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        urlMatch = arguments?.getString("url", "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMatchInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myOnBackPressed()
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply{
            loadUrl(urlMatch)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
        binding.innerToolbarMenu.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack("Match", 0)
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
                    requireActivity().supportFragmentManager.popBackStack("Match", 0)
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            DetailMatchInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("url", param1) }
            }
    }
}