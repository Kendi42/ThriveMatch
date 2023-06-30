package com.example.thrivematch.util
//
//class TestCkass {
//
//    class InvestorSetup3Fragment : Fragment(R.layout.fragment_investor_setup3) {
//
//        private lateinit var commonSharedPreferences: CommonSharedPreferences
//        private val sharedViewModelt: SharedViewModelTeToStoreImageData by activityViewModels()
//        private  var investorType: String  = ""
//        private lateinit var investorName: String
//        private lateinit var investorDescription: String
//        private lateinit var selectedInterests: MutableList<String>
//
//
//        private var selectedImage: Uri? = null
//        private lateinit var binding: FragmentInvestorSetup3Binding
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//
//        }
//        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//            binding = FragmentInvestorSetup3Binding.bind(view)
//
//            commonSharedPreferences = CommonSharedPreferences(requireContext())
//
//            fetchData()
//
//
//            //Progress Bar
//            binding.progressBarInvestor3.progress = 100
//
//            binding.btnBackInvestor3.setOnClickListener {
//                findNavController().navigate(R.id.action_investorSetup3Fragment_to_investorSetup2Fragment)
//            }
//            binding.btnFinishInvestor3.setOnClickListener {
////            val intent = Intent(requireContext(), HomeActivity::class.java)
////            startActivity(intent)
//                val alertDialogBuilder = AlertDialog.Builder(context)
//                alertDialogBuilder.setMessage("This is Investor Name ${investorType}") // Set the message
//                alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
//                    // Handle button click
//                    val intent = Intent(requireContext(), HomeActivity::class.java)
//                    startActivity(intent)
//                    dialog.dismiss()
//                }
//                val alertDialog = alertDialogBuilder.create()
//                alertDialog.show()
//
//            }
//
//
//            binding.ivUploadProfile.setOnClickListener {
//                showMenuDialog()
////                openImageChooser()
//
//
//            }
//
//        }
//
//        private fun fetchData() {
//            sharedViewModelt.investorType.observe(viewLifecycleOwner) { investorTypee ->
//                if (investorType != null) {
//                    investorType = investorTypee
//                } else {
//                    investorType = ""
//                }
//            }
////        investorType= commonSharedPreferences.getStringData(Constants.INVESTORTYPE)
//            investorName = commonSharedPreferences.getStringData("investor_name")
//            investorDescription = commonSharedPreferences.getStringData("investor_description")
//            selectedInterests = commonSharedPreferences.getStringArray("investor_interests")!!
//
//            Log.e(
//                "Data",
//                "Investor Name = ${investorName} \n Investor Type = ${investorType} \nInvestor Description = ${investorDescription} \nInvestor Interests = ${selectedInterests}"
//            )
//            val investorStep1 = InvestorStep1(
//                name = investorName,
//                investorType = investorType,
//                description = investorDescription,
//                selectedInterests = selectedInterests
//            )
//            sharedViewModelt.setInvestorStep1(investorStep1)
//
//            sharedViewModelt.investorStep1.observe(viewLifecycleOwner) {
//
//            }
//
//        }
//
//        private fun showMenuDialog() {
//            val options = arrayOf("Take Photo", "Choose from Gallery")
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setTitle("Select Option")
//            builder.setItems(options) { dialog, item ->
//                when (item) {
//                    0 -> {
////                    Toast.makeText(requireContext(), "Camera Chosen", Toast.LENGTH_SHORT).show()
//                        val permissionGranted = requestCameraPermission()
//                        if (permissionGranted) {
//                            openCameraInterface()
//                        }
//                    }
//                    1 -> {
//                        openImageChooser()
//                    }
//
//                }
//            }
//            builder.show()
//
//
//        }
//
//        private fun openCameraInterface() {
//            val values = ContentValues()
//            values.put(MediaStore.Images.Media.TITLE, R.string.take_picture)
//            values.put(MediaStore.Images.Media.DESCRIPTION, R.string.take_picture_description)
//            selectedImage = requireActivity().contentResolver?.insert(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                values
//            )
//            // Create camera intent
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage)
//            // Launch intent
//            startActivityForResult(intent, IMAGE_CAPTURE_CODE)
//
//        }
//
//        override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray
//        ) {
//            if (requestCode == CAMERA_PERMISSION_CODE) {
//                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission was granted
//                    openCameraInterface()
//                } else {
//                    // Permission was denied
//                    showAlert("Camera permission was denied. Unable to take a picture.")
//                }
//            }
//        }
//
//        private fun showAlert(message: String) {
//            val builder = AlertDialog.Builder(activity as Context)
//            builder.setMessage(message)
//            builder.setPositiveButton(R.string.ok, null)
//            val dialog = builder.create()
//            dialog.show()
//        }
//
//        private fun requestCameraPermission(): Boolean {
//            var permissionGranted = false
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                val cameraPermissionNotGranted = checkSelfPermission(
//                    activity as Context,
//                    Manifest.permission.CAMERA
//                ) == PackageManager.PERMISSION_DENIED
//                if (cameraPermissionNotGranted) {
//                    val permission = arrayOf(Manifest.permission.CAMERA)
//                    requestPermissions(permission, CAMERA_PERMISSION_CODE)
//                } else {
//                    permissionGranted = true
//                }
//            } else {
//                permissionGranted = true
//            }
//            return permissionGranted
//        }
//
//
//        private fun openImageChooser() {
//            Intent(Intent.ACTION_PICK).also {
//                it.type = "image/*"
//                // TODO: Accomodate for SVG
//                val mimeTypes = arrayOf("image/jpeg", "image/png")
//                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//                startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
//
//            }
//        }
//
//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            super.onActivityResult(requestCode, resultCode, data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                when (requestCode) {
//                    REQUEST_CODE_IMAGE_PICKER -> {
//                        Log.e("cam", "REQUEST_CODE_IMAGE_PICKER")
//                        selectedImage = data?.data
//                        binding.ivUploadProfile.setImageURI(selectedImage)
//                    }
//                    IMAGE_CAPTURE_CODE -> {
//                        Log.e("cam", "IMAGE_CAPTURE_CODE")
//                        binding.ivUploadProfile.setImageURI(selectedImage)
//                    }
//                }
//            } else {
//                showAlert("Failed to upload picture")
//            }
//        }
//
//        companion object {
//            private const val REQUEST_CODE_IMAGE_PICKER = 100
//            private const val CAMERA_PERMISSION_CODE = 1000
//            private const val IMAGE_CAPTURE_CODE = 1001
//        }
//    }
//
//}