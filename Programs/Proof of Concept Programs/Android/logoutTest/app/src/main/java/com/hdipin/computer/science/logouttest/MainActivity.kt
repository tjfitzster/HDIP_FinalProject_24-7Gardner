package com.hdipin.computer.science.logouttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_delete-> Toast.makeText(this, "Delte selected", Toast.LENGTH_SHORT).show()
            R.id.nav_favorite-> Toast.makeText(this, "Favorite selected", Toast.LENGTH_SHORT).show()
            R.id.hello-> Toast.makeText(this, "Hello selected", Toast.LENGTH_SHORT).show()
            R.id.goodbye-> Toast.makeText(this, "Goodbye selected", Toast.LENGTH_SHORT).show()
            R.id.nav_third-> Toast.makeText(this, "Third Item Selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}