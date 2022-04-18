package com.hdipin.computer.science.datepickertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OnClickTime()
    }

    private fun OnClickTime() {
        val textView = findViewById<TextView>(R.id.textView)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { view, hour, minute ->
            var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (textView != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "Time is: $hour : $min $am_pm"
                textView.text = msg
                textView.visibility = ViewGroup.VISIBLE
            }
        }
        //  val datePicker = findViewById<DatePicker>(R.id.date_Picker)
        //  val today = Calendar.getInstance()
        //  datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
        //     today.get(Calendar.DAY_OF_MONTH)

        //    ) { view, year, month, day ->
        //      val month = month + 1
        //     val msg = "You Selected: $day/$month/$year"
        //      Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        //  }

    }
}