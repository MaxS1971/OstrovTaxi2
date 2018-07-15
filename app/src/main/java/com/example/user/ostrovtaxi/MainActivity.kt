package com.example.user.ostrovtaxi

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun check(view: View) {

        val view: View = if (currentFocus == null) View(this) else currentFocus
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)

        if (editText.text.isEmpty()) {
            val stringError = "Введите госномер такси"
            val toasMe = Toast.makeText(this, stringError, Toast.LENGTH_SHORT)
            toasMe.show()
        } else {
            requestDatabase()
            //val toasMe = Toast.makeText(this, editText.text, Toast.LENGTH_SHORT)
            //toasMe.show()
        }

    }

    fun requestDatabase() {
        val text = editText.text.toString().toUpperCase()
        val referance = FirebaseDatabase.getInstance().reference
        val myQuery = referance.orderByChild("VehicleNumber").equalTo(text)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    val valueString = dataSnapshot.value as String
                    val toasMe = Toast.makeText(this@MainActivity, valueString, Toast.LENGTH_SHORT)
                    toasMe.show()
                } else {
                    val toasMe = Toast.makeText(this@MainActivity, "Empty", Toast.LENGTH_SHORT)
                    toasMe.show()

                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        }

        myQuery.addValueEventListener(postListener)

    }
}
