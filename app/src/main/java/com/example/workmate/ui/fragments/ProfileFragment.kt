package com.example.workmate.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.databinding.FragmentHomeBinding
import com.example.workmate.databinding.FragmentProfileBinding
import com.example.workmate.entity.User
import com.example.workmate.repository.UserRepository
import com.example.workmate.ui.ChangePasswordActivity
import com.example.workmate.ui.JobActivity
import com.example.workmate.ui.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private var userId: String? = null

    private var imageName: String = "";
    private var requestGalleryCode = 0
    private var requestCameraCode = 1
    private var imageUrl: String? = null

    private val arrayExtra = arrayListOf<String>(
        "Select Options",
        "My Jobs",
        "Change Password",
        "Log Out"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding?.root
        if (binding != null) {
            binding?.spinner?.adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    arrayExtra
                )
            binding?.spinner?.setSelection(0, false)
            binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    when (selectedItem) {
                        "Change Password" -> {
                            startActivity(
                                Intent(
                                    requireContext(),
                                    ChangePasswordActivity::class.java
                                )
                            )

                        }
                        "My Jobs" -> {
                            startActivity(
                                Intent(
                                    requireContext(),
                                    JobActivity::class.java
                                )
                            )

                        }
                        "Log Out" -> {
                            doLogout()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }

        loadProfile()
        binding?.apply {
            btnUpdate.setOnClickListener {
                updateProfile()
                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
            }
        }
        binding?.photo?.setOnClickListener {
            loadPopUpMenu()
        }

        return view
    }


    override fun onResume() {
        super.onResume()
        loadProfile()
        binding?.spinner?.setSelection(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.spinner?.setSelection(0)
    }

    private fun doLogout() {

        val sharedPref =
            context?.getSharedPreferences("loginSharedPref", AppCompatActivity.MODE_PRIVATE);
        sharedPref?.edit()?.clear()?.commit()
        context?.startActivity(Intent(context, LoginActivity::class.java))

    }

    private fun loadProfile() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepository()
                val response = userRepo.profile()
                if (response.success == true) {
                    withContext(Main) {
                        binding?.apply {
                            etEmail.setText(response.data!!.email);
                            etFullname.setText(response.data.name);
                            etPhoneNo.setText(response.data.phone);

                            val imagePath = ServiceBuilder.loadFilePath() + response?.data?.photo
                            Glide.with(requireContext())
                                .load(imagePath)
                                .into(binding!!.photo)

                        }
                        userId = response.data!!._id;
                    }
                }
            } catch (ex: IOException) {
                withContext(Main) {
                    Toast.makeText(activity, ex.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun updateProfile() {

        var name = ""
        var email = ""
        var phone = ""
        binding?.apply {
            name = etFullname.text.toString()
            email = etEmail.text.toString()
            phone = etPhoneNo.text.toString()
        }
        val user = User(
            name = name,
            email = email,
            phone = phone
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepository()
                val response = userRepo.update(user)

            } catch (ex: IOException) {
                withContext(Main) {
                    Toast.makeText(activity, ex.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }

    }


    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(context, binding?.photo)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuGallery ->
                    openGallery()
                R.id.menuCamera ->
                    openCamera()
            }
            true
        }
        popupMenu.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestGalleryCode)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, requestCameraCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestGalleryCode && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = context?.contentResolver
                val cursor =
                    contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)

                binding?.photo?.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == requestCameraCode && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                binding?.photo?.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
            uploadImage(ServiceBuilder.LoggedInUser.toString())
        }
    }

    private fun uploadImage(id: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)

            val body =
                MultipartBody.Part.createFormData("photo", file.name, reqFile)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepository()
                    val response = userRepo.updateImage(userId.toString(), body)
                    if (response.success == true) {
                        withContext(Main) {
                            Toast.makeText(
                                context,
                                "Image Uploaded",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } catch (ex: IOException) {
                    withContext(Main) {
                        Log.d("Error Uploading Image ", ex.message.toString())
                        Toast.makeText(
                            context,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}