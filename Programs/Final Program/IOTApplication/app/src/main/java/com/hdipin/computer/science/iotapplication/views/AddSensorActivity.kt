package com.hdipin.computer.science.iotapplication.views

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.database.*
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.databinding.ActivityAddSensorBinding
import com.hdipin.computer.science.iotapplication.models.GardenModel
import com.hdipin.computer.science.iotapplication.models.SensorModel
import kotlinx.android.synthetic.main.activity_add_sensor.*
import android.widget.Toast



class AddSensorActivity : BaseActivity() {

    private lateinit var binding: ActivityAddSensorBinding
    private lateinit var database : DatabaseReference
    private lateinit var gardenArrayList : ArrayList<GardenModel>
    val gardenList: MutableList<String?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  binding.etGardenName.isEnabled = false
        val userName = intent.getStringExtra("userName")
      //  binding.etGardenName.setText("$userName")
        //binding.etGardenName.setText("-Mz5WlbtYe-TBfB2EFPR")
     //   binding.etGardenName.setTypeface(null, Typeface.BOLD)

        gardenArrayList = arrayListOf<GardenModel>()
        generateUsergardenids()




        btn_save.setOnClickListener {
            if(validateSensorDetails()) {
                registerSensor()
            }

        }
    }

    private fun generateUsergardenids(): Boolean {

        database = FirebaseDatabase.getInstance().getReference("GardenHeader")
        val usergardenids = arrayOf<String>()
        val userName = intent.getStringExtra("userName")
       database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val garden = userSnapshot.getValue(GardenModel::class.java)
                       gardenArrayList.add(garden!!)
                    }
                  //  userRecyclerview.adapter = MyAdapter(userArrayList)
                }

                for (i in gardenArrayList.indices) {
                    if (gardenArrayList[i].username.toString() == userName)
                    {
                        gardenList.add(gardenArrayList[i].gid)
                    }
                }
                populateSpinner(gardenList as ArrayList<String?>)

           }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


       return true
    }

    private fun populateSpinner(gardenIds: ArrayList<String?>){

       // showErrorSnackBar(gardenIds.size.toString(), true)
        val spinner = findViewById<Spinner>(R.id.et_GardenName)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, gardenIds)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
            //        Toast.makeText(applicationContext,"Garden: " + gardenIds[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }

    private fun validateSensorDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_SensorName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_senname), true)
                false
            }
            TextUtils.isEmpty(et_sensorService.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_senname),
                    true
                )
                false
            }
            TextUtils.isEmpty(et_GardenName.selectedItem.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_senname),
                    true
                )
                false
            }
            TextUtils.isEmpty(et_SensorPinNo.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_pinno_garden),
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
            val gardenNo: String = et_GardenName.selectedItem.toString().trim { it <= ' ' }
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