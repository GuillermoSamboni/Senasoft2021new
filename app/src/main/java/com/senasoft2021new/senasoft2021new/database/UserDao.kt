package com.senasoft2021new.senasoft2021new.database

import androidx.room.*
import com.senasoft2021new.senasoft2021new.models.UserRegister

@Dao
interface UserDao {

    @Insert
    fun insertUser(userRegister: UserRegister)

    @Update
    fun updateUser(userRegister: UserRegister)

    @Delete
    fun deleteUser(userRegister: UserRegister)

    @Query("SELECT * FROM users")
    fun listAllUsers():MutableList<UserRegister>

    @Query("SELECT * FROM users WHERE name = :name")
    fun selectUserByName(name:String):UserRegister?

    @Query("SELECT * FROM users WHERE email = :email")
    fun selectUserByEmail(email:String):UserRegister?

    @Query("SELECT * FROM users WHERE id = :id")
    fun selectUserById(id:String):UserRegister?

}