package com.senasoft2021new.senasoft2021new.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserRegister(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,

    @ColumnInfo(name = "name")
    var name:String="",

    @ColumnInfo(name = "phone")
    var phone:String="",

    @ColumnInfo(name = "email")
    var email:String="",

    @ColumnInfo(name = "password")
    var password:String=""

)