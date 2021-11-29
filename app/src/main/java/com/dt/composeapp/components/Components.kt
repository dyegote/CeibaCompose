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



