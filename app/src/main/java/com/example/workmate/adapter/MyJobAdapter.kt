package com.example.workmate.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import com.example.workmate.ui.UpdateJobActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyJobAdapter(
    private val context: Context,
    private val job: ArrayList<Job>
) : RecyclerView.Adapter<MyJobAdapter.MyJobViewHolder>() {

    inner class MyJobViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val jobImage: CircleImageView = view.findViewById(R.id.imgJob)
        val jobTitle: TextView = view.findViewById(R.id.tvJobTitleI)
        val delete: TextView = view.findViewById(R.id.tvDelete)
        val edit: TextView = view.findViewById(R.id.tvEdit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyJobViewHolder {
        return MyJobViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_myjobsviewholder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyJobViewHolder, position: Int) {
        val newJob = job[position]
        val path = "${ServiceBuilder.loadFilePath() + newJob.job_image}"
        holder.apply {
            jobTitle.text = newJob.job_title.toString()
            Glide
                .with(context)
                .load(R.drawable.img)
                .into(jobImage)

            delete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val jobRepo = JobRepository()
                    val response = jobRepo.deleteJob(newJob._id.toString());
                    if (response.success == true) {
                        withContext(Main) {
                            Toast.makeText(
                                context, "" +
                                        "Job Deleted", Toast.LENGTH_SHORT
                            ).show()


                        }
                    }
                }
            }
            edit.setOnClickListener {
                context.startActivity(
                    Intent(context, UpdateJobActivity::class.java)
                        .putExtra("job", newJob)
                )
                ServiceBuilder.jid = newJob._id;
            }
        }
    }

    override fun getItemCount(): Int = job.size
}