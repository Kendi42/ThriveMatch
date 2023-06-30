package com.example.thrivematch.ui.account_setup

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.databinding.FragmentInvestorSetup3Binding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants

class InvestorSetup3Fragment : Fragment(R.layout.fragment_investor_setup3) {

    private lateinit var commonSharedPreferences: CommonSharedPreferences
    private val sharedViewModel: SharedAccountSetupViewModel by activityViewModels()
    private var selectedImage: Uri? = null
    private lateinit var binding: FragmentInvestorSetup3Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInvestorSetup3Binding.bind(view)


        commonSharedPreferences = CommonSharedPreferences(requireContext())

        //Progress Bar
        binding.progressBarInvestor3.progress = 100

        //Navigation Buttons
        binding.btnBackInvestor3.setOnClickListener {
            findNavController().navigate(R.id.action_investorSetup3Fragment_to_investorSetup2Fragment)
        }
        binding.btnFinishInvestor3.setOnClickListener {
            submitInvestorData()
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }

        // Image View Data Persistence
        if(commonSharedPreferences.getStringData(Constants.INVESTORPHOTO) != ""){
            try {
                val decodedBytes = Base64.decode(
                    commonSharedPreferences.getStringData(Constants.INVESTORPHOTO),
                    Base64.DEFAULT
                )
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                binding.ivUploadProfile.setImageBitmap(bitmap)
            }catch(e:Exception){
                Log.e("ImageConversion", "Failed to decode Base64 to image: ${e.message}", e)
            }
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
            sharedViewModel.setInvestorData(investorData)
        }

    private fun showMenuDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, item ->
            when (item) {
                0 -> {
//                    Toast.makeText(requireContext(), "Camera Chosen", Toast.LENGTH_SHORT).show()
                    val permissionGranted = requestCameraPermission()
                    if (permissionGranted) {
                        openCameraInterface()
                    } }
                1 -> {
                    openImageChooser() } } }
        builder.show()
    }

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

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok, null)
        val dialog = builder.create()
        dialog.show()
    }

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


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            // TODO: Accomodate for SVG
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)

        }
    }

    private fun convertImageToBase64(imageUri: Uri): String? {
        try {
            val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            return bytes?.let { Base64.encodeToString(it, Base64.DEFAULT) }
        } catch (e: Exception) {
            Log.e("ImageConversion", "Failed to convert image to Base64: ${e.message}", e)
        }
        return null
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
            // Saving Photo as Bit 64 code
            commonSharedPreferences.saveStringData(Constants.INVESTORPHOTO, selectedImage?.let {convertImageToBase64(it) }.toString() )


        } else {
            showAlert("Failed to upload picture")
        }
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val CAMERA_PERMISSION_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 1001
    }
}