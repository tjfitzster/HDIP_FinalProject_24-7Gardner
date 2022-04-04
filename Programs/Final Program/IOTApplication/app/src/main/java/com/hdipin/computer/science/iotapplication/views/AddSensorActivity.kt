package com.hdipin.computer.science.iotapplication.views

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.databinding.ActivityAddSensorBinding
import com.hdipin.computer.science.iotapplication.models.SensorModel
import kotlinx.android.synthetic.main.activity_add_sensor.*


class AddSensorActivity : BaseActivity() {

    private lateinit var binding: ActivityAddSensorBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSensorBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.etGardenName.isEnabled = false
        binding.etGardenName.setText("-Mz5WlbtYe-TBfB2EFPR")
        binding.etGardenName.setTypeface(null, Typeface.BOLD)

        btn_save.setOnClickListener {
            showErrorSnackBar("Clicked", true)
            if(validateSensorDetails()) {
                registerSensor()
            }

        }
    }

    private fun validateSensorDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_SensorName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            TextUtils.isEmpty(et_sensorService.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }
            TextUtils.isEmpty(et_GardenName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }
            TextUtils.isEmpty(et_SensorPinNo.text.toString().trim { it <= ' ' }) -> {
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


    private fun registerSensor() {
        // Check with validate function if the entries are valid or not.
        // Show the progress dialog.
        if (validateSensorDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))
            val sensorName: String = et_SensorName.text.toString().trim { it <= ' ' }
            val sensorDate: String = et_sensorService.text.toString().trim { it <= ' ' }
            val sensorPinNo: String = et_SensorPinNo.text.toString().trim { it <= ' ' }
            val gardenNo: String = et_GardenName.text.toString().trim { it <= ' ' }
            var sensorType = 0

            sensorType = if(DHT22.isChecked){
                1 // DHT22
            } else if(AirQuality.isChecked){
                2 // MQ12
            } else if(Moisture.isChecked){
                3 // MOISTURE
            } else{
                4 // PUMP
            }

           addSensortoDatabase(sensorName, sensorDate, sensorType,sensorPinNo, gardenNo)
            // Create an instance and create a register a user with email and password.
        }
    }

    private fun addSensortoDatabase(sensorName: String, sensorDate: String, sensorType: Int, sensorPinNo: String,  gardenNo: String) {
        val key: String? = FirebaseDatabase.getInstance().getReference("Devices").push().key
        val sensor = SensorModel(sensorName, sensorDate, sensorType, sensorPinNo, gardenNo, key)

        database = FirebaseDatabase.getInstance().getReference("Devices")
        database.child(key.toString()).setValue(sensor).addOnSuccessListener {
            hideProgressDialog()
           showErrorSnackBar("Sensor Successfully Added", true)
        }.addOnFailureListener {
           showErrorSnackBar("There was an error with the database", false)
        }
        finish()
    }


}