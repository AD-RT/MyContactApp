package com.example.mycontact.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
@PrimaryKey(autoGenerate = true)
val id: Int,
val name: String,
val phoneNumber: String,
val email: String,
//    @ColumnInfo("add", defaultValue = "0")
//    val add: Long = System.currentTimeMillis()
)

