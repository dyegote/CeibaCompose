package com.dt.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.dt.composeapp.components.*
import com.dt.composeapp.ui.theme.ComposeAppTheme
import com.dt.composeapp.viewmodel.PersonViewModel
import com.dt.composeapp.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import  com.dt.composeapp.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var snackbarHostState: SnackbarHostState
    lateinit var scope: CoroutineScope
    private val personViewModel: PersonViewModel by viewModels()
    val personsLiveData =  MutableLiveData(listOf<Person>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                snackbarHostState = remember { SnackbarHostState() }
                scope = rememberCoroutineScope()

                val persons = personsLiveData.observeAsState() as MutableState<List<Person>>
                var showProgress = rememberSaveable { mutableStateOf(false) }
                var identification = rememberSaveable { mutableStateOf("") }
                var name by rememberSaveable { mutableStateOf("") }

                Surface(color = MaterialTheme.colors.background) {

                    Scaffold(
                        topBar = { GenericTopAppBar(topAppBarText = stringResource(R.string.tittle)) },
                        content = { innerPadding ->
                            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = innerPadding.calculateBottomPadding())) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Titulo(texto = stringResource(R.string.input_data))
                                Spacer(modifier = Modifier.height(12.dp))
                                IdentificationTextField(identification)
                                Spacer(modifier = Modifier.height(16.dp))
                                NameTextField(name = name, onValueChange = { newValue -> name = newValue })
                                Spacer(modifier = Modifier.height(16.dp))
                                Titulo(texto = stringResource(R.string.persons))
                                Spacer(modifier = Modifier.height(12.dp))
                                PersonListContent(personas = persons)
                            }
                        },
                        bottomBar = {
                            GenericButton(
                                label = stringResource(R.string.save),
                                onClick = {
                                    val person = Person(identification.value, name)
                                    savePerson(person, showProgress)
                                }
                            )
                        }
                    )
                }

                ProgressDialog(showProgress.value)
                GenericSnackbar(snackbarHostState = snackbarHostState)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadPersons()
    }

    private fun savePerson(person: Person, showProgress: MutableState<Boolean>){
        personViewModel.savePerson(person).observe( this, { resultado ->
            when(resultado.status){
                Status.LOADING -> {
                    showProgress.value = true
                }
                Status.SUCCESS -> {
                    showProgress.value = false
                    loadPersons()
                    mostrarSnackbar(getString(R.string.save_message))
                }
                Status.ERROR -> {
                    showProgress.value = false
                    val message = resultado.exception?.let { it.message } ?: getString(R.string.generic_error)
                    mostrarSnackbar(message)
                }
            }
        })
    }

    private fun loadPersons(){
        personViewModel.loadPersons().observe( this, { resultado ->
            when(resultado.status){
                Status.SUCCESS -> {
                    personsLiveData.value = resultado.data
                }
                Status.ERROR -> {
                    val message = resultado.exception?.let { it.message } ?: getString(R.string.generic_error)
                    mostrarSnackbar(message)
                }
            }
        })
    }

    private fun mostrarSnackbar(message: String){
        scope.launch {
            snackbarHostState.showSnackbar(message = message)
        }
    }
}
