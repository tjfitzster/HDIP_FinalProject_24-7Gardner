package com.hdipin.computer.science.application.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hdipin.computer.science.application.R
import com.hdipin.computer.science.application.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getStringExtra("UserId")

        binding.btnViewGardens.setOnClickListener() {

            showErrorSnackBar("User Gardens pressed.", false)
        }
        binding.btnUserSettings.setOnClickListener(){
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
            intent.putExtra("UserId", id)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener(){
            //FirebaseAuth.getInstance().signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }



    }

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }


}