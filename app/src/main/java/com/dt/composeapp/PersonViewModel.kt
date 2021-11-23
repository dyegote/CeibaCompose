package com.dt.composeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dt.composeapp.database.repository.PersonRespository
import com.dt.composeapp.viewmodel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repository: PersonRespository) :  ViewModel()
{
    fun guardarDatosPersonales(person: Person) : LiveData<Resource<Person>> {
        return try {
            repository.savePerson(person)
            MutableLiveData(Resource.success(person))
        } catch(ex: Exception) {
            MutableLiveData(Resource.error(ex))
        }
    }

    fun loadPersons() : LiveData<Resource<List<Person>>>{
        return try {
            val persons = repository.loadPersons()
            MutableLiveData(Resource.success(persons))
        } catch(ex: Exception) {
            MutableLiveData(Resource.error(ex))
        }
    }

}