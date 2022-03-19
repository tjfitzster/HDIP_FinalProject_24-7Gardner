package com.hdipin.computer.science.testloginprogram



import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hdipin.computer.science.testloginprogram.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendbtn.setOnClickListener{

            val firstName = binding.editTextTextPersonName2.text.toString()
            val lastName = binding.editTextTextPassword2.text.toString()


            val user = User(firstName,lastName)
            database.child(firstName).setValue(user).addOnSuccessListener {

                binding.editTextTextPersonName2.text.clear()
                binding.editTextTextPassword2.text.clear()


                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()

            }

        }


    }
}