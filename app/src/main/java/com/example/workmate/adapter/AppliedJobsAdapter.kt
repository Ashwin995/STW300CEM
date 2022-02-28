package com.example.workmate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.entity.Job
import de.hdodenhof.circleimageview.CircleImageView

class AppliedJobsAdapter(
    private val context: Context,
    private val job: ArrayList<Job>
) : RecyclerView.Adapter<AppliedJobsAdapter.AppliedJobViewHolder>() {

    inner class AppliedJobViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val jobImageView: CircleImageView = itemView.findViewById<CircleImageView>(R.id.imgJob)
        val tvJobTitle: TextView = itemView.findViewById<TextView>(R.id.tvJobName)
        val tvDateOfExpiry: TextView = itemView.findViewById(R.id.tvRemDay)
        val tvJobsProvider: TextView = itemView.findViewById(R.id.tvCompany)
        val btnTime: Button = itemView.findViewById(R.id.btnTime)
        val btnJobLevel: Button = itemView.findViewById(R.id.btnJobLevel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedJobViewHolder {
        return AppliedJobViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_appliedjobsviewholder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AppliedJobViewHolder, position: Int) {

        val newJobs = job[position]
        val path = "${ServiceBuilder.loadFilePath() + newJobs.job_image}"
        holder.apply {
            tvJobTitle.text = newJobs.job_title.toString()
            tvJobsProvider.text = newJobs.company.toString()
            btnTime.text = newJobs.job_time.toString()
            btnJobLevel.text = newJobs.job_level.toString()
            Glide.with(context).load(R.drawable.img).into(jobImageView)

        }
    }

    override fun getItemCount(): Int = job.size
}