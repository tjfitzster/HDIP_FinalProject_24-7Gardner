package com.hdipin.computer.science.iotapplication.views


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.databinding.ActivityForgotPasswordBinding



class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_forgot_password)
        showErrorSnackBar("ssssssssssssssss", false)

        binding.btnSubmit.setOnClickListener() {
            showErrorSnackBar("Reset Email has Been Sent If User Is Registered", false)
        }

    }

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@ForgotPasswordActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@ForgotPasswordActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }
}