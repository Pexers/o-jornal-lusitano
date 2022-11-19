/*
 * Copyright © 11/19/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.activities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private val webViewClient = WebViewClient()
    private val webChromeClient = WebChromeClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val webView = binding.webView
        setupWebView(webView)
        webView.loadUrl(intent.getStringExtra("url")!!)
    }
    
    // TODO: buttons

    private fun setupWebView(webView: WebView) {
        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            mediaPlaybackRequiresUserGesture = true
            builtInZoomControls = true
            domStorageEnabled = true
        }
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient
        /*CookieManager.getInstance().acceptCookie()
        CookieManager.getInstance().acceptThirdPartyCookies(webView)*/
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()){
                    binding.webView.goBack()  // Don't finish activity if can go back
                }
                else {
                    finish()
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }
        })
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressBar.progress = newProgress
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }

}