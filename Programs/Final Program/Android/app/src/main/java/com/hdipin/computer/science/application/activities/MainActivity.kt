package com.hdipin.computer.science.application.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hdipin.computer.science.application.databinding.ActivityMainBinding
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnViewGardens.setOnClickListener() {
            Toast.makeText(this,"View Gardens Button Pressed",Toast.LENGTH_SHORT).show()
        }
        binding.btnUserSettings.setOnClickListener(){
            Toast.makeText(this,"User Settings Button Pressed",Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            //Toast.makeText(this,"Logout Button Pressed",Toast.LENGTH_SHORT).show()

        }


    }
}