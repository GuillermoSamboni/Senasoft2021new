package com.senasoft2021new.senasoft2021new.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "denuncias")
data class DenunciaRegister(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,

    @ColumnInfo(name = "title")
    var title:String="",

    @ColumnInfo(name = "description")
    var description:String="",

    @ColumnInfo(name = "image")
    var image:String=""

)