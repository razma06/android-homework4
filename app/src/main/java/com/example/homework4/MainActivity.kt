package com.example.homework4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val userList: MutableList<String> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        fetchUsers()
    }

    private fun init() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RecyclerViewAdapter(userList)
        recyclerView.adapter = adapter

    }

    private fun fetchUsers() {
        val usersCollectionRef = db.collection("users")

        usersCollectionRef.get()
            .addOnSuccessListener { result ->
                userList.clear()
                for (document in result) {
                    val name = document.getString("name")
                    if (name != null) {
                        println(name)
                        userList.add(name)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting documents: ", exception)
            }
    }
}
