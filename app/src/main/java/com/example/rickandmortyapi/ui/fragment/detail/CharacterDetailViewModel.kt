package com.example.rickandmortyapi.ui.fragment.detail

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class CharacterDetailViewModel (private val repository: Repository): ViewModel() {

    fun getCharacter(id: Int) = repository.getCharacter(id)
}