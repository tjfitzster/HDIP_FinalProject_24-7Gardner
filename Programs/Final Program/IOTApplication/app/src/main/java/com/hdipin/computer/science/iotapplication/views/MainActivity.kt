package com.hdipin.computer.science.iotapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userName = intent.getStringExtra("Username")



        binding.btnAddaGarden.setOnClickListener() {
            var userName = "tjf"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent = Intent(this@MainActivity, AddGardenActivity::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }
      //  binding.btnUserSettings.setOnClickListener {

       // }

       //   binding.btnAddaGarden.setOnClickListener {
         //     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         //     val intent = Intent(this@MainActivity, AddGardenActivity::class.java)
          //   intent.putExtra("userName", userName)
         //    startActivity(intent)
       //   }

        binding.btnListSensors.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent = Intent(this@MainActivity, ListSensorActivity::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }

         binding.btnListgardes.setOnClickListener {
              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
              val intent = Intent(this@MainActivity, ListUsersActivity::class.java)
             intent.putExtra("userName", userName)
             startActivity(intent)
             finish()
          }

      //  binding.btnSchedule.setOnClickListener {
      //      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      //      val intent = Intent(this@MainActivity, ScheduleActivity::class.java)
       //     intent.putExtra("userName", userName)
       //     startActivity(intent)
      //  }

        binding.btnAddaSensor.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent = Intent(this@MainActivity, AddSensorActivity::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_settings-> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT)
            }
            R.id.nav_userprofile-> {
                var userName = intent.getStringExtra("Username")
                userName = "tjf"
                Toast.makeText(this, userName.toString(), Toast.LENGTH_SHORT)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                intent.putExtra("userName", userName)
                startActivity(intent)
            }
            R.id.logout-> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT)
            } // finish()}
        }
        return super.onOptionsItemSelected(item)
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