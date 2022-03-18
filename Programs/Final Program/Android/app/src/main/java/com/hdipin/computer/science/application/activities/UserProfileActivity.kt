package com.hdipin.computer.science.application.activities


import android.graphics.Typeface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity

import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hdipin.computer.science.application.R
import com.hdipin.computer.science.application.databinding.ActivityUserProfileBinding
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

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

            showErrorSnackBar("$id", false)
       
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