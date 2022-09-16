package com.example.sampleapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.databinding.FragmentSecondBinding
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.lang.Exception


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val urlStr: String? = arguments?.getString("url");
        val myWebView: WebView = view.findViewById(R.id.webView);

        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                Log.println(Log.INFO,null, "I am here === $url");
                if (url.startsWith("https://avibra.app.link", true) || url.startsWith("https://avibra.test-app.link", true)) {
                    return try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        myWebView.stopLoading()
                        myWebView.goBack()
                        false
                    } catch (e: Exception) {
                        Log.println(Log.INFO,null, "I am Exception1 === $e");
                        true
                    }
                } else if (url.startsWith("market://details?id=com.avibra", true) || url.startsWith("https://play.google.com/store/apps/details?id=com.avibra", true)) {
                    return try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        myWebView.stopLoading()
                        myWebView.goBack()
                        false
                    } catch (e: Exception) {
                        Log.println(Log.INFO,null, "I am Exception2 === $e");
                        false
                    }
                } else {
                    webView.loadUrl(url)
                }
                return true
            }
            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                Log.println(Log.INFO,null, "Error = $error");
                Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
            }
        }
        urlStr?.let { myWebView.loadUrl(it) };
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}