package com.example.rickandmortyapi.ui.fragment.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmortyapi.data.base.BaseFragment
import com.example.rickandmortyapi.data.model.characters.Character
import com.example.rickandmortyapi.databinding.FragmentCharacterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment : BaseFragment<FragmentCharacterBinding, CharacterViewModel>(), OnClick {

    override val viewModel: CharacterViewModel by viewModel()
    private lateinit var adapter: CharacterAdapter


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterBinding {
        return FragmentCharacterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
        initialize()
    }

    private fun setupObserve() {
        observeData(viewModel.getCharacters()) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initialize() {
        adapter = CharacterAdapter(this)
        binding.rvCharacter.adapter = adapter
    }

    override fun onClick(model: Character) {
        val action =
            CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(model.id)
        findNavController().navigate(action)
    }
}
