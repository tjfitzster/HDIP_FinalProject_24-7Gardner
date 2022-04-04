package com.hdipin.computer.science.iotapplication.views

import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.database.DatabaseReference
import com.hdipin.computer.science.iotapplication.R
import com.google.firebase.database.*
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.models.GardenModel
import kotlinx.android.synthetic.main.activity_add_garden.*


class AddGardenActivity : BaseActivity() {

    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_garden)
        val userName = intent.getStringExtra("userName")
        btn_submit.setOnClickListener {
            showErrorSnackBar("Clicked", true)
            if (userName != null) {
                registerGarden(userName)
            }
        }
    }

    private fun validateGardenDetails(): Boolean {
        return when {

            TextUtils.isEmpty(et_Garden_title.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            TextUtils.isEmpty(et_Garden_location.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }
            TextUtils.isEmpty(et_Garden_description.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            else -> {
                true
            }
        }
    }


    private fun addGardentoDatabase(username: String, gardenTitle: String, gardenLocation: String, description: String) {
        val key: String? = FirebaseDatabase.getInstance().getReference("GardenHeader").push().key
        val user = GardenModel(username, gardenTitle, gardenLocation, description, key)

        database = FirebaseDatabase.getInstance().getReference("GardenHeader")

                database.child(key.toString()).setValue(user).addOnSuccessListener {
                    hideProgressDialog()
                    showErrorSnackBar("Garden Successfully Created", true)
                }.addOnFailureListener {
                    showErrorSnackBar("There was an error with the database", false)
                }
        finish()
    }

    private fun registerGarden(username: String,) {
        // Check with validate function if the entries are valid or not.
        // Show the progress dialog.
        if (validateGardenDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))
            val gardenTitle: String = et_Garden_title.text.toString().trim { it <= ' ' }
            val gardenLocation: String = et_Garden_location.text.toString().trim { it <= ' ' }
            val description: String = et_Garden_description.text.toString().trim { it <= ' ' }

            //generateanidifnot
            addGardentoDatabase(username, gardenTitle, gardenLocation,description)
            // Create an instance and create a register a user with email and password.

        }
    }
}
