package com.hdipin.computer.science.kotlinfirebasespinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    val spinner = findViewById<Spinner>(R.id.spinner)
    val edtext = findViewById<EditText>(R.id.tvsv)
    val ArrayList<String> spinnerList;

    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance().reference




    }
}