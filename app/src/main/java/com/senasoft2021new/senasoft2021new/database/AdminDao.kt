package com.senasoft2021new.senasoft2021new.database

import androidx.room.*
import com.senasoft2021new.senasoft2021new.models.AdminRegister
import com.senasoft2021new.senasoft2021new.models.EventRegister

@Dao
interface AdminDao {

    @Insert
    fun insertAdmin(adminRegister: AdminRegister)

    @Update
    fun updateAdmin(adminRegister: AdminRegister)

    @Delete
    fun deleteAdmin(adminRegister: AdminRegister)

    @Query("SELECT * FROM admin")
    fun listAllAdmin():MutableList<AdminRegister>

    @Query("SELECT * FROM admin WHERE dni = :dni")
    fun selectAdminByDni(dni:String):AdminRegister?

}