package com.hdipin.computer.science.application.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.hdipin.computer.science.application.R
import kotlinx.android.synthetic.main.activity_register.*

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hdipin.computer.science.application.activities.BaseActivity

@Suppress("DEPRECATION")
class RegisterActivity : BaseActivity() {

    private lateinit var database : DatabaseReference

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_register)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //setupActionBar()

        btn_register.setOnClickListener {

             registerUser()
        }

        // START
        tv_login.setOnClickListener {
            // Here when the user click on login text we can either call the login activity or call the onBackPressed function.
            // We will call the onBackPressed function.
            onBackPressed()
        }
    }

    /**
     * A function for actionBar Setup.
     */

    private fun setupActionBar() {

        setSupportActionBar(toolbar_register_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
           // TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
          //      showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
          //      false
        //    }

          //  TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
            //    showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
          //      false
         //   }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to register the user with email and password using FirebaseAuth.
     */
    private fun registerUser() {
        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))
            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }


            addUserToDatabase(email, password)
            // Create an instance and create a register a user with email and password.
       }
    }

    private fun addUserToDatabase(email: String, password: String){

        hideProgressDialog()
        val user = User(email,password)
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(email).setValue(user).addOnSuccessListener {

        showErrorSnackBar("Successfully Saved", true)

        }.addOnFailureListener{
            showErrorSnackBar("Failed", false)

        }


    }


}

