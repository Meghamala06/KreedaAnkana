package com.kreedabuddy.kreedaankana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button 1: Ground Calendar
        findViewById<Button>(R.id.btn_ground_calendar).setOnClickListener {
            val intent = Intent(this, GroundCalendarActivity::class.java)
            startActivity(intent)
        }

        // Button 2: Book Slot - IDI ADD CHEYYI
        findViewById<Button>(R.id.btn_book_slot).setOnClickListener {
            val intent = Intent(this, BookSlotActivity::class.java)
            startActivity(intent)
        }

        // Button 3: Challenge Board - IDI MUKYAM
        findViewById<Button>(R.id.btn_challenge_board).setOnClickListener {
            val intent = Intent(this, ChallengeBoardActivity::class.java)
            startActivity(intent)
        }

        // Button 4: Score Wall - IDI KUDA ADD CHEYYI
        findViewById<Button>(R.id.btn_score_wall).setOnClickListener {
            val intent = Intent(this, ScoreWallActivity::class.java)
            startActivity(intent)
        }
    }
}