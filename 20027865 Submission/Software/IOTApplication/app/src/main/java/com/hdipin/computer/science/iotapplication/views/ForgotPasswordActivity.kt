package com.hdipin.computer.science.iotapplication.views



import android.os.Bundle
import android.text.TextUtils
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.databinding.ActivityForgotPasswordBinding
import kotlinx.android.synthetic.main.activity_forgot_password.*


class ForgotPasswordActivity  : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener() {
            resetPassword()
        }
    }

    private fun resetPassword() {
        // Check with validate function if the entries are valid or not.
        if (!validateemail()) {
            // Show the progress dialog.
            showErrorSnackBar("Please enter an email", true)
        }
        else{
            showErrorSnackBar("Reset Email has Been Sent If User Is Registered", false)
        }
    }

    private fun validateemail(): Boolean {

        return when {

            TextUtils.isEmpty(et_forgot_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_confirm_email),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }
}