package com.hdipin.computer.science.iotapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hdipin.computer.science.iotapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.hdipin.computer.science.iotapplication.adapter.MyGardensAdapter
import com.hdipin.computer.science.iotapplication.models.ListGardensModel

private lateinit var dbref : DatabaseReference
private lateinit var gardenRecyclerview : RecyclerView
private lateinit var gardenArrayList : ArrayList<ListGardensModel>

class ListgardensActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gardenlist)
        gardenRecyclerview = findViewById(R.id.gardenList)
        gardenRecyclerview.layoutManager = LinearLayoutManager(this)
        gardenRecyclerview.setHasFixedSize(true)
        gardenArrayList = arrayListOf<ListGardensModel>()
        getGardenData()
    }
    private fun getGardenData() {
        dbref = FirebaseDatabase.getInstance().getReference("GardenHeader")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val garden = userSnapshot.getValue(ListGardensModel::class.java)
                        gardenArrayList.add(garden!!)
                    }
                    gardenRecyclerview.adapter = MyGardensAdapter(gardenArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

}