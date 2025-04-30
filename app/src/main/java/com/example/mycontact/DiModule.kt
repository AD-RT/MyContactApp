package com.example.mycontact

import android.app.Application

import androidx.room.Room

import com.example.mycontact.data.Repository
import com.example.mycontact.data.database.ContactDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides               // we used Room that is other libraries so we apply @Provides annotation to tell dagger how to provide the instance of the room database
    @Singleton
    fun provideDatabase(application: Application): ContactDatabase {
        return Room.databaseBuilder(
            application,
            ContactDatabase::class.java,
            "contact_database.sql"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: ContactDatabase): Repository {
        return Repository(database.contactDao())
    }

}