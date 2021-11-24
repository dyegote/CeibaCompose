package com.dt.composeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*

class ResourceFlow<T>(private val block: suspend FlowCollector<T>.() -> Unit) : AbstractFlow<T>() {
    override suspend fun collectSafely(collector: FlowCollector<T>) {
        collector.block()
    }

    fun build() : LiveData<Resource<Any>> {
        val flow = this as Flow<Resource<Any>>
        return flow.onStart { emit(Resource.loading("Cargando")) }
                .catch { exception-> emit(Resource.error(exception)) }
                .flowOn(Dispatchers.IO)
                .asLiveData()
    }
}