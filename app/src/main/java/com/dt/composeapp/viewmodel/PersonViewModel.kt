package com.dt.composeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dt.composeapp.Person
import com.dt.composeapp.database.repository.PersonRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repository: PersonRespository) :  ViewModel()
{
    fun savePerson(person: Person) : LiveData<Resource<Person>> {
        return resourceFlow{
            emit(Resource.success(repository.savePerson(person)))
        }.build() as LiveData<Resource<Person>>
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