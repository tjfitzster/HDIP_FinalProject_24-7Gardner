package com.hdipin.computer.science.application.activities


import android.graphics.Typeface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity

import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hdipin.computer.science.application.R
import com.hdipin.computer.science.application.databinding.ActivityUserProfileBinding


class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("UserId")
        binding.etUserID.setText("User Id: " + id)
        binding.etUserID.isEnabled = false
        binding.etUserID.setTypeface(null, Typeface.BOLD)
        binding.etUserID.gravity = Gravity.CENTER_HORIZONTAL;

        binding.btnSave.setOnClickListener(){

            val userName = binding.etUsername.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val mobile = binding.etMobile.text.toString()

         //   val User = User(userName, firstName,lastName,mobile)
            val User = User(userName, firstName)
            database = FirebaseDatabase.getInstance().getReference("Users")

            database.child(userName).setValue(User).addOnSuccessListener {
                binding.etUsername.text.clear()
                binding.etFirstName.text.clear()
                binding.etLastName.text.clear()
                binding.etMobile.text.clear()
                showErrorSnackBar("Successfully Saved", false)
            }.addOnFailureListener{
                showErrorSnackBar("Not Added", true)
            }


        }
    }


    private fun updateData(id: String, userName: String, firstName: String, lastName: String, mobile: String) {
        showErrorSnackBar("Button Pressed", false)
      //  database = FirebaseDatabase.getInstance().getReference("Users")

        val user = mapOf<String,String>(
            "Id" to id,
            "firstName" to firstName,
            "lastName" to lastName,
            "age" to mobile
        )

      //  database.child(userName).updateChildren(user).addOnSuccessListener {

         //   binding.etUsername.text.clear()
        //    binding.etFirstName.text.clear()
        //    binding.etLastName.text.clear()
      //      binding.etMobile.text.clear()
      //      showErrorSnackBar("Successfully Saved", false)


       // }.addOnFailureListener{

       //     showErrorSnackBar("Failed to save", true)
//
      //  }
        }

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@UserProfileActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@UserProfileActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }

}