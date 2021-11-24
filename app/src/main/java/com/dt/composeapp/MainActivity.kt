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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

                var showProgress = remember { mutableStateOf(false) }
                var identification = remember { mutableStateOf("") }
                var name by remember { mutableStateOf("") }

                Surface(color = MaterialTheme.colors.background) {

                    Scaffold(
                        topBar = {
                            GenericTopAppBar(topAppBarText = stringResource(R.string.tittle))
                        },
                        content = {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Titulo(texto = "Ingrese datos")
                                Spacer(modifier = Modifier.height(16.dp))
                                IdentificationTextField(identification)
                                Spacer(modifier = Modifier.height(16.dp))
                                NameTextField(name = name, onValueChange = { newValue -> name = newValue })
                            }
                        },
                        bottomBar = {
                            GenericButton(
                                label = stringResource(R.string.save),
                                enabled = true,
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

    private fun savePerson(person: Person, showProgress: MutableState<Boolean>){
        personViewModel.savePerson(person).observe( this, { resultado ->
            when(resultado.status){
                Status.LOADING -> {
                    showProgress.value = true
                }
                Status.SUCCESS -> {
                    showProgress.value = false
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

    private fun mostrarSnackbar(message: String){
        scope.launch {
            snackbarHostState.showSnackbar(message = message)
        }
    }
}
