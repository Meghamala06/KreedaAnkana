package com.kreedabuddy.kreedaankana

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ChallengeBoardActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: ChallengeAdapter
    private val list = mutableListOf<Challenge>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_board)

        // 🔹 UI references
        val btnAdd = findViewById<Button>(R.id.btnAddChallenge)
        val etTeam = findViewById<EditText>(R.id.etTeam)
        val etSport = findViewById<EditText>(R.id.etSport)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etMessage = findViewById<EditText>(R.id.etMessage)

        rv = findViewById(R.id.rvChallenges)

        // 🔹 RecyclerView setup
        adapter = ChallengeAdapter(list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // 🔥 Firebase reference
        val dbRef = FirebaseDatabase.getInstance().getReference("challenges")

        // 🔥 Load challenges from Firebase
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (child in snapshot.children) {
                    val challenge = child.getValue(Challenge::class.java)
                    challenge?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChallengeBoardActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })

        // 🔥 Add challenge from user input
        btnAdd.setOnClickListener {

            val team = etTeam.text.toString()
            val sport = etSport.text.toString()
            val date = etDate.text.toString()
            val msg = etMessage.text.toString()

            if (team.isEmpty() || sport.isEmpty() || date.isEmpty() || msg.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val challenge = Challenge(team, sport, date, msg)

            dbRef.push().setValue(challenge)

            // clear fields
            etTeam.text.clear()
            etSport.text.clear()
            etDate.text.clear()
            etMessage.text.clear()

            Toast.makeText(this, "Challenge Posted!", Toast.LENGTH_SHORT).show()
        }
    }
}