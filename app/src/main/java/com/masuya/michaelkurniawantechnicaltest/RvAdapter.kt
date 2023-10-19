package com.masuya.michaelkurniawantechnicaltest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RvAdapter(private val context: Context, private val usersList : List<com.masuya.michaelkurniawantechnicaltest.User>) :
    RecyclerView.Adapter<RvAdapter.ListViewHolder>() {
    private  lateinit var onItemClickCallback : OnItemClickCallback
    interface OnItemClickCallback{
        fun onItemCLicked(data : com.masuya.michaelkurniawantechnicaltest.User)
    }
    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallback= onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var user = usersList[position]
        holder.name.setText(user.UName)
        holder.role.setText(user.NmRole)
        holder.uid.setText(user.UID)
        holder.itemView.setOnClickListener {
            val detailPage = Intent(context, DetailUser::class.java)
            detailPage.putExtra("UID", user.UID)
            context.startActivity(detailPage)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(R.id.tvTitle)
        var role : TextView = itemView.findViewById(R.id.tvSubtitle)
        var uid : TextView = itemView.findViewById(R.id.tvTrailing)
        var item : ConstraintLayout = itemView.findViewById(R.id.item)
    }

}