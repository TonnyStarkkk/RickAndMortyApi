package com.example.rickandmortyapi.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.rickandmortyapi.data.api.ApiService
import com.example.rickandmortyapi.data.base.BaseRepository
import com.example.rickandmortyapi.data.model.characters.Character
import com.example.rickandmortyapi.data.model.detailCharacter.DetailModel
import com.example.rickandmortyapi.data.paging.PagingSource
import com.example.rickandmortyapi.utils.Resource

class Repository (private val api: ApiService): BaseRepository() {

    fun getCharacters(): LiveData<Resource<PagingData<Character>>> {
        return performPagingRequest(
            pagingSourceFactory = { PagingSource(api) }
        )
    }

    fun getCharacter(id: Int): LiveData<Resource<DetailModel>> {
        return performRequest(
            call = { api.getSingleCharacter(id) },
            errorMessage = "Failed to load character details"
        )
    }
}