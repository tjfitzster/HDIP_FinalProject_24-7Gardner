package com.hdipin.computer.science.iotapplication.views

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.databinding.ActivityUserProfileBinding
import com.hdipin.computer.science.iotapplication.models.userProfile


class UserProfileActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("userName")
        binding.etUsername.setText(userName.toString())

        binding.etUsername.isEnabled = false
        binding.etUsername.setTypeface(null, Typeface.BOLD)
        binding.etUsername.gravity = Gravity.CENTER_HORIZONTAL;
        readData(userName.toString())
        binding.btnSave.setOnClickListener(){

            writeData(binding.etUsername.text.toString())
            showErrorSnackBar("User Successfully Updated", false)

        finish()

        }

    }

    private fun readData(userName: String){

        database = FirebaseDatabase.getInstance().getReference("UserProfile")
        database.child(userName).get().addOnSuccessListener {

            if (it.exists()){
                binding.etFirstName.setText(it.child("firstName").value.toString())
                binding.etLastName.setText(it.child("secondname").value.toString())
                binding.etMobile.setText(it.child("mobileNo").value.toString())
            }

        }
    }

    private fun writeData(userName: String){

        val userProfile = userProfile(binding.etUsername.text.toString(),
        binding.etFirstName.text.toString(),
        binding.etLastName.text.toString(),
        binding.etMobile.text.toString())

        database = FirebaseDatabase.getInstance().getReference("UserProfile")
        database.child(userName).setValue(userProfile).addOnSuccessListener {



        }
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