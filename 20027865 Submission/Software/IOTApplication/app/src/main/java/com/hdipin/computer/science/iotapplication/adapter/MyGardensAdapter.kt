package com.hdipin.computer.science.iotapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.models.ListGardensModel

class MyGardensAdapter (private val gardenList : ArrayList<ListGardensModel>) : RecyclerView.Adapter<MyGardensAdapter.MyViewHolder>() {
    var gardenID : String = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.garden_item,
            parent,false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = gardenList[position]
//left side of this is the app right side is the database this is where we bind the App to the database
        gardenID = currentitem.gid.toString()
        holder.grdentitle.text = currentitem.gardenTitle
        holder.location.text = currentitem.gardenLocation
    }
    override fun getItemCount(): Int {
        return gardenList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val grdentitle : TextView = itemView.findViewById(R.id.tvgardenTitle)
        val location : TextView = itemView.findViewById(R.id.tvlocation)
    }
}