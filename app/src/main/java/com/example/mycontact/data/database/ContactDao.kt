package com.example.mycontact.data.database

import androidx.room.Dao
import androidx.room.Delete

import androidx.room.Query

import androidx.room.Upsert
import com.example.mycontact.data.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {                       // these all functions are heavy to perform so we cannot perform them in the main thread so we have to perform them in the background thread

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertContact(contact: Contact)

    // this upsert function will insert the contact if it does not exist and update the contact if it exists
    @Upsert
    suspend fun UpsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

//    @Update
//    fun updateContact(contact: Contact)

    @Query("SELECT * FROM Contact")
    // wrap it in flow so as data is emitted from the database it will be updated in the UI
    fun getAllContacts(): Flow<List<Contact>>
}