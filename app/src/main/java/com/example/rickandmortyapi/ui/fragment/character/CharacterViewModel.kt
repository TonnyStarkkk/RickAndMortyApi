package com.example.rickandmortyapi.ui.fragment.character

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class CharacterViewModel (private val repository: Repository): ViewModel() {
    fun getCharacters() = repository.getCharacters()
}