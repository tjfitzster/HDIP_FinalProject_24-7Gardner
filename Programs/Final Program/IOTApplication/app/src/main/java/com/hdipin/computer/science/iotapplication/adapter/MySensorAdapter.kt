package com.hdipin.computer.science.iotapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hdipin.computer.science.iotapplication.R
import com.hdipin.computer.science.iotapplication.models.listSensorModel

class MySensorAdapter (private val sensorList : ArrayList<listSensorModel>) : RecyclerView.Adapter<MySensorAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.sensor_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = sensorList[position]
//lecf side of this is the app right side is the database this is where we bind the App to the database
        holder.deviceID.text = currentitem.DeviceID
        holder.timeStamp.text = currentitem.Timestamp
        holder.value.text = currentitem.Value

    }
    override fun getItemCount(): Int {

        return sensorList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val deviceID : TextView = itemView.findViewById(R.id.tvdeviceID)
        val timeStamp : TextView = itemView.findViewById(R.id.tvtimestamp)
        val value : TextView = itemView.findViewById(R.id.tvValue)

    }


}