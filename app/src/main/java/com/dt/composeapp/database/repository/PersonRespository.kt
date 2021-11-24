package com.dt.composeapp.database.repository

import android.content.Context
import com.dt.composeapp.Person
import com.dt.composeapp.database.AppDatabase
import com.dt.composeapp.database.dao.PersonDao
import com.dt.composeapp.database.model.PersonEntity

class PersonRespository(val context: Context) {

    private val personaDao: PersonDao by lazy { AppDatabase.getInstance(context).personDao() }

    fun savePerson(person: Person){

        if(person.identification.isNullOrBlank() || person.name.isNullOrBlank())
            throw Exception("Datos no validos")

        Thread.sleep(1000)

        val entity = personaDao.getByIdentification(person.identification)
        entity?.let {
            it.name = person.name
            personaDao.actualizar(entity)
        } ?: personaDao.insert(PersonEntity(person.identification, person.name))
    }

    fun loadPersons() : List<Person>{
        val entities = personaDao.getPersonList()
        return entities.map { Person(it.identification, it.name) }
    }

}