package com.dicoding.picodiploma.loginwithanimation.view.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityUploadBinding
import com.dicoding.picodiploma.loginwithanimation.utils.getImageUri
import com.dicoding.picodiploma.loginwithanimation.utils.reduceFileImage
import com.dicoding.picodiploma.loginwithanimation.utils.uriToFile
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private var currentImageUri: Uri? = null
    private val viewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectImage.setOnClickListener {
            galleryLaunch.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnTakeImage.setOnClickListener {
            currentImageUri = getImageUri(this)
            cameraLaunch.launch(currentImageUri!!)
        }

        binding.buttonAdd.setOnClickListener {
            if (!binding.edAddDescription.text.isNullOrBlank() && currentImageUri != null) {
                currentImageUri?.let { uri ->
                    val imageFile = uriToFile(uri, this).reduceFileImage()
                    val desc = binding.edAddDescription.text.toString()

                    showLoading(true)

                    val requestBody = desc.toRequestBody("text/plain".toMediaType())
                    val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())

                    val multipartBody =
                        MultipartBody.Part.createFormData("photo", imageFile.name, requestImageFile)

                    viewModel.uploadStory(multipartBody, requestBody).observe(this) { response ->
                        when (response) {
                            is Result.Error -> showToast(getString(R.string.upload_failed))
                            Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showToast(getString(R.string.upload_success))
                                runBlocking {
                                    delay(500)
                                }
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                            }
                        }
                    }
                }
            } else {
                showToast(getString(R.string.upload_not_valid))
            }
        }
    }

    private val galleryLaunch =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                currentImageUri = uri
                imageShow()
            } else {
                Log.d("Photo Picker", "No media selected")
            }
        }

    private val cameraLaunch =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                imageShow()
            } else {
                currentImageUri = null
            }
        }

    fun imageShow() {
        currentImageUri?.let { uri ->
            binding.ivStoryImage.setImageURI(uri)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar5.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        showLoading(false)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}


