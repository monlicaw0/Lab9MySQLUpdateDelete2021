package com.myweb.lab9mysqlupdatedelete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.myweb.lab9mysqlupdatedelete.databinding.ActivityEditDeleteBinding
import com.myweb.lab9mysqlupdatedelete.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDeleteActivity : AppCompatActivity() {
    private lateinit var bindingEditDelete : ActivityEditDeleteBinding
    val createClient = StudentAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingEditDelete = ActivityEditDeleteBinding.inflate(layoutInflater)
        setContentView(bindingEditDelete .root )

        val mID = intent.getStringExtra("mId")
        val mName = intent.getStringExtra("mName")
        val mAge = intent.getStringExtra("mAge")

         bindingEditDelete.edtId.setText(mID)
         bindingEditDelete.edtId.isEnabled = false
         bindingEditDelete.edtName.setText(mName)
         bindingEditDelete.edtAge.setText(mAge)
    }
    fun saveStudent(v: View) {
        createClient.updateStudent(
            bindingEditDelete.edtId.text.toString(),
            bindingEditDelete.edtName.text.toString(),
            bindingEditDelete.edtAge.text.toString().toInt()
        )
            .enqueue(object : Callback<Student> {
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    if (response.isSuccessful) {
                        Toast.makeText( applicationContext, "Seccessfully Updated",Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, " Update Failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Student>, t: Throwable) = t.printStackTrace()

            })

    }
    fun deleteStudent(v: View) {
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("Warning Message")
            setMessage("Do you want to delete the student?")
            setNegativeButton("Yes") { dialog, which ->
                createClient.deleteStudent(bindingEditDelete.edtId.text.toString())
                        .enqueue(object : Callback<Student> {
                            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(applicationContext, "Seccessfully Deleted",
                                            Toast.LENGTH_LONG).show()
                                    finish()
                                } else {
                                    Toast.makeText( applicationContext, " Delete Failure",
                                            Toast.LENGTH_LONG ).show()
                                }
                            }
                            override fun onFailure(call: Call<Student>, t: Throwable) =
                                    t.printStackTrace()
                        })
            }
            setPositiveButton("No") { dialog, which ->  dialog.cancel() }
            show()
        }
    }

}