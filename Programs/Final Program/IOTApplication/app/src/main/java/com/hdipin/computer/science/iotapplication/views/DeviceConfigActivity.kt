package com.hdipin.computer.science.iotapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.models.SensorModel
import com.hdipin.computer.science.iotapplication.models.configModel
import kotlinx.android.synthetic.main.activity_add_sensor.*
import kotlinx.android.synthetic.main.activity_config_dev.*
import kotlinx.android.synthetic.main.activity_config_dev.btn_save
import kotlinx.android.synthetic.main.activity_register.*

class DeviceConfigActivity : BaseActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_dev)


        btn_save.setOnClickListener {

            saveUserSettings()
        }

    }

    private fun saveUserSettings() {

        // Check with validate function if the entries are valid or not.
        if (validateUserDetails()) {
            // Show the progress dialog.
           showProgressDialog(resources.getString(R.string.please_wait))
            val frequency: String = et_sample_Frequency.text.toString().trim { it <= ' ' }
            var executeProgram: String = "False"
            var uploadConfigurationFile: String = "False"
            var writeConfigurationFile: String = "False"

           executeProgram = if(exeProgramTrue.isChecked){
                "True"
            } else{
               "False" }

            uploadConfigurationFile = if(UploadConfigTrue.isChecked){
                "True"
            } else{
                "False" }

            writeConfigurationFile = if(WriteConfigTrue.isChecked){
                "True"
           } else{
                "False" }
            updatesettingsindatabase(frequency, executeProgram, uploadConfigurationFile, writeConfigurationFile)

       }
    }

    private fun validateUserDetails(): Boolean {

        return when {
            TextUtils.isEmpty(et_sample_Frequency.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.validfequenct), true)
                false
        }
        else -> {
            true
        }
    }
}


    private fun updatesettingsindatabase(frequency: String, executeProgram: String, uploadConfigurationFile: String,  writeConfigurationFile: String) {

        val configuration = configModel(frequency, executeProgram, uploadConfigurationFile, writeConfigurationFile)

        database = FirebaseDatabase.getInstance().getReference("Configuration")


        database.setValue(configuration).addOnSuccessListener {


            hideProgressDialog()
            showErrorSnackBar("Configuration Successfully Updated", true)
       }.addOnFailureListener {
           showErrorSnackBar("There was an error with the database", false)
        }
        finish()
    }

}