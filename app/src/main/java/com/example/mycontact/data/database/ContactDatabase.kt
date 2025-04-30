package com.example.mycontact.data.database



import androidx.room.Database

import androidx.room.RoomDatabase

import com.example.mycontact.data.entity.Contact


@Database(entities = [Contact::class], version = 1, exportSchema = false)
 abstract class ContactDatabase: RoomDatabase() {
     abstract fun contactDao(): ContactDao

}