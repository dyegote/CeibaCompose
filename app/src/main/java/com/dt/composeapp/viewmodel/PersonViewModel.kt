package com.dt.composeapp.viewmodel

import androidx.lifecycle.*
import com.dt.composeapp.Person
import com.dt.composeapp.database.repository.PersonRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repository: PersonRespository) :  ViewModel()
{

    val persoState = MutableLiveData<Resource<Person>>()
    val personsState = MutableLiveData<Resource<List<Person>>>()

    fun savePerson(person: Person){
        persoState.value = Resource.loading("Cargando")
        viewModelScope.launch {
            delay(3000)
            try{
                persoState.value = Resource.success(repository.savePerson(person))
            }catch (ex: java.lang.Exception){
                persoState.value = Resource.error(ex)
            }
        }

    }

    fun loadPersons(){
        try {
            val persons = repository.loadPersons()
            personsState.value = Resource.success(persons)
        } catch(ex: Exception) {
            personsState.value = Resource.error(ex)
        }
    }

}