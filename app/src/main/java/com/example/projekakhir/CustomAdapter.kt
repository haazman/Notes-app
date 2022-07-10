package com.example.projekakhir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.template_item.view.*

class CustomAdapter(private var array_adp : ArrayList<DataClass>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    private lateinit var clicked: itemClick

    interface itemClick{
        fun click(position: Int)
    }

    fun setItemClick(isClicked: itemClick){
        clicked = isClicked
    }

    class CustomViewHolder(parent: View, itemClick: itemClick) : RecyclerView.ViewHolder(parent){
        val judulhold : TextView? = itemView.judul_template
        val catatanhold : TextView? = itemView.catatan_template

        init {
            itemView.setOnClickListener{
                itemClick.click(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val buatHolder =  LayoutInflater.from(parent.context).inflate(R.layout.template_item,parent,false)
        return CustomViewHolder(buatHolder, clicked)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.judulhold?.text = array_adp[position].judul_data
        holder.catatanhold?.text = array_adp[position].catatan_data
    }

    override fun getItemCount(): Int {
        return array_adp.size
    }

    fun filteredData(filteredList : ArrayList<DataClass>){
        array_adp = filteredList
        notifyDataSetChanged()
    }

}


