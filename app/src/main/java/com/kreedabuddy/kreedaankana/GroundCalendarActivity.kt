package com.kreedabuddy.kreedaankana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class GroundCalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ground_calendar)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val btnBook = findViewById<Button>(R.id.btnBookSlot)

        btnBook.setOnClickListener {

            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val selectedDate = format.format(calendar.time)

            val intent = Intent(this, BookSlotActivity::class.java)
            intent.putExtra("SELECTED_DATE", selectedDate)

            startActivity(intent)
        }
    }
}