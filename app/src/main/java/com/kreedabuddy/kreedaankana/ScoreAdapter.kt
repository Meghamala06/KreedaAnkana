package com.kreedabuddy.kreedaankana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(
    private val list: List<Score>
) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvTeams: TextView =
            itemView.findViewById(R.id.tvTeams)

        val tvScore: TextView =
            itemView.findViewById(R.id.tvScore)

        val tvDate: TextView =
            itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScoreViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score, parent, false)

        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ScoreViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.tvTeams.text =
            "${item.teamA} vs ${item.teamB}"

        holder.tvScore.text =
            item.score

        holder.tvDate.text =
            item.date
    }

    override fun getItemCount(): Int = list.size
}