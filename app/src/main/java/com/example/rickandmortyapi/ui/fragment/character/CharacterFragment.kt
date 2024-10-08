package com.example.rickandmortyapi.ui.fragment.character

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.data.model.characters.Character
import com.example.rickandmortyapi.databinding.FragmentCharacterBinding
import com.example.rickandmortyapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : Fragment(), OnClick {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
        initialize()
    }

    private fun setupObserve() {
        viewModel.getCharacters().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> viewLifecycleOwner.lifecycleScope.launch {
                    binding.pgCharacter.visibility = View.GONE
                    adapter.submitData(resource.data)
                }

                is Resource.Error -> {
                    binding.pgCharacter.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.pgCharacter.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initialize() {
        adapter = CharacterAdapter(this@CharacterFragment)
        binding.rvCharacter.adapter = adapter
    }

    override fun onClick(model: Character) {
        val action = CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(model.id)
        Log.e("TAG", "onClick: $model.id",)
        findNavController().navigate(action)
    }
}