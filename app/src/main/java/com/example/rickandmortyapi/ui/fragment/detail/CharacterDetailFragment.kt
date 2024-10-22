package com.example.rickandmortyapi.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.data.base.BaseFragment
import com.example.rickandmortyapi.databinding.FragmentCharacterDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>() {

    override val viewModel: CharacterDetailViewModel by viewModel()
    private val args: CharacterDetailFragmentArgs by navArgs()


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterDetailBinding {
        return FragmentCharacterDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListData()
    }

    private fun initListData() {
        observeData(viewModel.getCharacter(args.id)) { detail ->
            binding.apply {
                characterNameTextView.text = detail.name
                characterStatusTextView.text = detail.status
                characterGenderTextView.text = detail.gender
                lastKnownLocationTextView.text = detail.location.name
                headerImageView.load(detail.image) {
                    crossfade(true)
                }
            }
        }
    }
}