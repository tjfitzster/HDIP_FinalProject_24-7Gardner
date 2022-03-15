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

        if (intent.hasExtra("STRING_I_NEED")) {
            // Get the user details from intent as a ParcelableExtra.
            val email = intent.getStringExtra("STRING_I_NEED")!!
            binding.emailView.text = email
        }

        binding.btnViewGardens.setOnClickListener() {
            Toast.makeText(this,"View Gardens Button Pressed",Toast.LENGTH_SHORT).show()
        }
        binding.btnUserSettings.setOnClickListener(){
            Toast.makeText(this,"User Settings Button Pressed",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
            intent.putExtra("STRING_I_NEED", binding.emailView.text)
            startActivity(intent)
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