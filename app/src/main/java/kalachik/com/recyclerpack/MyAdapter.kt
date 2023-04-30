package kalachik.com.recyclerpack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kalachik.com.practicmfc20.R

class MyAdapter(private val taskList: ArrayList<Task>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = taskList[position]


        holder.task1.text = currentitem.taskName


    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val task1 : TextView = itemView.findViewById(R.id.task)


    }
}