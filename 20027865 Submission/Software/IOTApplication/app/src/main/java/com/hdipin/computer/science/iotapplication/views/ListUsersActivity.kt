package com.hdipin.computer.science.iotapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hdipin.computer.science.iotapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.hdipin.computer.science.iotapplication.adapter.MyAdapter
import com.hdipin.computer.science.iotapplication.models.UserModel

private lateinit var dbref : DatabaseReference
private lateinit var userRecyclerview : RecyclerView
private lateinit var userArrayList : ArrayList<UserModel>

class ListUsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<UserModel>()
        getUserData()
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Users")
            dbref.addValueEventListener(object : ValueEventListener{

                 override fun onDataChange(snapshot: DataSnapshot) {
                     if (snapshot.exists()){
                         for (userSnapshot in snapshot.children){

                             val user = userSnapshot.getValue(UserModel::class.java)
                             userArrayList.add(user!!)

                         }

                         userRecyclerview.adapter = MyAdapter(userArrayList)
                     }
                 }
                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }


            })

        }
}