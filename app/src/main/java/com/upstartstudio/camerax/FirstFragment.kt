package com.upstartstudio.camerax

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.upstartstudio.camerax.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

  private var _binding: FragmentFirstBinding? = null

  private val binding get() = _binding!!

  private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    _binding = FragmentFirstBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

    cameraProviderFuture.addListener(Runnable {
      val cameraProvider = cameraProviderFuture.get()
      bindPreview(cameraProvider)
    }, ContextCompat.getMainExecutor(requireContext()))
  }

  fun bindPreview(cameraProvider : ProcessCameraProvider) {
    var preview : Preview = Preview.Builder()
      .build()

    var cameraSelector : CameraSelector = CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_BACK)
      .build()

    preview.setSurfaceProvider(binding.previewView.surfaceProvider)

    var camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
