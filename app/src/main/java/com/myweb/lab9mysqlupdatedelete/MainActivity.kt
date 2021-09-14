package com.myweb.lab9mysqlupdatedelete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.myweb.lab9mysqlupdatedelete.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var studentList = arrayListOf<Student>()
    val createClient = StudentAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )

        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(binding.recyclerView.context,
                DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        callStudent()
    }

    fun callStudent(){
        studentList.clear()
        createClient.retrieveStudent()
                .enqueue(object : Callback<List<Student>> {
                    override fun onResponse(
                            call: Call<List<Student>>,
                            response: Response<List<Student>>
                    ) {
                        response.body()?.forEach {
                            studentList.add(Student(it.std_id, it.std_name, it.std_age))
                        }
                        binding.recyclerView.adapter = EditStudentsAdapter(studentList, applicationContext)
                    }

                    override fun onFailure(call: Call<List<Student>>, t: Throwable){
                        t.printStackTrace()
                        Toast.makeText(applicationContext,"Error2", Toast.LENGTH_LONG).show()
                    }
                })
    }

    fun clickSearch(v: View){
        studentList.clear()
        if(binding.edtSearch.text!!.isEmpty()){
            callStudent()
        }else{
            createClient.retrieveStudentID(binding.edtSearch.text.toString())
                    .enqueue(object :Callback<Student>{
                        override fun onResponse(call: Call<Student>, response: Response<Student>) {
                            if(response.isSuccessful){
                                studentList.add(Student(response.body()?.std_id.toString(),
                                        response.body()?.std_name.toString(),
                                        response.body()?.std_age.toString().toInt()
                                ))
                                binding.recyclerView.adapter= EditStudentsAdapter(studentList,applicationContext)
                            }else{
                                Toast.makeText(applicationContext,"Student ID Not Found",Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<Student>, t: Throwable) = t.printStackTrace()
                    })
        }
    }
}