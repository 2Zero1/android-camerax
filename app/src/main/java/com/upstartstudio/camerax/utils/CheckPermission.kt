package com.upstartstudio.camerax.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

fun CheckPermission(activity: ComponentActivity, ) {
  // Register the permissions callback, which handles the user's response to the
  // system permissions dialog. Save the return value, an instance of
  // ActivityResultLauncher. You can use either a val, as shown in this snippet,
  // or a lateinit var in your onAttach() or onCreate() method.
  val requestPermissionLauncher =
    activity.registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
      if (isGranted) {
        // Permission is granted. Continue the action or workflow in your
        // app.
      } else {
        // Explain to the user that the feature is unavailable because the
        // feature requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
      }
    }

  when {
    ContextCompat.checkSelfPermission(
      activity.applicationContext,
      Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED -> {
      // You can use the API that requires the permission.
    }
//      shouldShowRequestPermissionRationale(...) -> {
//      // In an educational UI, explain to the user why your app requires this
//      // permission for a specific feature to behave as expected, and what
//      // features are disabled if it's declined. In this UI, include a
//      // "cancel" or "no thanks" button that lets the user continue
//      // using your app without granting the permission.
//      showInContextUI(...)
//    }
    else -> {
      // You can directly ask for the permission.
      // The registered ActivityResultCallback gets the result of this request.
      requestPermissionLauncher.launch(
        Manifest.permission.CAMERA)
      requestPermissionLauncher.launch(
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
  }
}
