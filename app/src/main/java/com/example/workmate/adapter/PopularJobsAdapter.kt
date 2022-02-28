package com.example.workmate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workmate.R
import com.example.workmate.entity.Job
import de.hdodenhof.circleimageview.CircleImageView

class PopularJobsAdapter(
    private val jobs: ArrayList<Job>,
    private val context: Context
) :
    RecyclerView.Adapter<PopularJobsAdapter.JobsViewHolder>() {

    inner class JobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val jobImageView: CircleImageView = itemView.findViewById<CircleImageView>(R.id.imgJob)
        val tvJobTitle: TextView = itemView.findViewById<TextView>(R.id.tvJobTitleI)
//        val tvJobCount: TextView = itemView.findViewById<TextView>(R.id.tvJobCount)
        val layout: RelativeLayout = itemView.findViewById(R.id.layoutPopularJobs)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        return JobsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_popolarjobs_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val newJobs = jobs[position]

        val colors = arrayListOf<Int>(
            R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
            R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10
        )
        val index = position % colors.size
        val color: Int = ContextCompat.getColor(context, colors[index])

        holder.apply {
            tvJobTitle.text = newJobs.job_title.toString()
//            tvJobCount.text = "6"
            layout.setBackgroundColor(color)

        }
    }

    override fun getItemCount(): Int = jobs.size


}