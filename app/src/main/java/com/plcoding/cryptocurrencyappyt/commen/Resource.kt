package com.plcoding.cryptocurrencyappyt.commen

/**
 * a class which represent the network response
 *
 * this wrapper class contains information about the actual data and the potential error message
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T> (data: T) : Resource<T>(data)
    class Error <T>(message: String, data: T ?= null) : Resource<T>(data, message)
    class Loading<T>(data: T ?= null) : Resource<T>(data)
}
