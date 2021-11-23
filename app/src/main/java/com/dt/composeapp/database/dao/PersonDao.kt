package com.dt.composeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dt.composeapp.database.model.PersonEntity

@Dao
interface PersonDao {

    @Insert
    fun insert(persona: PersonEntity)

    @Update
    fun actualizar(person: PersonEntity)

    @Query("SELECT * FROM Person")
    fun getPersonList(): List<PersonEntity>

    @Query("SELECT * FROM Person WHERE identification = :identification")
    fun getByIdentification(identification: String) : PersonEntity?

}