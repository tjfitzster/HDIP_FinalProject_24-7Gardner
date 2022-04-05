package com.hdipin.computer.science.iotapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hdipin.computer.science.iotapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.hdipin.computer.science.iotapplication.adapter.MySensorAdapter
import com.hdipin.computer.science.iotapplication.models.listSensorModel


private lateinit var dbref : DatabaseReference
private lateinit var sensorRecyclerview : RecyclerView
private lateinit var sensorArrayList : ArrayList<listSensorModel>

class ListSensorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensorlist)
        sensorRecyclerview = findViewById(R.id.sensorList)
        sensorRecyclerview.layoutManager = LinearLayoutManager(this)
        sensorRecyclerview.setHasFixedSize(true)
        sensorArrayList = arrayListOf<listSensorModel>()
        getSensorData()
    }
    private fun getSensorData() {

        dbref = FirebaseDatabase.getInstance().getReference("SensorMeasurments")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val sensor = userSnapshot.getValue(listSensorModel::class.java)
                        sensorArrayList.add(sensor!!)

                    }

                    sensorRecyclerview.adapter = MySensorAdapter(sensorArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
