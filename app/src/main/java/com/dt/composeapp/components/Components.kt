package com.dt.composeapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dt.composeapp.Person
import  com.dt.composeapp.R

//Sin estado (stateless)
@Composable
fun IdentificationTextField(identification: MutableState<String>){
    OutlinedTextField(
        label = { Text(text =  stringResource(R.string.identification))},
        value = identification.value,
        onValueChange = { newValue -> identification.value = newValue },
        modifier = Modifier.fillMaxWidth()
    )
}

//Elevacion de estado (State hoisting)
@Composable
fun NameTextField(name: String, onValueChange: (newValue: String) -> Unit){
    OutlinedTextField(
        label = { Text(stringResource(R.string.name))},
        value = name,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = Modifier.fillMaxWidth()
    )
}

@SuppressLint("ResourceType")
@Composable
fun PersonCard(nombre: String, identificacion: String)
{
    Card(
        modifier = Modifier.padding(6.dp).fillMaxWidth().wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.padding(12.dp)) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface,
                )
                Text(
                    text = "CC $identificacion",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun PersonListContent(personas:  List<Person>) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {

        items(items = personas){ person ->
            PersonCard(
                nombre = person.name,
                identificacion = person.identification
            )
        }

    }
}


//Con estado(statefull)
@Composable
fun ProgressDialog(mostrarProgress: Boolean){

    val texto = remember { mutableStateOf("Cargando...") }
    //val texto by  remember { mutableStateOf("Crgando...") }

    if(mostrarProgress) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CircularProgressIndicator()
                    Text(text = texto.value)
                }
            }
        }
    }
}

@Composable
fun Titulo(texto: String){
    Text(
        text = texto,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.primary)
}

@Composable
fun GenericTopAppBar(topAppBarText: String, onBackPressed: () -> Unit = {}) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 3.dp
    )
}

@Composable
fun GenericButton(label: String, enabled: Boolean = true, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        enabled = enabled
    ) {
        Text(text = label, style = MaterialTheme.typography.button)
    }
}

@Composable
fun GenericSnackbar(snackbarHostState: SnackbarHostState)
{
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarConfirmation(
            snackbarHostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SnackbarConfirmation(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                backgroundColor = SnackbarDefaults.backgroundColor.copy(alpha = 0.9f),
                content = {
                    Text(text = data.message, style = MaterialTheme.typography.body2)
                },
                action = {
                    TextButton(
                        onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                    ) {
                        Text(text = stringResource(id = R.string.close), color = MaterialTheme.colors.onError)
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

