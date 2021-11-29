package com.dt.composeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

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
                        Text(text = stringResource(id = com.dt.composeapp.R.string.close), color = MaterialTheme.colors.onError)
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

@Composable
fun Titulo(texto: String){
    Text(
        text = texto,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.primary)
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