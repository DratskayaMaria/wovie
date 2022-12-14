package com.example.wovie.ui.actor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.wovie.databinding.FragmentActorBinding
import com.example.wovie.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorFragment : Fragment() {
    private val viewModel: ActorViewModel by viewModels()
    private var _binding: FragmentActorBinding? = null
    val binding: FragmentActorBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorBinding.inflate(inflater, container, false)
        val actorId = ActorFragmentArgs.fromBundle(requireArguments()).result
        viewModel.getActorInfo(actorId)
        binding.backButton.setOnClickListener {
            closeFragment()
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressbar.isVisible = isLoading
            binding.actorInfo.isVisible = !isLoading
        }
        viewModel.actor.observe(viewLifecycleOwner) { actor ->
            actor.photo?.let { loadImage(requireContext(), it, binding.posterImage) }
            binding.name.text = actor.name
            if (actor.dateBirth != null) {
                binding.birthYear.text = actor.dateBirth
            } else {
                binding.birthYear.isVisible = false
                binding.dash.isVisible = false
                binding.deathYear.isVisible = false
            }
            if (actor.dateDeath != null) {
                binding.deathYear.text = actor.dateDeath
            }
            if (actor.bio.isNullOrEmpty()) {
                binding.biography.isVisible = false
            } else {
                binding.biographyTitle.visibility = View.VISIBLE
                binding.biography.text = actor.bio
            }
        }

        viewModel.msg.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}