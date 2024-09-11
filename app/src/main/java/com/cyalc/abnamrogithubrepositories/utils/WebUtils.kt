package com.cyalc.abnamrogithubrepositories.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun openInBrowser(context: Context, url: String) {
  try {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
  } catch (e: Exception) {
    // Fallback to default browser if CustomTabs is not available
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
  }
}
