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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var snackbarHostState: SnackbarHostState
    lateinit var scope: CoroutineScope
    private val personViewModel: PersonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                snackbarHostState = remember { SnackbarHostState() }
                scope = rememberCoroutineScope()

                val persons = personViewModel.personsState.observeAsState()
                var person = personViewModel.persoState.observeAsState()

                var identification = rememberSaveable { mutableStateOf("") }
                var name by rememberSaveable { mutableStateOf("") }

                when(person.value?.status){
                    Status.LOADING -> { ProgressDialog(true) }
                    Status.SUCCESS -> {
                        ProgressDialog(false)
                        personViewModel.loadPersons()
                        mostrarSnackbar(getString(R.string.save_message))
                    }
                    Status.ERROR -> {
                        ProgressDialog(false)
                        val message = person.value?.exception?.let { it.message } ?: getString(R.string.generic_error)
                        mostrarSnackbar(message)
                    }
                }

                personViewModel.loadPersons()

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
                                persons.value?.data?.let {
                                    PersonListContent(personas = it)
                                }
                            }
                        },
                        bottomBar = {
                            GenericButton(
                                label = stringResource(R.string.save),
                                onClick = {
                                    personViewModel.savePerson(Person(identification.value, name))
                                }
                            )
                        }
                    )
                }

                GenericSnackbar(snackbarHostState = snackbarHostState)
            }
        }
    }

    private fun mostrarSnackbar(message: String){
        scope.launch {
            snackbarHostState.showSnackbar(message = message)
        }
    }
}
