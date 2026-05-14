package com.kreedabuddy.kreedaankana

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

data class Slot(
    var time: String = "",
    var isBooked: Boolean = false
)

class BookSlotActivity : AppCompatActivity() {

    private lateinit var adapter: SlotAdapter
    private lateinit var tvSelectedDateTitle: TextView
    private lateinit var rvTimeSlots: RecyclerView

    private var selectedDate = ""
    private val bookedSlots = mutableListOf<String>()

    private val dbRef = FirebaseDatabase
        .getInstance()
        .getReference("bookings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_slot)

        tvSelectedDateTitle = findViewById(R.id.tvSelectedDateTitle)
        rvTimeSlots = findViewById(R.id.rvTimeSlots)

        selectedDate =
            intent.getStringExtra("SELECTED_DATE") ?: "2026-05-10"

        tvSelectedDateTitle.text = "Slots for $selectedDate"
        setupRecyclerView()
        loadBookedSlotsFromFirebase()
    }

    private fun loadBookedSlotsFromFirebase() {

        dbRef.child(selectedDate)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    bookedSlots.clear()

                    for (child in snapshot.children) {

                        if (child.getValue(Boolean::class.java) == true) {
                            child.key?.let {
                                bookedSlots.add(it)
                            }
                        }
                    }

                    setupRecyclerView()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@BookSlotActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun setupRecyclerView() {

        val timeSlotStrings = generateTimeSlots()

        val slots = timeSlotStrings.map { time ->
            Slot(
                time = time,
                isBooked = bookedSlots.contains(time)
            )
        }.toMutableList()

        adapter = SlotAdapter(slots) { slot, position ->

            if (slot.isBooked) {

                Toast.makeText(
                    this,
                    "Already booked",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // UI changes immediately
                slot.isBooked = true
                adapter.notifyItemChanged(position)

                // Save to Firebase
                dbRef.child(selectedDate)
                    .child(slot.time)
                    .setValue(true)

                Toast.makeText(
                    this,
                    "Booked ${slot.time}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        rvTimeSlots.layoutManager =
            GridLayoutManager(this, 2)

        rvTimeSlots.adapter = adapter
    }

    private fun generateTimeSlots(): List<String> {

        val slots = mutableListOf<String>()

        for (hour in 6..21) {

            val startHour =
                if (hour > 12) hour - 12 else hour

            val nextHour = hour + 1

            val endHour =
                if (nextHour > 12) nextHour - 12 else nextHour

            val startPeriod =
                if (hour < 12) "AM" else "PM"

            val endPeriod =
                if (nextHour < 12) "AM" else "PM"

            val startTime =
                String.format("%02d:00 %s", startHour, startPeriod)

            val endTime =
                String.format("%02d:00 %s", endHour, endPeriod)

            slots.add("$startTime - $endTime")
        }

        return slots
    }
}