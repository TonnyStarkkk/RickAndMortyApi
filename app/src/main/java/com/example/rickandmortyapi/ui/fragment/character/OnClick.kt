package com.example.rickandmortyapi.ui.fragment.character

import com.example.rickandmortyapi.data.model.characters.Character

interface OnClick {

    fun onClick(model: Character)
}