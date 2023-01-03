/*
 * Copyright © 11/21/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.activities

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
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
        val journalUrl = intent.getStringExtra("url")!!
        val webView = binding.webView
        setToolbarListeners()
        setupWebView(webView)
        binding.textJournalUrl.text = journalUrl
        webView.loadUrl(journalUrl)
    }

    // Override to add transition animation
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun setToolbarListeners() {
        binding.toolbarWebView.apply {
            imageButtonGoBack.setOnClickListener { finish() }
            imageButtonReload.setOnClickListener {
                imageButtonReload.startAnimation(
                    AnimationUtils.loadAnimation(
                        applicationContext, R.anim.rotate_right
                    )
                )
                binding.webView.reload()
                checkInternetConnection()
            }
        }
        binding.buttonTryAgain.setOnClickListener {
            binding.webView.reload()
            checkInternetConnection()
        }
    }

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
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) binding.webView.goBack()
                else finish()  // Only finish activity when can't go back
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
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            checkInternetConnection()
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun checkInternetConnection() {
        if (isConnectedToNetwork()) binding.relativeLayoutNoInternet.visibility = View.GONE
        else binding.relativeLayoutNoInternet.visibility = View.VISIBLE
    }

    private fun isConnectedToNetwork(): Boolean {
        val connectManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true  // Web page won't be available regardless
        val activeNetwork =
            connectManager.getNetworkCapabilities(connectManager.activeNetwork) ?: return false
        return when {
            // Indicates this network uses a Wi-Fi transport, or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            // Indicates this network uses a Cellular transport or Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}