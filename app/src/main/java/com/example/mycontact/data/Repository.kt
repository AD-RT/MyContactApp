package com.example.mycontact.data

import com.example.mycontact.data.database.ContactDao
import com.example.mycontact.data.entity.Contact
import kotlinx.coroutines.flow.onEach

class Repository (private val contactDao : ContactDao)
{
//  suspend fun insertContact(contact: Contact) = contactDao.insertContact(contact)

    suspend fun UpsertContact(contact: Contact) = contactDao.UpsertContact(contact)

    suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)

//    fun updateContact(contact: Contact) = contactDao.updateContact(contact)

    fun getAllContacts() = contactDao.getAllContacts().onEach { contacts -> }

}