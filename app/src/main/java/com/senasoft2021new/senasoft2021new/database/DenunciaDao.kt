package com.senasoft2021new.senasoft2021new.database

import androidx.room.*
import com.senasoft2021new.senasoft2021new.models.DenunciaRegister
import com.senasoft2021new.senasoft2021new.models.EventRegister

@Dao
interface DenunciaDao {

    @Insert
    fun insertDenuncia(denunciaRegister: DenunciaRegister)

    @Update
    fun updateDenuncia(denunciaRegister: DenunciaRegister)

    @Delete
    fun deleteDenuncia(denunciaRegister: DenunciaRegister)

    @Query("SELECT * FROM denuncias")
    fun listAllDenuncias():MutableList<DenunciaRegister>

}