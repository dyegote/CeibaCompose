package com.dt.composeapp.viewmodel

import kotlinx.coroutines.flow.FlowCollector
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
fun <T> resourceFlow(@BuilderInference block: suspend FlowCollector<T>.() -> Unit): ResourceFlow<T> = ResourceFlow(block)

