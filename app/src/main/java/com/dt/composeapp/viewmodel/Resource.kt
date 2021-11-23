package com.dt.composeapp.viewmodel

data class Resource<out T>(val status: Status, val data: T?, val exception: Throwable?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return  Resource(Status.SUCCESS, data, exception = null)
        }

        fun <T> error(exception: Throwable?): Resource<T> {
            return Resource(Status.ERROR, data = null, exception)
        }

        fun <T> loading(message: String?):  Resource<T> {
            return Resource(Status.LOADING, data = null, exception = null)
        }
    }
}