package com.kreedabuddy.kreedaankana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ChallengeAdapter(
    private val list: MutableList<Challenge>
) : RecyclerView.Adapter<ChallengeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTeam: TextView = view.findViewById(R.id.tvTeam)
        val tvDetails: TextView = view.findViewById(R.id.tvDetails)
        val btnReply: Button = view.findViewById(R.id.btnReply)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_challenge, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvTeam.text = item.teamName
        holder.tvDetails.text = "${item.sport} | ${item.date}\n${item.message}"

        holder.btnReply.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Challenge Accepted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = list.size
}