package com.example.workmate.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.entity.Job
import com.example.workmate.ui.DescriptionActivity
import de.hdodenhof.circleimageview.CircleImageView


class JobsForYouAdapter(
    private val jobs: ArrayList<Job>,
    private val context: Context
) :
    RecyclerView.Adapter<JobsForYouAdapter.JustForYouViewHolder>() {

    inner class JustForYouViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val jobImageView: CircleImageView = itemView.findViewById<CircleImageView>(R.id.imgJob)
        val tvJobTitle: TextView = itemView.findViewById<TextView>(R.id.tvJobTitleI)
        val tvDateOfExpiry: TextView = itemView.findViewById(R.id.tvDateOfExpiry)
        val tvJobsProvider: TextView = itemView.findViewById(R.id.tvJobProvider)
        val layout: ConstraintLayout = itemView.findViewById(R.id.layoutJobsForYou)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JustForYouViewHolder {
        return JustForYouViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_justforyou_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: JustForYouViewHolder, position: Int) {
        val newJobs = jobs[position]

        val colors = arrayListOf<Int>(
            R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
            R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10
        )
        val index = position % colors.size
        val color: Int = ContextCompat.getColor(context, colors[index])

        holder.apply {
            tvJobTitle.text = newJobs.job_title.toString()
            tvJobsProvider.text = ""
            tvDateOfExpiry.text = ""
            layout.setBackgroundColor(color)
            layout.setOnClickListener {
                context.startActivity(
                    Intent(context, DescriptionActivity::class.java)
                        .putExtra("job", newJobs)
                )
                ServiceBuilder.jfuid = newJobs._id.toString()
//                Toast.makeText(context, "${newJobs._id}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = jobs.size
}
