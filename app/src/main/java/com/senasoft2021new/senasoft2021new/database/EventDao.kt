package com.senasoft2021new.senasoft2021new.database

import androidx.room.*
import com.senasoft2021new.senasoft2021new.models.EventRegister
import com.senasoft2021new.senasoft2021new.models.UserRegister

@Dao
interface EventDao {

    @Insert
    fun insertEvent(eventRegister: EventRegister)

    @Update
    fun updateEvent(eventRegister: EventRegister)

    @Delete
    fun deleteEvent(eventRegister: EventRegister)

    @Query("SELECT * FROM events")
    fun listAllEvents():MutableList<EventRegister>

}