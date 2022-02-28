package com.example.workmate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workmate.R
import com.example.workmate.entity.Job
import de.hdodenhof.circleimageview.CircleImageView

class CategoryListAdapter(
    private val jobs: ArrayList<Job>,
    private val context: Context
) : RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    inner class CategoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val jobImageView: CircleImageView = itemView.findViewById<CircleImageView>(R.id.imgJob)
        val tvJobTitle: TextView = itemView.findViewById<TextView>(R.id.tvJobName)
        val tvDateOfExpiry: TextView = itemView.findViewById(R.id.tvRemDay)
        val tvJobsProvider: TextView = itemView.findViewById(R.id.tvCompany)
        val btnTime: Button = itemView.findViewById(R.id.btnTime)
        val btnJobLevel: Button = itemView.findViewById(R.id.btnJobLevel)
        val layout: RelativeLayout = itemView.findViewById(R.id.layoutJobsForYou)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_category_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val newJobs = jobs[position]

        val colors = arrayListOf<Int>(
            R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
            R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10
        )
        val index = position % colors.size
        val color: Int = ContextCompat.getColor(context, colors[index])

        holder.apply {
            tvJobTitle.text = newJobs.job_title.toString()
            tvJobsProvider.text = newJobs.employer.toString()
            btnTime.text = newJobs.job_time.toString()
            btnJobLevel.text = newJobs.job_level.toString()
            layout.setBackgroundColor(color)

        }
    }

    override fun getItemCount(): Int = jobs.size
}