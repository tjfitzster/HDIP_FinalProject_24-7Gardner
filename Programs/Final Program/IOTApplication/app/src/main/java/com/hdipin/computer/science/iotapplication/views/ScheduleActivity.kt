package com.hdipin.computer.science.iotapplication.views


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.DatePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.activities.BaseActivity
import com.hdipin.computer.science.iotapplication.models.ScheduleModel
import kotlinx.android.synthetic.main.activity_add_sensor.btn_save
import kotlinx.android.synthetic.main.activity_schedule.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ScheduleActivity : BaseActivity() {
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        val datePicker = findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        val dateSelected = ""

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
        { view, year, month, day ->
            val month = month + 1
            val dateSelected = "$day/$month/$year"
        }

        btn_save.setOnClickListener {
            if(validateScheduleDetails()) {
                val dateTime = datePicker.calendarView.date
                val date = Date(dateTime)
                val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val formattedDate: String = dateFormat.format(date)
                addScheduletoDatabase(et_deviceName.text.toString(), formattedDate, et_turnonTime.text.toString())
            }
        }
    }

    private fun validateScheduleDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_deviceName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_mdevname), true)
                false
            }
            TextUtils.isEmpty(et_turnonTime.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_devtime),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    private fun addScheduletoDatabase(deviceID: String, turnOnDate: String, turnOnTime: String) {
        val scheduleid: String? = FirebaseDatabase.getInstance().getReference("PumpSchedule").push().key

        val schedule = ScheduleModel(deviceID, turnOnDate, turnOnTime, scheduleid)

        database = FirebaseDatabase.getInstance().getReference("PumpSchedule")
        database.child(scheduleid.toString()).setValue(schedule).addOnSuccessListener {
            hideProgressDialog()
            showErrorSnackBar("Schedule Successfully Added", true)
        }.addOnFailureListener {
            showErrorSnackBar("There was an error with the database", false)
        }

        val intent = Intent(this@ScheduleActivity, MainActivity::class.java)
        startActivity(intent)
    }



}