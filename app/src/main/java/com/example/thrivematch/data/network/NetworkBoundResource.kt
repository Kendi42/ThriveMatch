package com.example.thrivematch.data.network

import android.util.Log
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType>networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow{
    val data = query().first()
    val flow =if(shouldFetch(data)){
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map {Resource.Success(it)}
        }
        catch(throwable:Throwable){
            Log.e("Network error", throwable.toString())
            query().map { Resource.Failure(true, null, null)}
        }
    }
    else{
        query().map { Resource.Success(it) }
    }
    emitAll(flow)

}