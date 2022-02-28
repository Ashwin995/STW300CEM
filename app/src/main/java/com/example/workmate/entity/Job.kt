package com.example.workmate.entity

import android.os.Parcel
import android.os.Parcelable

data class Job(
    val job_image: String? = null,
    val job_title: String? = null,
    val employer: String? = null,
    val job_level: String? = null,
    val job_time: String? = null,
    val experience_required: String? = null,
    val company: String? = null,
    val company_details: String? = null,
    val employee: String? = null,
    val _id: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(job_image)
        parcel.writeString(job_title)
        parcel.writeString(employer)
        parcel.writeString(job_level)
        parcel.writeString(job_time)
        parcel.writeString(experience_required)
        parcel.writeString(company)
        parcel.writeString(company_details)
        parcel.writeString(employee)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Job> {
        override fun createFromParcel(parcel: Parcel): Job {
            return Job(parcel)
        }

        override fun newArray(size: Int): Array<Job?> {
            return arrayOfNulls(size)
        }
    }
}