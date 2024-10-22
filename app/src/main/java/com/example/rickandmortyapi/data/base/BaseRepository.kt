package com.example.rickandmortyapi.data.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyapi.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import androidx.paging.PagingSource
import androidx.paging.liveData

abstract class BaseRepository {

    protected fun <T> performRequest(
        call: suspend () -> Response<T>,
        errorMessage: String = "Unknown error"
    ): LiveData<Resource<T>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val response = call.invoke()
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        emit(Resource.Success(it))
                    }
                } else {
                    emit(Resource.Error("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Network error: ${e.message ?: errorMessage}"))
            }
        }
    }

    protected fun <T: Any> performPagingRequest(
        pagingSourceFactory: () -> PagingSource<Int, T>,
        pageSize: Int = 10
    ): LiveData<Resource<PagingData<T>>> {
        return liveData {
            emit(Resource.Loading())
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = pageSize,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = pagingSourceFactory
                ).liveData

                emitSource(pager.map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Error("Networl error : ${e.message}"))
            }
        }
    }
}