package com.hdipin.computer.science.iotapplication.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.models.LoginModel
import java.time.LocalDateTime

@Suppress("DEPRECATION")
class LoginActivity : BaseActivity(), View.OnClickListener {
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {


        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Click event assigned to Forgot Password text.
        tv_forgot_password.setOnClickListener(this)
        // Click event assigned to Login button.
        btn_login.setOnClickListener(this)
        // Click event assigned to Register text.
        tv_register.setOnClickListener(this)
    }
    /**
     * In Login screen the clickable components are Login Button, ForgotPassword text and Register Text.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {
                    // START
                    // Launch the forgot password screen when the user clicks on the forgot password text.
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                    // END
                }

                R.id.btn_login -> {
                    logInRegisteredUser()
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                   startActivity(intent)
                }
            }
        }
    }
    /**
     * A function to validate the login entries of a user.
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_login_username.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }
    /**
     * A function to Log-In. The user will be able to log in using the registered email and password with Firebase Authentication.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val username = et_login_username.text.toString().trim { it <= ' ' }
            val password = et_login_password.text.toString().trim { it <= ' ' }


            readData(username, password)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun readData(userName: String, password: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userName).get().addOnSuccessListener {

            if (it.exists()){

                val readusername = it.child("username").value
                val readpassword = it.child("password").value

                    if((readusername?.equals(userName) == true) &&  (readpassword?.equals(password) == true)){
                        showErrorSnackBar("User Logged In", false)
                        hideProgressDialog()

                        updateLoginTable(userName)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("Username", userName)
                        startActivity(intent)
                    }
            }else{

                showErrorSnackBar("Incorrect Username or Password", true)
                hideProgressDialog()
            }
        }.addOnFailureListener{
            showErrorSnackBar("Incorrect Username or password", true)
        }

        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun updateLoginTable(userName: String){

            val currentTimestamp = System.currentTimeMillis()

            val login = LoginModel(userName,LocalDateTime.now().toString())
            val key: String? = database.push().key

            database = FirebaseDatabase.getInstance().getReference("loginTable")
            database.child(key.toString()).setValue(login).addOnSuccessListener {

                hideProgressDialog()


            }.addOnFailureListener{
               showErrorSnackBar("There was an issue updating the login table", true)

            }

        }

    }