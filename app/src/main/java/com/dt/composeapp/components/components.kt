package com.dt.composeapp.components

import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

//Con estado (statefull)
@Composable
fun IdentificacionTextField(){

    var identification by remember { mutableStateOf("") }

    OutlinedTextField(
        value = identification,
        onValueChange = { nuevoValor ->
            identification = nuevoValor
        }
    )


}

fun Nombre(){

}