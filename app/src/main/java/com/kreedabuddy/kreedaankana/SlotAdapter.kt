package com.kreedabuddy.kreedaankana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SlotAdapter(
    private val slots: MutableList<Slot>,
    private val onSlotClick: (Slot, Int) -> Unit
) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardSlot: CardView = itemView.findViewById(R.id.cardSlot)
        val tvSlotTime: TextView = itemView.findViewById(R.id.tvSlotTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slot, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val slot = slots[position]
        holder.tvSlotTime.text = slot.time

        if (slot.isBooked) {
            holder.cardSlot.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark))
        } else {
            holder.cardSlot.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
        }

        holder.tvSlotTime.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
        holder.cardSlot.setOnClickListener { onSlotClick(slot, position) }
    }

    override fun getItemCount() = slots.size
}