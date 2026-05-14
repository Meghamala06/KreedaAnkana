package com.kreedabuddy.kreedaankana

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ScoreWallActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: ScoreAdapter
    private val list = mutableListOf<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_wall)

        val etA = findViewById<EditText>(R.id.etTeamA)
        val etB = findViewById<EditText>(R.id.etTeamB)
        val etScore = findViewById<EditText>(R.id.etScore)
        val etDate = findViewById<EditText>(R.id.etDate)
        val btn = findViewById<Button>(R.id.btnAddScore)

        rv = findViewById(R.id.rvScores)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ScoreAdapter(list)
        rv.adapter = adapter

        val dbRef = FirebaseDatabase.getInstance().getReference("scores")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (child in snapshot.children) {
                    val s = child.getValue(Score::class.java)
                    if (s != null) list.add(s)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ScoreWallActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })

        btn.setOnClickListener {

            if (etA.text.isEmpty() || etB.text.isEmpty()
                || etScore.text.isEmpty() || etDate.text.isEmpty()
            ) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val s = Score(
                etA.text.toString(),
                etB.text.toString(),
                etScore.text.toString(),
                etDate.text.toString()
            )

            dbRef.push().setValue(s)
                .addOnSuccessListener {
                    Toast.makeText(this, "Saved to Firebase", Toast.LENGTH_SHORT).show()

                    etA.text.clear()
                    etB.text.clear()
                    etScore.text.clear()
                    etDate.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}