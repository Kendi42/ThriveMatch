package com.example.thrivematch.ui.account_setup

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.network.AccountSetupAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.AccountSetupRepository
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentInvestorSetup3Binding
import com.example.thrivematch.databinding.FragmentLoginBinding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.ui.authentication.AuthenticationViewModel
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.example.thrivematch.util.handleApiError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class InvestorSetup3Fragment :
    BaseFragment<SharedAccountSetupViewModel, FragmentInvestorSetup3Binding, AccountSetupRepository>() {
    private lateinit var commonSharedPreferences: CommonSharedPreferences
    private var selectedImage: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInvestorSetup3Binding.bind(view)
        commonSharedPreferences = CommonSharedPreferences(requireContext())

        //Linear Progress Bar
        binding.progressBarInvestor3.progress = 100

        // Radial Progress Bar
        binding.investorSetupProgressBar.isVisible= false

        // Observe Account Setup Response
        viewModel.accountSetupResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            binding.investorSetupProgressBar.isVisible= false
            when(it){
                is Resource.Success ->{
                    Toast.makeText(requireContext(),"Account Setup Success", Toast.LENGTH_SHORT).show()
                    Log.i("Account Setup Success", it.toString())
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                }
                is Resource.Failure -> handleApiError(it){ submitInvestorData() }
                is Resource.Loading->{

                }
            }

        })

        //Navigation Buttons
        binding.btnBackInvestor3.setOnClickListener {
            findNavController().navigate(R.id.action_investorSetup3Fragment_to_investorSetup2Fragment)
        }
        binding.btnFinishInvestor3.setOnClickListener {
            submitInvestorData()

        }

        // Image View Data Persistence
        if(commonSharedPreferences.getStringData(Constants.INVESTORPHOTO) != ""){
            val imagePath = commonSharedPreferences.getStringData(Constants.INVESTORPHOTO)
            val imageFile = File(imagePath)
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            binding.ivUploadProfile.setImageBitmap(bitmap)

        }

        //Image Upload OnClick Listener
        binding.ivUploadProfile.setOnClickListener {
            showMenuDialog()
        }


    }

    private fun submitInvestorData() {
            val investorData = InvestorModel(
                name = commonSharedPreferences.getStringData(Constants.INVESTORNAME),
                investorType = commonSharedPreferences.getStringData(Constants.INVESTORTYPE),
                description = commonSharedPreferences.getStringData(Constants.INVESTORDESCRIPTION),
                selectedInterests = commonSharedPreferences.getStringArray(Constants.INVESTORINTERESTS),
                photo= commonSharedPreferences.getStringData(Constants.INVESTORPHOTO)
            )
            binding.investorSetupProgressBar.isVisible= true
            viewModel.setInvestorData(investorData)
        }

    // Image Chooser Dialog
    private fun showMenuDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, item ->
            when (item) {
                0 -> {
                    val permissionGranted = requestCameraPermission()
                    if (permissionGranted) {
                        openCameraInterface()
                    } }
                1 -> {
                    openImageChooser() } } }
        builder.show()
    }

    // Camera Functions
    // Permissions
    private fun requestCameraPermission(): Boolean {
        var permissionGranted = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraPermissionNotGranted = checkSelfPermission(
                activity as Context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
            if (cameraPermissionNotGranted) {
                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, CAMERA_PERMISSION_CODE)
            } else {
                permissionGranted = true
            }
        } else {
            permissionGranted = true
        }
        return permissionGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                openCameraInterface()
            } else {
                // Permission was denied
                showAlert("Camera permission was denied. Unable to take a picture.")
            }
        }
    }
    // Opening Camera
    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, R.string.take_picture)
        values.put(MediaStore.Images.Media.DESCRIPTION, R.string.take_picture_description)
        selectedImage = requireActivity().contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        // Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage)
        // Launch intent
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok, null)
        val dialog = builder.create()
        dialog.show()
    }


    // Gallery Functions
    // Opening Gallery
    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            // TODO: Accomodate for SVG
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)

        }
    }


    private fun getImageBitmap(context: Context, selectedImage: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver,
                        selectedImage))
            } else {
                context
                    .contentResolver
                    .openInputStream(selectedImage)?.use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    }

            }
    }

    private fun convertBitmapToFile(destinationFile: File, bitmap: Bitmap?) {
        CoroutineScope(Dispatchers.IO).launch {
        //create a file to write bitmap data
        destinationFile.createNewFile()
        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData = bos.toByteArray()
        //write the bytes in file
        val fos = FileOutputStream(destinationFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
    }
    }

    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        } catch (e: Exception) {
            Log.e("InvestorSetup3Fragment", "Failed to create image file: ${e.message}", e)
            null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    Log.i("cam", "REQUEST_CODE_IMAGE_PICKER")
                    selectedImage = data?.data
                    binding.ivUploadProfile.setImageURI(selectedImage)
                }
                IMAGE_CAPTURE_CODE -> {
                    Log.i("cam", "IMAGE_CAPTURE_CODE")
                    binding.ivUploadProfile.setImageURI(selectedImage)
                }
            }
            val selectedImageBitmap: Bitmap? = selectedImage?.let { getImageBitmap(requireContext(), it) }
            val selectedImageFilePath = createImageFile()

            if (selectedImageFilePath != null && selectedImageBitmap != null) {
                convertBitmapToFile(selectedImageFilePath, selectedImageBitmap)
                commonSharedPreferences.saveStringData(Constants.INVESTORPHOTO, selectedImageFilePath.toString())
            }
            else {
                showAlert("Something went wrong. Try again")
            }
        } else {
            showAlert("Failed to upload picture")
        }
    }


    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val CAMERA_PERMISSION_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 1001
    }

    override fun getViewModel() = SharedAccountSetupViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentInvestorSetup3Binding.inflate(inflater, container, false)


    override fun getFragmentRepository() = AccountSetupRepository(remoteDataSource.buildApi(AccountSetupAPI::class.java), AppDatabase.invoke(requireActivity()))
}