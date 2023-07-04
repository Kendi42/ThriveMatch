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
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.databinding.FragmentBusinessSetup3Binding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class BusinessSetup3Fragment : Fragment(R.layout.fragment_business_setup3) {
    private var selectedImage: Uri? =null
    private lateinit var binding:FragmentBusinessSetup3Binding
    private val sharedViewModel: SharedAccountSetupViewModel by activityViewModels()
    private lateinit var commonSharedPreferences: CommonSharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBusinessSetup3Binding.bind(view)
        commonSharedPreferences = CommonSharedPreferences(requireContext())


        //Progress Bar
        binding.progressBarBusiness3.progress= 100

        // Navigation Buttons
        binding.btnBackBusiness3.setOnClickListener {
            findNavController().navigate(R.id.action_businessSetup3Fragment_to_businessSetup2Fragment)
        }
        binding.btnFinishBusiness3.setOnClickListener {
            submitBusinessData()
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }

        // Image View Data Persistence
        if(commonSharedPreferences.getStringData(Constants.BUSINESSPHOTO) != ""){
            val imagePath = commonSharedPreferences.getStringData(Constants.BUSINESSPHOTO)
            val imageFile = File(imagePath)
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            binding.ivUploadLogo.setImageBitmap(bitmap)
        }
        // Image Upload
        binding.ivUploadLogo.setOnClickListener{
            showMenuDialog()
        }


    }

    private fun submitBusinessData() {
        val businessData = BusinessModel(
            businessName=commonSharedPreferences.getStringData(Constants.BUSINESSNAME),
            industry= commonSharedPreferences.getStringData(Constants.INDUSTRY),
            dateFounded= commonSharedPreferences.getStringData(Constants.DATEFOUNDED),
            companyDescription= commonSharedPreferences.getStringData(Constants.COMPANYDESCRIPTION),
            phoneNumber= commonSharedPreferences.getStringData(Constants.BUSINESSPHONE),
            email= commonSharedPreferences.getStringData(Constants.BUSINESSEMAIL),
            address= commonSharedPreferences.getStringData(Constants.BUSINESSADDRESS),
            poBox= commonSharedPreferences.getStringData(Constants.BUSINESSPOBOX),
            photo= commonSharedPreferences.getStringData(Constants.BUSINESSPHOTO)
        )

        sharedViewModel.setBusinessData(businessData)
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
                    if(permissionGranted){
                        openCameraInterface()
                    }
                }
                1 -> {
                    openImageChooser()
                }

            }
        }
        builder.show()

    }

    // Taking Picture
    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, R.string.take_picture)
        values.put(MediaStore.Images.Media.DESCRIPTION, R.string.take_picture_description)
        selectedImage = requireActivity().contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        // Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage)
        // Launch intent
        startActivityForResult(intent, BusinessSetup3Fragment.IMAGE_CAPTURE_CODE)

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == BusinessSetup3Fragment.CAMERA_PERMISSION_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Permission was granted
                openCameraInterface()
            }
            else{
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
        var permissionGranted= false

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val cameraPermissionNotGranted = ContextCompat.checkSelfPermission(
                activity as Context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
            if(cameraPermissionNotGranted){
                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, BusinessSetup3Fragment.CAMERA_PERMISSION_CODE)
            }
            else{
                permissionGranted = true
            }
        }
        else{
            permissionGranted = true
        }
        return permissionGranted
    }

    // Gallery Option
    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also{
            it.type = "image/*"
            // TODO: Accomodate for SVG
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, BusinessSetup3Fragment.REQUEST_CODE_IMAGE_PICKER)

        }
    }

    // URI to file
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

    // On Activity Result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                BusinessSetup3Fragment.REQUEST_CODE_IMAGE_PICKER -> {
                    Log.e("cam", "REQUEST_CODE_IMAGE_PICKER")
                    selectedImage = data?.data
                    binding.ivUploadLogo.setImageURI(selectedImage)
                }
                BusinessSetup3Fragment.IMAGE_CAPTURE_CODE -> {
                    Log.e("cam", "IMAGE_CAPTURE_CODE")
                    binding.ivUploadLogo.setImageURI(selectedImage)
                }
            }
            val selectedImageBitmap: Bitmap? = selectedImage?.let { getImageBitmap(requireContext(), it) }
            val selectedImageFilePath = createImageFile()
            if (selectedImageFilePath != null && selectedImageBitmap != null) {
                convertBitmapToFile(selectedImageFilePath, selectedImageBitmap)
                commonSharedPreferences.saveStringData(Constants.BUSINESSPHOTO, selectedImageFilePath.toString())
            }
            else {
                showAlert("Something went wrong. Try again")
            }
        } else {
            showAlert("Failed to upload picture")
        }
    }

    companion object{
        private const val REQUEST_CODE_IMAGE_PICKER= 100
        private const val CAMERA_PERMISSION_CODE = 1000
        private const val IMAGE_CAPTURE_CODE = 1001
    }

}